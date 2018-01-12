package skillsassessment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author landini-a
 */
import java.util.*;
import java.io.*;
public class Sudoku {

    private ArrayList<ArrayList<Integer>> puzzle = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<Integer>>> image = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> boxes = new ArrayList<>(9);
    private HashMap<ArrayList<Integer>,ArrayList<Integer>> context = new HashMap<ArrayList<Integer>,ArrayList<Integer>>(81);
    private int count = 0;
    private Rules rules = new Rules();

    private void buildContext(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                ArrayList<Integer> key = new ArrayList<>(2);
                key.add(i);key.add(j);
                context.put(key, new ArrayList<>());

            }
        }
    }

    private void printSud(){
        String output = "";
        for(int i=0;i<9;i++){
            output+="[";
            for(int j=0;j<9;j++){
                if(j == 8){
                    if(i==8 && j==8){
                        output += puzzle.get(i).get(j) +"]";
                        System.out.println(output);
                    }
                    else{ output += puzzle.get(i).get(j);
                    }
                }
                else{
                    output+= puzzle.get(i).get(j) + "|";
                }
            }
            output+="]\n";
        }
    }

    private void boxMaker(Integer i, Integer j, Integer count){
        ArrayList<Integer> box = new ArrayList<>(9);

        if(count == 3){
            i = i+3;
            j = 0;
            count = 0;
        }
        if(i == 9){
            return;
        }
        for(int row = i;row<i+3;row++){

            for(int col = j;col<j+3;col++){
                box.add(puzzle.get(row).get(col));
            }
        }

        boxes.add(box);
        boxMaker(i,j+3,count+1);
    }

    private void imgToPuz(){
        ArrayList<Integer> rowImg = new ArrayList<>();
        puzzle.clear();
        for(int k=0;k<10;k++){
            rowImg = new ArrayList<>(image.get(image.size()-1).get(k));
            puzzle.add(new ArrayList<>(rowImg));
        }
    }

    private void addToImg(){
        ArrayList<Integer> rowImg = new ArrayList<>();
        ArrayList<ArrayList<Integer>> place = new ArrayList<>();
        for(int k=0;k<10;k++){
            rowImg = new ArrayList<>(puzzle.get(k));
            place.add(new ArrayList<>(rowImg));
        }
        image.add(new ArrayList<>(place));
    }


    public void solveHelper(){
        boolean update;
        ArrayList<Integer> holder = new ArrayList<>();
        for(int row = 0; row<9;row++){

            for(int col=0; col<9;col++){

                if(puzzle.get(row).get(col) == 0){
                    update = false;
                    ArrayList<Integer> changeLoc = new ArrayList<>(2);
                    for(int i= 1;i<10;i++){
                        if(rules.checkRules(row, col, i, puzzle, boxes)){
                            if(!context.get(Arrays.asList(row,col)).contains(i)){
                                update = true;
                                changeLoc.clear();
                                changeLoc.add(row); changeLoc.add(col);
                                puzzle.add(new ArrayList<>(changeLoc));

                                holder = new ArrayList<>(context.get(changeLoc));
                                holder.add(i);

                                context.put(changeLoc, holder);

                                addToImg();

                                puzzle.remove(puzzle.size()-1);

                                puzzle.get(row).set(col, i);
                                boxes.clear();
                                boxMaker(0,0,0);
                                break;
                            }
                        }
                            else{}
                    }
                    if(!update){
                        count++;
                        if(context.get(Arrays.asList(row,col)).size() == 9){
                            holder = context.get(Arrays.asList(row,col));
                            holder.clear();
                        }
                        if(image.size() <= 1){
                            context.get(Arrays.asList(row,col)).clear();
                            imgToPuz();
                            puzzle.remove(puzzle.size()-1);

                            image.remove(image.size()-1);

                            row = 0;
                            col = -1;

                            boxes.clear();
                            boxMaker(0,0,0);

                        }
                        else{
                            context.get(Arrays.asList(row,col)).clear();
                            imgToPuz();

                            image.remove(image.size()-1);

                            row = puzzle.get(puzzle.size()-1).get(0);
                            col = puzzle.get(puzzle.size()-1).get(1)-1;

                            puzzle.remove(puzzle.get(puzzle.size()-1));

                            boxes.clear();
                            boxMaker(0,0,0);
                        }
                    }
                }

            }
        }
    }

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


    public void sudokuSolver(String fileName){
        puzzle = new ArrayList<>();
        image = new ArrayList<>();
        boxes = new ArrayList<>(9);
        String line = null;
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
        buildContext();
        boxMaker(0,0,0);
        solveHelper();
        //printSud();
        //System.out.println(sudokuChecker());
        //System.out.println(count);

    }
	
}
