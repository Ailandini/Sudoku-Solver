/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skillsassessment;

/**
 *
 * @author landini
 */
import java.util.*;
import java.io.*;
public class AdvancedSudoku {

    //Instantiate variables for solver
    private ArrayList<ArrayList<Integer>> puzzle = new ArrayList<>(); //place holder for the evolving puzzle
    private ArrayList<ArrayList<ArrayList<Integer>>> image = new ArrayList<>(); //list of passed puzzles. POTENTIAL OPTIMIZATION: Convert image to stack object
    private ArrayList<ArrayList<Integer>> boxes = new ArrayList<>(9); //convenience for checking box rules
    private HashMap<ArrayList<Integer>,ArrayList<Integer>> context = new HashMap<>(81); //structure for saving numerical sum of possibilities by location
    private int count = 0; //for speed testing purposes
    private final Rules rules = new Rules(); //imported set of sudoku rules
    private boolean solved = false; //end condition boolean
    private boolean noupdate; //backtrack condition boolean
    
    //build the possibilities structure, keys for which are every position in the puzzle
    private void buildContext(){
        //loop through puzzle
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                ArrayList<Integer> key = new ArrayList<>(2);
                //assign index to key
                key.add(i);key.add(j);
                //assign key to context
                context.put(key, new ArrayList<>());

            }
        }
    }

    //standard print method to make ending sudoku aestethic 
    private void printSud(){
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
    private void boxMaker(Integer i, Integer j, Integer count){
        ArrayList<Integer> box = new ArrayList<>(9);
        //if count = 3 (as it starts at 0) we need to jump down 3 rows to the next set of boxes
        if(count == 3){
            i = i+3;
            j = 0;
            count = 0;
        }
        //if we have surpassed all rows of the puzzle. Our box structure is complete.
        if(i == 9){
            return;
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
        boxMaker(i,j+3,count+1);
    }

    //Method that reverts the puzzle to its previous image (backtrack)
    private void imgToPuz(){
        ArrayList<Integer> rowImg = new ArrayList<>();
        //erase the current state of the puzzle
        puzzle.clear();
        //copy the image into the puzzle row by row (this is to insure that no variable points to numerous objects
        for(int k=0;k<9;k++){
            rowImg = new ArrayList<>(image.get(image.size()-1).get(k));
            puzzle.add(new ArrayList<>(rowImg));
        }
    }
    
    //Save an image into the image array.
    private void addToImg(){
        ArrayList<Integer> rowImg = new ArrayList<>();
        //placeholder matrix
        ArrayList<ArrayList<Integer>> place = new ArrayList<>();
        for(int k=0;k<9;k++){
            //copy rows from puzzle
            rowImg = new ArrayList<>(puzzle.get(k));
            //add copied rows to placeholder matrix
            place.add(new ArrayList<>(rowImg));
        }
        //add placeholder matrix to image collection
        image.add(new ArrayList<>(place));
    }

    //check the hashmap for next position to try
    private ArrayList<Integer> findMin(){
        //instantiate useful variables: 
        ArrayList<Integer> location = new ArrayList<>(Arrays.asList(0,0)); //to be returned as next location
        int Min = 9; //placeholder min as looping through puzzle
        boolean isSolved = true; 
        //loop through puzzle
        for(int row=0;row<9;row++){
            for(int col=0;col<9;col++){
                //set up comparison variable
                int temp = 0;
                //if the puzzle contains a gap
                if(puzzle.get(row).get(col) == 0){
                    //the puzzle is not complete
                    isSolved = false;
                    //for possible numbers of the box [row][col]
                    for(int i=1;i<10;i++){
                        //check if i is a potential possibility
                        if(rules.checkRules(row, col, i, puzzle, boxes)){
                            //if it is then check if we've already tried that value
                            if(!context.get(Arrays.asList(row,col)).contains(i)){
                                //if not, then there is one+ possibility for that location
                                temp++;
                           }
                        }
                    } 
                    
                    //once the number of possibilities for a given location are tallied, see if its less than the current lowest
                    if(temp < Min){
                        //if it is, noupdate min and set the location we were checking as the potential minimum
                        Min = temp;
                        location.clear();
                        location = new ArrayList<>(Arrays.asList(row,col));
                    }
                    //if an open position has no possibilities, then alert a backtrack is needed and provide the location at which there is an issue
                    if(Min == 0){
                        noupdate = false;
                        return location;
                    }
                    
                }
                
            }
        }
        //noupdate the global solved to check for completion and return the minimal location
        solved = isSolved;
        return location;
    }

    //Helper method to reduce text traffic in main function method.
    private void solveHelper(){
       
        //loop forever
        while(true){
            //assert there is not a need for backtracking, get the minimal location and assign helpful variables
            noupdate = true;
            ArrayList<Integer> location = findMin();
            ArrayList<Integer> holder = new ArrayList<>();
            int row = location.get(0);
            int col = location.get(1);
            //if findmin discovers the puzzle is complete, return
            if(solved){
                return;
            }
            //if we do not need to backtrack, try a value
            if(noupdate){
                //instantiate a variable for 
                ArrayList<Integer> changeLoc = new ArrayList<>(2);
                //for all possible number 1-10
                for(int i= 1;i<10;i++){
                    //check if a possibility passes all rules
                    if(rules.checkRules(row, col, i, puzzle, boxes)){
                        //check if a possibility has not be tried before
                        if(!context.get(Arrays.asList(row,col)).contains(i)){
                           //clear whatever may have been the chosen location and update it to the current min
                           changeLoc.clear();
                           changeLoc.add(row); changeLoc.add(col);
                           //add the chosen value for the position to the tried values
                           holder = new ArrayList<>(context.get(changeLoc));
                           holder.add(i);

                           context.put(changeLoc, holder);
                           //add the current version of the puzzle to the image map
                           addToImg();
                           //add the value to the puzzle and update the box structure
                           puzzle.get(row).set(col, i);
                           boxes.clear();
                           boxMaker(0,0,0);
                           break;
                        }
                    }
                    else{}
                }
            }
            //if we do need to backtrack
            else{
                count++; //count backtrack for speed calculations
                //if the location has tried every number, clear the location's tried map
                if(context.get(Arrays.asList(row,col)).size() == 9){
                    holder = context.get(Arrays.asList(row,col));
                    holder.clear();
                }
                //if we backtracked to the original puzzle
                if(image.size() <= 1){
                    //clear the possibilities at the current min as above
                    context.get(Arrays.asList(row,col)).clear();
                    //revert the puzzle to its original state
                    imgToPuz();
                    //clear the images array
                    image.remove(image.size()-1);
                    //update the boxes structure to its original state
                    boxes.clear();
                    boxMaker(0,0,0);

                }
                //if we have images to backtrack to before the original puzzle
                else{
                    //clear the possibilities assigned at the position we just tried
                    context.get(Arrays.asList(row,col)).clear();
                    //revert the puzzle to the previous version
                    imgToPuz();
                    //remove the image we just reverted to
                    image.remove(image.size()-1);
                    //update the box structure
                    boxes.clear();
                    boxMaker(0,0,0);
                }
            }
        }
    }//POTENTIAL OPTIMIZATION: Move boxes.clear() and boxMaker(0,0,0) to the end of the method as we must always do it
    



    //check that the solution to the puzzle is correct by seeing that the summation of every individual box in the puzzle is equal to 45 
    public boolean sudokuChecker(){
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
        image = new ArrayList<>(); 
        boxes = new ArrayList<>(9);
        context = new HashMap<>(81);
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
        buildContext();
        boxMaker(0,0,0);
        solveHelper();
        //printSud();
        return puzzle;
        
        //explicitly disclose whether the puzzle is correct
        //System.out.println(sudokuChecker());
        //disclose the number of backtracks required to solve the given puzzle
        //System.out.println(count);

    }
	
}
