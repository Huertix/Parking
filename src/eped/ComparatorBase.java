/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eped;

/**
 *
 * @author huertix
 */
public abstract class ComparatorBase<T> implements ComparatorIF<T>{
   

    @Override
    public int compare(T e1,T e2) {
        
        if(isLess(e1,e2))
            return -1;
        if(isEqual(e1,e2))
            return 0;
        else
            return 1;
        
    }
    

    @Override
    public boolean isLess (T e1, T e2){     
        return (Integer)e1<(Integer)e2;
    }
    
    @Override
    public boolean isEqual (T e1, T e2){
        return e1.equals(e2);      
    }
    
    @Override
    public boolean isGreater (T e1, T e2){
       return (Integer)e1>(Integer)e2;
    }
    
}
