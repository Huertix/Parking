/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eped.queue;

import eped.IteratorIF;

/**
 *
 * @author huertix
 */
public interface QueueIF<Object> {
    
    /**
* Devuelve la cabeza de la cola.
* @return la cabeza de la cola.
*/
public Object getFirst ();

/**
* Inserta un nuevo elemento a la cola.
* @param element El elemento a añadir.
* @return la cola incluyendo el elemento.
*/
public QueueIF<Object> add (Object element);

/**
* Borra la cabeza de la cola.
* la cola excluyendo la cabeza.
*/
public QueueIF<Object> remove ();

/**
* Devuelve cierto si la cola esta vacia.
* @return cierto si la cola esta vacia.
*/
public boolean isEmpty ();

/**
* Devuelve cierto si la cola esta llena.
* @return cierto si la cola esta llena.
*/
public boolean isFull();

/**
* Devuelve el numero de elementos de la cola.
* @return el numero de elementos de la cola.
*/
public int getLength ();

/**
* Devuelve cierto si la cola contiene el elemento.
* @param element El elemento buscado.
* @return cierto si la cola contiene el elemento.
*/
public boolean contains (Object element);

/**
* Devuelve un iterador para la cola.
* @return un iterador para la cola.
*/
public IteratorIF getIterator ();
   
}
