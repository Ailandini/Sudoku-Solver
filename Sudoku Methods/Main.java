/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skillsassessment;
import java.util.*;
/**
 *
 * @author landini-a
 */
public class Main {
    

    public static void main(String[] args) throws InterruptedException{
        // TODO code application logic here
        //Checkers myCheck = new Checkers();
        //int[][] board = {{1,1,0,2,0},{0,1,2,1,2},{1,2,0,2,1}};
        //int[] pos = {2,0};
        //System.out.println(myCheck.jumpCounter(board, pos));
        /*
        Sudoku s = new Sudoku();
        AdvancedSudoku as = new AdvancedSudoku();
        File dir = new File("sudoku puzzles");
        File[] dirlist = dir.listFiles();
        long[] slowTimes = new long[60];
        long[] fastTimes = new long[60];
        int ct = 0;
        
        while(ct < 15){
            for(File i:dirlist){
                long start = System.currentTimeMillis();
                as.sudokuSolver(i.getName());
                long end = System.currentTimeMillis();
                fastTimes[ct] = end - start;
                start = System.currentTimeMillis();
                s.sudokuSolver(i.getName());
                end = System.currentTimeMillis();
                slowTimes[ct] = end-start;
                ct++;
            }
        }
        long slowAvg = 0;
        for(long i: slowTimes){
            slowAvg+= i;
        }
        slowAvg = slowAvg/slowTimes.length;
        
        long fastAvg = 0;
        for(long i: fastTimes){
            fastAvg+=i;
        }
        fastAvg = fastAvg/fastTimes.length;
        
        System.out.println("Avg fast time: " + fastAvg);
        System.out.println("Avg slow time: " + slowAvg);
        */
        //AdvancedSudoku as = new AdvancedSudoku();
        //System.out.println(as.sudokuSolver("sudoku test.txt"));
        //RecursiveSudoku rs = new RecursiveSudoku();
        //rs.sudokuSolver("sudoku puzzle4.txt");
        //AC3Sudoku acs = new AC3Sudoku();
        //acs.sudokuSolver("sudoku puzzle4.txt");
 
        
        
        
        
        AC3 ac = new AC3();
        
        AC3.R1Operator R1 = (int x) -> {
            return x%2 == 0;
        };
        
        AC3.R2Operator R2 = (int x, int y) -> {
            return x + y == 4;
        };
        
        ArrayList<Integer> l1 = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
        ArrayList<Integer> l2 = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
        ArrayList<Integer> l3 = new ArrayList<>(Arrays.asList(2,4,5,6));
        Set s1 = new HashSet<>(l1);
        Set s2 = new HashSet<>(l2);
        Set s3 = new HashSet<>(l3);
        
        ac.AC3(R1, R2, s1,s2,s3);
        
        
        
        
        //AdditionUI gui = new AdditionUI();
        //gui.setVisible(true);
        //MineSweeper ms = new MineSweeper();
        //ms.setVisible(true);
     
    }
    
}
