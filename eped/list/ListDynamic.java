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
public class ListDynamic<T> implements ListIF<T> {
    private T first;
    private ListIF<T> tail;

    
    public ListDynamic(){
        first = null;
        tail = null;
    }
   
    
    public ListDynamic(ListIF<T> list){
        this();
        if (list != null){
            if (!list.isEmpty()){
                first = list.getFirst ();
                tail = new ListDynamic<T> (list.getTail());
            }
        }
    }
    
    
    @Override
    public T getFirst(){
        return first;
    }


    @Override
    public ListIF<T> getTail(){
        if (isEmpty ())
            return this;
        else
            return tail;
    }


    @Override
    public ListIF<T> insert (T element){
        if (element != null) {
            ListDynamic<T> next = new ListDynamic<T>();
            next.first = first;
            next.tail = tail;
            first = element;
            tail = next;
        }
        return this;
    }
    
    


    @Override
    public boolean isEmpty (){
        return first == null && tail == null;
    }


    @Override
    public boolean isFull () {
        return false;
    }


    @Override
   public int getLength (){    
        if (isEmpty ()) 
            return 0;
        else 
            return 1 + tail.getLength ();
   }


    public IteratorIF<T> getIterator (){
        ListIF<T> handler = new ListDynamic<T>(this);
        return new ListIterator<T>(handler);
    }

    

    /**
    * Devuelve cierto si la lista contiene el elemento.
    * Metodo Iterativo.
    * @param element El elemento buscado.
    * @return cierto si la lista contiene el elemento.
    */
    
    public boolean containsIter (T element){
        IteratorIF<T> listIt = this.getIterator ();
        boolean found = false;
        while (!found && listIt.hasNext ()) {
            T anElement = listIt.getNext ();
            found = anElement.equals (element);
        }
        return found;
    }
    
    /**
    * Devuelve cierto si la lista contiene el elemento.
    * Metodo Recursivo.
    * @param element El elemento buscado.
    * @return cierto si la lista contiene el elemento.
    */
    @Override
    public boolean contains (T element){
        if (isEmpty ()) return false;
        return first.equals (element) || tail.contains (element);
    }



    /**
    * Ordena los elementos de la lista por inserción.
    * @param comparator El comparador de elementos.
    * @return la lista ordenada.
    */
    @Override
    public ListIF<T> sort (ComparatorIF<T> comparator){
        if (isEmpty ()) return this;
        else return ((ListDynamic<T>) tail.sort (comparator)).
                                                sortInsert(first, comparator);
    }
    
    /**
    * Ordena los elementos de la lista por Mezcla.
    * @param element El comparador de elementos.
    * @return la lista ordenada.
    */
    
    public ListIF<T> sortMx (ComparatorIF<T> comparator){
        if (getLength () <= 1) return this;
        else {
            int middle = (int) (getLength () / 2);
            int index = 0;
            ListIF<T> lLeft = new ListDynamic<T> ();
            ListIF<T> lRight = new ListDynamic<T> ();
            IteratorIF<T> listIt = getIterator ();
            while (listIt.hasNext ()) {
                T element = listIt.getNext ();
                if (index < middle) lLeft.insert (element);
                if (index >= middle) lRight.insert (element);
                index = index + 1;
            }
            lLeft = lLeft.sortMx (comparator);
            lRight = lRight.sortMx (comparator);
            return sortMerge (lLeft, lRight, comparator);
        }
    }

    
    /**
    * Inserta un elemento en orden en una lista ordenada.
    * @param element El elemento a insertar
    * @param comparator El comparador de elementos.
    * @return la lista ordenada.
    */
    private ListIF<T> sortInsert (T element, ComparatorIF<T> comparator){
        if (isEmpty ()) return this.insert (element);
        else if (comparator.isLess (element, first))
            return this.insert(element);
        else return ((ListDynamic<T>) tail).sortInsert (element, comparator).
                                                                insert (first);
    }
    
    
    private ListIF<T> sortMerge (ListIF<T> lLeft, ListIF<T> lRight,
                                                    ComparatorIF<T> comparator){
        
        ListDynamic<T> result = new ListDynamic<T> ();
        while (lLeft.getLength () > 0 || lRight.getLength () > 0) {
            if (lLeft.getLength () > 0 && lRight.getLength () > 0) {
                T eLeft = lLeft.getFirst ();
                T eRight = lRight.getFirst ();
                if (comparator.isGreater (eLeft, eRight)) {
                    result.insert (eLeft);
                    lLeft = lLeft.getTail ();
                } else {
                    result.insert (eRight);
                    lRight = lRight.getTail ();
                }
            } else if (lLeft.getLength () > 0) {
                T eLeft = lLeft.getFirst ();
                result.insert (eLeft);
                lLeft = lLeft.getTail ();
            } else if (lRight.getLength () > 0) {
                T eRight = lRight.getFirst ();
                result.insert (eRight);
                lRight = lRight.getTail ();
            }
        }
        return result;
    }

   
}
