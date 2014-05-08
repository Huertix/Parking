package eped;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author huertix
 */
public interface IteratorIF<T>{

    
    /**
    * Devuelve el siguiente elemento de la iteracion.
    * @return el siguiente elemento de la iteracion.
    */
    public boolean hasNext();
    
    /**
    * Devuelve el siguiente elemento de la iteracion.
    * @return el siguiente elemento de la iteracion.
    */
    public T getNext(); 
    
    /**
    * Restablece el iterador para volver al inicio.
    */
    public void reset();
    
    @Override
    public int hashCode();
    
    @Override
    public boolean equals(Object o);
    
    @Override
    public String toString();
       
}
