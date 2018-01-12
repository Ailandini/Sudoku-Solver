/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package skillsassessment;
import java.util.*;
/**
*
* @author landini
*/
public class AC3 {
    public interface R1Operator{
        public boolean op(int X);
    }
   
    public interface R2Operator{
        public boolean op(int X, int Y);
    }
   
    
    private boolean arc_reduce(Set X, Set Y, R2Operator R2){
        boolean change = false;
        boolean pass;
        Iterator xIter = X.iterator();
        Iterator yIter;
        int x;
        int y;
       
        while(xIter.hasNext()){
            pass = false;
            yIter = Y.iterator();
            x = (int) xIter.next();
            while(yIter.hasNext()){
                y = (int) yIter.next();
                if(R2.op(x, y)){
                    System.out.println("pass "+ x);
                    pass = true;
                }
            }
            if(!pass){
                System.out.println("no pass " + x);
                xIter.remove();
                xIter = X.iterator();
                change = true;
            }
        }
        
        return change;
    }
   
    
    public Set[] AC3(R1Operator R1, R2Operator R2, Set... X){
        for(Set x : X){
            Iterator iter = x.iterator();
            int vx;
            while(iter.hasNext()){
                vx = (int) iter.next();
                if(!R1.op(vx)){
                    iter.remove();
                }
            }
        }
        Set<ArrayList<Set>> worklist = new HashSet<>();
        ArrayList<Set> arc = new ArrayList<>(2);
        boolean first = true;
        for(Set x : X){
            for(Set y: X){
                if(first){
                   first = false;
                }
                else{
                    arc.add(x); arc.add(y);
                    worklist.add(arc);
                    arc = new ArrayList<>(2);
                    arc.add(y); arc.add(x);
                    worklist.add(arc);
                    arc = new ArrayList<>(2);
                }
            }
        }
       
        Iterator wlIter = worklist.iterator();
       
        do{
            arc = new ArrayList<>((ArrayList<Set>) wlIter.next());
            System.out.println("Get Arc");
            wlIter.remove();
            if(arc_reduce(arc.get(0), arc.get(1), R2)){
                if(arc.get(0).isEmpty()){
                    System.out.println("No solution");
                    return null;
                }
                else{
                    ArrayList<Set> newarc = new ArrayList<>(2);
                    for(Set x : X){
                        if(x != arc.get(1) && x!= arc.get(0)){
                            newarc.add(arc.get(0)); newarc.add(x);
                            worklist.add(newarc);
                            newarc = new ArrayList<>(2);
                        }
                    }
                }
            }
            
        }while(!worklist.isEmpty());
       
       
        for(Set x : X){
            System.out.println(x);
        }
        return X;
       
    }
   
    
}
