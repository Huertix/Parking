/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eped.stack;

import eped.IteratorIF;
import eped.list.ListIF;

/**
 *
 * @author huertix
 */
public class StackStatic <T> implements StackIF<T>{
    private Object[] elements;
    private int capacity;
    private int top;
    
    public StackStatic (int capacity){
        //this.elements = new Object[capacity + 1];
        this.elements = new Object[capacity];
        //this.capacity = capacity + 1;
        this.capacity = capacity;
        this.top= -1;
        //this.top= 0;
    }

    @SuppressWarnings("unchecked")
    public StackStatic (StackIF<T> stack, int capacity){
        this (capacity);
        if (stack != null) {
            for (int i = stack.getLength (); i > 0; i--) {
                T element = stack.getTop ();
                    elements [i] = element;
                    stack.pop ();
                    top = top + 1;
            }
            for (int i = 1; i <= getLength(); i++) {
                T element = (T) elements [i];
                stack.push (element);
            }
        }
    }

    
    public StackStatic (ListIF<T> list, int capacity){
        this (capacity);
        if (list != null) {
            ListIF<T> aList = list;
            for (int index = 0; index < list.getLength (); index++) {
                this.elements [index] = aList.getFirst ();
                aList = list.getTail ();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getTop (){
        if (isEmpty ()) return null;
        return (T) elements [top];
    }
    
    @Override
    public StackIF<T> push (T element){
        if (element != null)
        if (!isFull ()) {
            //elements [top] = element;
            top = top + 1;
            elements [top] = element;
            
            
        }
        return this;
    }
        
        
    @Override
    public StackIF<T> pop (){
        if (!isEmpty ()) {
            top = top - 1;
            
        }
        return this;
    }
    

    @Override
    public boolean isEmpty (){
        return top == -1;
    }


    @Override
    public boolean isFull(){
        return top == (capacity-1);
    }


    @Override
    public int getLength (){
        return top;
    }


    @Override
    public boolean contains (T element){
        IteratorIF<T> stackIt = this.getIterator ();
        boolean found = false;
        while (!found && stackIt.hasNext ()) {
            T anElement = stackIt.getNext ();
            found = anElement.equals (element);
        }
        return found;
    }


    @Override
    public IteratorIF<T> getIterator (){
        StackIF<T> handler = new StackStatic<T> (this, capacity);
        return new StackIterator<T> (handler);
    }

    
    

    
    

}
