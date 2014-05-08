/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eped.list;


import eped.IteratorIF;


/**
 *
 * @author huertix
 */
public class ListIterator<T> implements IteratorIF<T> {

    private ListIF<T> handler;
    private ListIF<T> restart;

    /**
    * Constructor para ListIterator.
    * @param handler el manejador de listas.
    */
    public ListIterator(ListIF<T> handler){
        this.handler = handler;
        this.restart = handler;   
    }
    
    
    /**
    * Devuelve cierto si existen mas elementos a iterar.
    * @return cierto si existen mas elementos a iterar.
    */
    @Override
    public boolean hasNext(){
        return !handler.isEmpty ();    
    }

    
    /**
    * Devuelve el siguiente elemento de la iteracion.
    * @return el siguiente elemento de la iteracion.
    */
    @Override
    public T getNext(){
        T next = handler.getFirst ();
        handler = handler.getTail ();
        return next;       
    }

    
    /**
    * Restablece el iterador para volver al inicio.
    */
    @Override
    public void reset (){
        handler = restart;
    }
           
}
