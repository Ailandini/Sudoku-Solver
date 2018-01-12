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
public class Rules {
    public boolean checkRules(Integer row, Integer col, Integer candidate, ArrayList<ArrayList<Integer>> puzzle, ArrayList<ArrayList<Integer>> boxes){
        
        //Rule 1:
        if(puzzle.get(row).contains(candidate)){
            return false;
        }   
        //Rule 2:
        for(int i =0;i<9;i++){
            if(candidate == puzzle.get(i).get(col)){
                return false;
            }
        }
        
        //Rule 3:
        if(row<3&&col<3){
            if(boxes.get(0).contains(candidate)){
                return false;
            } else {
            }
        }
        if(row<3&&col>=3&&col<6){
            if(boxes.get(1).contains(candidate)){
                return false;
            }
        }
        if(row<3&&col>=6&&col<9){
            if(boxes.get(2).contains(candidate)){
                return false;
            }
        }
        if(row>=3&&row<6&&col<3){
            if(boxes.get(3).contains(candidate)){
                return false;
            }
        }
        if(row>=3&&row<6&&col>=3&&col<6){
            if(boxes.get(4).contains(candidate)){
                return false;
            }
        }
        if(row>=3&&row<6&&col>=6&&col<9){
            if(boxes.get(5).contains(candidate)){
                return false;
            }
        }
        if(row>=6&&row<9&&col<3){
            if(boxes.get(6).contains(candidate)){
                return false;
            }
        }
        if(row>=6&&row<9&&col>=3&&col<6){
            if(boxes.get(7).contains(candidate)){
                return false;
            }
        }
        if(row>=6&&row<9&&col>=6&&col<9){
            if(boxes.get(8).contains(candidate)){
                return false;
            }
        }
        
        
        return true;
    }
}
