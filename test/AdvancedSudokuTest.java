/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skillsassessment;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author landini
 */
public class AdvancedSudokuTest {
    static ArrayList<ArrayList<Integer>> preMat = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> finMat = new ArrayList<>();
    static String output = "";
    public AdvancedSudokuTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        
        ArrayList<Integer> row1 = new ArrayList<>(Arrays.asList(0,5,2,4,9,0,0,0,0)); 
        ArrayList<Integer> row2 = new ArrayList<>(Arrays.asList(3,0,0,1,7,0,0,0,0)); 
        ArrayList<Integer> row3 = new ArrayList<>(Arrays.asList(0,0,7,0,0,0,0,3,0)); 
        ArrayList<Integer> row4 = new ArrayList<>(Arrays.asList(1,0,0,0,6,2,0,4,8)); 
        ArrayList<Integer> row5 = new ArrayList<>(Arrays.asList(0,6,5,0,8,1,2,0));
        ArrayList<Integer> row6 = new ArrayList<>(Arrays.asList(4,2,0,9,1,0,0,0,7)); 
        ArrayList<Integer> row7 = new ArrayList<>(Arrays.asList(0,4,0,0,0,0,7,0,0)); 
        ArrayList<Integer> row8 = new ArrayList<>(Arrays.asList(0,0,3,0,2,7,0,0,6)); 
        ArrayList<Integer> row9 = new ArrayList<>(Arrays.asList(0,0,0,0,4,1,9,5,0));
        preMat.add(row1); preMat.add(row2); preMat.add(row3);
        preMat.add(row4); preMat.add(row5); preMat.add(row6);
        preMat.add(row7); preMat.add(row8); preMat.add(row9);
        
       
        ArrayList<Integer> row10 = new ArrayList<>(Arrays.asList(6,5,2,4,9,3,8,7,1)); 
        ArrayList<Integer> row20 = new ArrayList<>(Arrays.asList(3,8,4,1,7,6,2,9,5)); 
        ArrayList<Integer> row30 = new ArrayList<>(Arrays.asList(9,1,7,2,5,8,6,3,4)); 
        ArrayList<Integer> row40 = new ArrayList<>(Arrays.asList(1,3,9,7,6,2,5,4,8)); 
        ArrayList<Integer> row50 = new ArrayList<>(Arrays.asList(7,6,5,3,8,4,1,2,9));
        ArrayList<Integer> row60 = new ArrayList<>(Arrays.asList(4,2,8,9,1,5,3,6,7)); 
        ArrayList<Integer> row70 = new ArrayList<>(Arrays.asList(5,4,1,6,3,9,7,8,2)); 
        ArrayList<Integer> row80 = new ArrayList<>(Arrays.asList(8,9,3,5,2,7,4,1,6)); 
        ArrayList<Integer> row90 = new ArrayList<>(Arrays.asList(2,7,6,8,4,1,9,5,3));
        finMat.add(row10); finMat.add(row20); finMat.add(row30);
        finMat.add(row40); finMat.add(row50); finMat.add(row60);
        finMat.add(row70); finMat.add(row80); finMat.add(row90);
        
        output = "[6|5|2|4|9|3|8|7|1]\n[3|8|4|1|7|6|2|9|5]\n[9|1|7|2|5|8|6|3|4]\n[1|3|9|7|6|2|5|4|8]\n[7|6|5|3|8|4|1|2|9]\n[4|2|8|9|1|5|3|6|7]\n[5|4|1|6|3|9|7|8|2]\n[8|9|3|5|2|7|4|1|6]\n[2|7|6|8|4|1|9|5|3]";
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    

    /**
     * Test of sudokuChecker method, of class AdvancedSudoku.
     */
    @Test
    public void testSudokuChecker() {
        System.out.println("sudokuChecker");
        AdvancedSudoku instance = new AdvancedSudoku();
        boolean expResult = true;
        boolean result = instance.sudokuChecker();
        assertEquals(expResult, result);
        
    }

    
    /**
     * Test of sudokuSolver method, of class AdvancedSudoku.
     */
    @Test
    public void testSudokuSolver() {
        System.out.println("sudokuSolver");
        String fileName = "sudoku test.txt";
        AdvancedSudoku instance = new AdvancedSudoku();
        ArrayList<ArrayList<Integer>> expResult = new ArrayList<>(finMat);
        ArrayList<ArrayList<Integer>> result = instance.sudokuSolver(fileName);
        assertEquals(expResult, result);
        
    }
    
}
