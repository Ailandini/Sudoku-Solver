package skillsassessment;

/**
 *
 * @author landini
 */
import java.util.*;
import java.io.*;
public class AC3Sudoku {

    //Instantiate variables for solver
    
    private int count = 0; //for speed testing purposes
    private final Rules rules = new Rules(); //imported set of sudoku rules
    private boolean solved = false; //end condition boolean
    private boolean noupdate; //backtrack condition boolean
    
    //build the possibilities structure, keys for which are every position in the puzzle
    private HashMap<ArrayList<Integer>, ArrayList<Integer>> buildContext(HashMap<ArrayList<Integer>, ArrayList<Integer>> context, ArrayList<ArrayList<Integer>> puzzle, ArrayList<ArrayList<Integer>> boxes){
        //loop through puzzle
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                ArrayList<Integer> nons = new ArrayList<>();
                for(int k=1;k<10;k++){
                    
                    if(!rules.checkRules(i, j, k, puzzle, boxes)){
                        nons.add(k);
                    }
                }
                ArrayList<Integer> key = new ArrayList<>(2);
                //assign index to key
                key.add(i);key.add(j);
                //assign key to context
                context.put(key, nons);

            }
        }
        return context;
    }

    //standard print method to make ending sudoku aestethic 
    private void printSud(ArrayList<ArrayList<Integer>> puzzle){
        String output = "";
        //loop through puzzle
        for(int i=0;i<9;i++){
            //start each row with a '['
            output+="[";
            for(int j=0;j<9;j++){
                //if we reach the end of the row:
                if(j == 8){
                    //check if this is the end of the puzzle, and if so, print it
                    if(i==8 && j==8){
                        output += puzzle.get(i).get(j) +"]";
                        System.out.println(output);
                    }
                    //if its not the end of the puzzle, add the last number in the row
                    else{ output += puzzle.get(i).get(j);
                    }
                }
                //if we are in the middle of the row, add the next number and a '|' to separate from the next
                else{
                    output+= puzzle.get(i).get(j) + "|";
                }
            }
            //at the end of each row print ']'
            output+="]\n";
        }
    }

    //method for creating and updating "box structure" The box structure is a 9x9 matrix
    //However it differs from the main puzzle in that each row is composed of the 3x3 squares of the puzzle
    //The order of the elements in each row is 1:1 - 1:3, 2:1 - 2:3, 3:1 - 3:3 of each box respectively
    //Each successive row is composed of the subsequent sudoku boxes left-right and top-down
    //for example if the first two sudoku boxes read: 
    //[1 2 3][4 5 6]
    //[4 5 6][7 8 9]
    //[7 8 9][1 2 3] 
    //Then the box structure's first two rows would read:
    //[1 2 3 4 5 6 7 8 9]
    //[4 5 6 7 8 9 1 2 3]
    //boxMaker works recursively, taking 0 0 0 in as its starting parameters always.
    private ArrayList<ArrayList<Integer>> boxMaker(Integer i, Integer j, Integer count, ArrayList<ArrayList<Integer>> boxes, ArrayList<ArrayList<Integer>> puzzle){
        ArrayList<Integer> box = new ArrayList<>(9);
        //if count = 3 (as it starts at 0) we need to jump down 3 rows to the next set of boxes
        if(count == 3){
            i = i+3;
            j = 0;
            count = 0;
        }
        //if we have surpassed all rows of the puzzle. Our box structure is complete.
        if(i == 9){
            return boxes;
        }
        //iterate over a box of the puzzle, transcribing its values into a straight array
        for(int row = i;row<i+3;row++){
            for(int col = j;col<j+3;col++){
                    box.add(puzzle.get(row).get(col));
            }
        }
        //add the straight array to the box structure;
        boxes.add(box);
        //jump to the next box of the puzzle
        boxMaker(i,j+3,count+1, boxes, puzzle);
        return new ArrayList<>();
    }

    
//    private Set<Set> buildWorklist(HashMap<ArrayList<Integer>,ArrayList<Integer>> context){
//        ArrayList<ArrayList<Integer>> vals = new ArrayList<>(context.values());
//        Set<Set<Set>> worklist;
//        worklist.add((Set<Set>)context);
//    }
    
