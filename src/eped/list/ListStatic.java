/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eped.list;

import eped.ComparatorIF;
import eped.IteratorIF;

/**
 *
 * @author huertix
 */

public class ListStatic<T> implements ListIF<T>{
    private Object[] elements;
    private int capacity;
    private int first;
    
    public ListStatic(int capacity){
        this.capacity = capacity;
        this.first = capacity;
        this.elements = new Object[capacity];
    }

    public ListStatic (int capacity, ListIF<T> list){
        this (capacity);
        if (list != null){
            ListIF<T> aList = list;
            for (int i = capacity-list.getLength (); i <
                                            capacity ; i++){
                this.elements [i] = aList.getFirst ();
                this.first = this.first - 1;
                aList = aList.getTail ();
            }
        }
    }
    
    private ListIF<T> copy (ListStatic<T> list) {
        ListStatic<T> l = new ListStatic<T> (capacity);
        l.first = list.first;
        l.elements = list.elements;
        return l; 
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public T getFirst (){
        if (isEmpty())
            return null;
        else
            return (T) elements [first];
    }
    
    
    @Override
    public ListIF<T> getTail (){
        if (!isEmpty()) {
            ListStatic<T> tail = (ListStatic<T>) copy (this);
            tail.first = first + 1;
            return tail;
        }else
            return this;
    }
    
    
    @Override
    public ListIF<T> insert (T element){
        if (!isFull ()) {
            first = first - 1;
            elements [first] = element;
        }
        return this;
        
    }

    
    @Override 
    public boolean isEmpty (){ 
        return first == capacity; 
    } 
    
    
    @Override 
    public boolean isFull(){ 
        return first == 0; 
    } 
    
    
    @Override
    public int getLength (){
        return capacity - first;
    }
    
    
    public IteratorIF<T> getIterator (){
        ListIF<T> handler = new ListStatic<T> (capacity, this);
        return new ListIterator<T> (handler);
    }
    
   
    public boolean containsIter(T element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(T element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIF<T> sort(ComparatorIF<T> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public ListIF<T> sortMx(ComparatorIF<T> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public int hashCode () {
        return 31 * 31 * ((elements == null) ? 0 : elements.hashCode ()) +
                                                        31 * capacity + first;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals (Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass () != this.getClass ()) return false;
        else { ListStatic<T> l = (ListStatic<T>) o;
            return l.elements.equals (elements) && l.capacity == capacity &&
                                                               l.first == first; 
        }
    }
    
    @Override
    public String toString (){
        StringBuffer buff = new StringBuffer ();
        buff.append ("ListStatic - [");
        IteratorIF<T> listIt = getIterator ();
        while (listIt.hasNext ()) {
            T element = listIt.getNext ();
            buff.append (element);
            if (listIt.hasNext ())
                buff.append (", ");
        }
        buff.append ("]");
        return buff.toString (); 
    } 


}