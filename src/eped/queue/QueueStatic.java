/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eped.queue;

import eped.IteratorIF;
import eped.list.ListIF;

/**
 *
 * @author huertix
 */
public class QueueStatic <T> implements QueueIF <T>{
    
    private Object[] elements;
    private int capacity;
    private int first;
    private int last;
    
    public QueueStatic (int capacity){
        this.elements = new Object [capacity + 1];
        this.capacity = capacity + 1;
        this.first = 0;
        this.last = 0;

    }
    
    public QueueStatic (QueueIF<T> queue, int capacity){
        this (capacity);
        if (queue != null)
            for (int i = 0; i < queue.getLength (); i++) {
                T element = queue.getFirst ();
                add (element);
                queue.remove ();
                queue.add (element);
            }
    }
    
    
    public QueueStatic (ListIF<T> list, int capacity){
        this (capacity);
        if (list != null) {
            ListIF<T> l = list;
            while (!l.isEmpty ()){
                add (l.getFirst ());
                l = l.getTail ();
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public T getFirst (){
        if (isEmpty ()) return null;
        return (T) elements [first];
    }
    
    
    @Override
    public QueueStatic<T> add (T element){
        if (element != null) {
            if (!isFull ()) {
                elements [last] = element;
                last = next (last);
            }
        }
        return this;   
    }
    
    @Override
    public QueueStatic<T> remove (){
        if (! isEmpty ()) {
            first = next (first);
        }
        return this;
    }
    
    
    @Override
    public boolean isEmpty () {
        return first == last;
    }
    
    
    @Override
    public boolean isFull() {
        return next(last) == first ;

    }
    
    
    @Override
    public int getLength () {
        if (first <= last) return last - first;
        else return capacity - (first - last);
    }
    
    
    private int next (int index){
        return (index + 1) % capacity;
    }
    
    
    @Override
    public boolean contains (T element){
        boolean found = false;
        int index = first;
        while (!found && Math.abs (last - index) > 0) {
            found = elements [index].equals (element);
            index = next (index);
        }
        return found;
    }
    
    
    @Override
    public IteratorIF<T> getIterator (){
        QueueIF<T> handler = new QueueStatic<T> (this, capacity);
        return new QueueIterator<T> (handler);
    }
    
    @Override
    public int hashCode () { return 0;}
    
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals (Object o) {return false;}
    
    
    @Override
    public String toString () {return null;}

     
}
