/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eped.tree;

import eped.IteratorIF;
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;
import eped.queue.QueueIterator;
import eped.stack.StackDynamic;
import eped.stack.StackIF;

/**
 *
 * @author huertix
 */
public class TreeIterator<T> implements IteratorIF<T>{
    
    private IteratorIF<T> iterator;

    
    /**
    * Constructor para TreeIterator.
    * @param handler el manejador de arboles.
    * @param type el tipo de recorrido.
    */
    public TreeIterator (TreeIF<T> handler, int type){
        
        QueueIF<T> traverse = null;
        switch (type) {
            case TreeIF.PREORDER: traverse = preorder (handler);break;
            case TreeIF.POSTORDER: traverse = postorder (handler);break;
            case TreeIF.LRBREADTH: traverse = lrBreadth (handler);break;
            case TreeIF.RLBREADTH: traverse = rlBreadth (handler);break;
        }
        this.iterator = new QueueIterator<T> (traverse);
    }
    
    /**
    * Devuelve el siguiente elemento de la iteracion.
    * @return el siguiente elemento de la iteracion.
    */
    @Override
    public T getNext (){
        return iterator.getNext ();
    }
    
    /**
    * Devuelve cierto si existen mas elementos en el iterador.
    * @return cierto si existen mas elementos en el iterador.
    */
    @Override
    public boolean hasNext (){
        return iterator.hasNext ();
    }
    
    
    /**
    * Restablece el iterador para volver a recorrer la estructura.
    */
    @Override
    public void reset () {
        iterator.reset ();
    }
    
    
    private QueueIF<T> preorder (TreeIF<T> tree){
        QueueIF<T> traverse = new QueueDynamic<T> ();
        T element = tree.getRoot ();
        traverse.add (element);
        IteratorIF <TreeIF <T>> childrenIt = tree.getChildren ().getIterator ();
        while (childrenIt.hasNext ()) {
            TreeIF <T> aChild = childrenIt.getNext ();
            QueueIF <T> aTraverse = preorder (aChild);
            addAll (traverse, aTraverse);
        }
        return traverse;
    }
    
    private QueueIF<T> postorder (TreeIF<T> tree){
        QueueIF<T> traverse = new QueueDynamic<T> ();
        T element = tree.getRoot ();
        IteratorIF <TreeIF <T>> childrenIt = tree.getChildren ().getIterator ();
        while (childrenIt.hasNext ()) {
            TreeIF <T> aChild = childrenIt.getNext ();
            QueueIF <T> aTraverse = postorder (aChild);
            addAll (traverse, aTraverse);
        }
        traverse.add (element);
        return traverse;
    }

    
    
    private QueueIF<T> lrBreadth (TreeIF<T> tree) {
        QueueIF<T> traverse = new QueueDynamic <T> ();
        QueueIF<TreeIF<T>> aux = new QueueDynamic <TreeIF<T>> ();
        aux.add (tree);
        while (!aux.isEmpty ()) {
            TreeIF<T> aTree = aux.getFirst();
            T element = aTree.getRoot();
            IteratorIF <TreeIF <T>> childrenIt = aTree.getChildren ().getIterator ();
            while (childrenIt.hasNext ()) {
                TreeIF <T> aChild = childrenIt.getNext ();
                aux.add (aChild);
            }
            traverse.add (element);
            aux.remove ();
        }
        return traverse;
    }
    
    
    private QueueIF<T> rlBreadth (TreeIF<T> tree){
        QueueIF<T> traverse = lrBreadth (tree);
        StackIF<T> aux = new StackDynamic<T> ();
        while (!traverse.isEmpty ()) {
            T element = traverse.getFirst ();
            aux.push (element);
            traverse.remove ();
        }
        while (!aux.isEmpty ()) {
            T element = aux.getTop ();
            traverse.add (element);
            aux.pop ();
        }
        return traverse;
    }
    
    
    private void addAll (QueueIF<T> q, QueueIF<T> p){
        while (!p.isEmpty ()) {
            T element = p.getFirst ();
            q.add (element);
            p.remove ();
        }
    }
    
}