//    private void ac3 (Set<Set<Set>> worklist){
//     
//        Iterator iter = worklist.iterator();
//        Set<Set> arc = (Set<Set>)iter.next();
//        ArrayList<Integer> Dx;
//        ArrayList<Integer> Dy;
//        do{
//            Dx = (ArrayList)iter.next();
//            Dy = (ArrayList)iter.next();
//            worklist.remove(arc);
//            if(arc_reduce(arc, Dx, Dy)){
//                if(Dx.isEmpty()){
//                    return worklist; //backtrack
//                }
//                else{
//                    newarc.add((Set)Dy); newarc.add((Set)Dx);
//                }
//            }
//        }while(!worklist.isEmpty());
//               
//    }
    
    private boolean arc_reduce(Set<Set> pair, ArrayList<Integer> Dx, ArrayList<Integer> Dy){
        Iterator iter = pair.iterator();
        
        boolean change = false;
        boolean pass = false;
        for(Integer vx : Dx){
            for(Integer vy : Dy){
                if(vx == vy){
                    pass = true;
                    break;
                }
            }
            if(!pass){
                Dx.remove(vx);
                change = true;
            }
        }
        return change;
    }
    
    private ArrayList<ArrayList<Integer>> finished(ArrayList<ArrayList<Integer>> puzzle){
        printSud(puzzle);
        
        return puzzle;
    }

    //Helper method to reduce text traffic in main function method.
    private void solveHelper(ArrayList<ArrayList<Integer>> puzzle, ArrayList<ArrayList<Integer>> boxes, HashMap<ArrayList<Integer>, ArrayList<Integer>> context, ArrayList<Integer> place){
            
            //assert there is not a need for backtracking, get the minimal location and assign helpful variables
            noupdate = true;
            
            ArrayList<Integer> holder = new ArrayList<>();
            ArrayList<ArrayList<Integer>> updatedPuzzle = new ArrayList<>(puzzle);
            ArrayList<ArrayList<Integer>> updatedBoxes = new ArrayList<>(boxes);
            HashMap<ArrayList<Integer>, ArrayList<Integer>> updatedContext = new HashMap<>(context);
            int row = 0;
            int col = 1;
            
            //if findmin discovers the puzzle is complete, return
            if(solved){
                System.out.println(sudokuChecker(boxes));
                finished(puzzle);
            }
            //if we do not need to backtrack, try a value
            if(noupdate){
                //instantiate a variable for 
                ArrayList<Integer> changeLoc = new ArrayList<>(2);
                //for all possible number 1-9
                for(int i= 1;i<10;i++){
                   
                    //check if a possibility passes all rules
                    if(rules.checkRules(row, col, i, updatedPuzzle, updatedBoxes)){
                        //check if a possibility has not be tried before
                        if(!updatedContext.get(Arrays.asList(row,col)).contains(i)){
                           //clear whatever may have been the chosen location and update it to the current min
                           changeLoc.clear();
                           changeLoc.add(row); changeLoc.add(col);
                           //add the chosen value for the position to the tried values
                           holder = new ArrayList<>(updatedContext.get(changeLoc));
                           holder.add(i);

                           updatedContext.put(changeLoc, holder);
                           
                           ArrayList<Integer> replacrow = new ArrayList<>(puzzle.get(row));
                           replacrow.set(col, i);
                           updatedPuzzle.set(row, replacrow);
                           updatedBoxes.clear();
                           boxMaker(0,0,0,updatedBoxes,updatedPuzzle);
                           solveHelper(updatedPuzzle, updatedBoxes, updatedContext, holder);
                           
                        }
                    }
                }
                //if we leave this loop, no possible values at that location are valid
            }
            //if we do need to backtrack
            else{
                count++; //count backtrack for speed calculations
            
            }
        
    }//POTENTIAL OPTIMIZATION: Move boxes.clear() and boxMaker(0,0,0) to the end of the method as we must always do it
    



    //check that the solution to the puzzle is correct by seeing that the summation of every individual box in the puzzle is equal to 45 
    public boolean sudokuChecker(ArrayList<ArrayList<Integer>> boxes){
        int sum;
        for(ArrayList<Integer> i:boxes){
            sum = 0;
            for(int j:i){
                sum += j;
            }
            if (sum != 45){
                System.out.println("Error in box: " + i);
            }
        }
        return true;
    }

    //The sudoku solving algorithm grabs an unsolved puzzle from a file, then brute force steps through every open position and tries every possible legal value
    //The advanced version of this algorithm uses the idea of minimal locations. I.E. location for which the least amount of possibilities exist.
    public ArrayList<ArrayList<Integer>> sudokuSolver(String fileName){
        //set all the key elements to empty matrices and 1 hashmap
        ArrayList<ArrayList<Integer>> puzzle = new ArrayList<>(); //place holder for the evolving puzzle
        ArrayList<ArrayList<Integer>> boxes = new ArrayList<>(9); //convenience for checking box rules
        HashMap<ArrayList<Integer>,ArrayList<Integer>> context = new HashMap<>(81); //structure for saving numerical sum of possibilities by location
        HashMap<ArrayList<Integer>, Set<Set>> worklist = new HashMap<>(81);
        String line = null;
        //fetch the puzzle from the given file and set up the initial puzzle
        try{
            ArrayList<Integer> row = new ArrayList<>();
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine())!= null){
                for(int i=0;i<9;i++){
                    row.add(Character.getNumericValue(line.charAt(i)));
                }
                puzzle.add(new ArrayList<>(row));
                row.clear();
            }
            br.close();
        }

        catch(FileNotFoundException e){
                    System.out.println("File could not be found");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //set final necessary steps and begin.
       
        boxMaker(0,0,0,boxes, puzzle);
        buildContext(context, puzzle, boxes);
        ArrayList<Integer> start = new ArrayList<>(Arrays.asList(0,0));

        solveHelper(puzzle, boxes, context, start);
        return puzzle;
        
        //explicitly disclose whether the puzzle is correct
        //System.out.println(sudokuChecker(boxes));
        //disclose the number of backtracks required to solve the given puzzle
        //System.out.println(count);

    }
	
}