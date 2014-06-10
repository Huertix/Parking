package eped.tree;

import eped.IteratorIF;
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;
import eped.queue.QueueIterator;
import eped.stack.StackDynamic;
import eped.stack.StackIF;


public class BTreeIterator<T> implements IteratorIF<T> {
	
	private IteratorIF<T> iterator;
	
	
	public BTreeIterator (BTreeIF<T> handler, int type){
		
		QueueIF<T> traverse = null;
		switch (type){
		case BTreeIF.PREORDER: traverse = preorder (handler); break;
		case BTreeIF.INORDER: traverse = inorder (handler); break;
		case BTreeIF.POSTORDER: traverse = postorder (handler); break;
		case BTreeIF.LRBREADTH: traverse = lrBreadth (handler); break;
		case BTreeIF.RLBREADTH: traverse = rlBreadth (handler); break;
		}
		this.iterator = new QueueIterator<T> (traverse);
	}
	
	public T getNext(){
		return iterator.getNext();
	}
	
	public boolean hasNext(){
		return iterator.hasNext();
	}
	
	public void reset(){
		iterator.reset();
	}
	
	private QueueIF<T> preorder (BTreeIF<T> tree){
		QueueIF<T> traverse = new QueueDynamic<T>();
		if(tree == null) return traverse;
		T element = tree.getRoot();
		BTreeIF<T> lTree = tree.getLeftChild();
		BTreeIF<T> rTree = tree.getRightChild();
		QueueIF<T> lTraverse = preorder(lTree);
		QueueIF<T> rTraverse = preorder(rTree);
		traverse.add(element);
		addAll(traverse,lTraverse);
		addAll(traverse,rTraverse);
		return traverse;		
	}
	
	private QueueIF<T> inorder (BTreeIF<T> tree){
		QueueIF<T> traverse = new QueueDynamic<T>();
		if(tree == null) return traverse;
		T element = tree.getRoot();
		BTreeIF<T> lTree = tree.getLeftChild();
		BTreeIF<T> rTree = tree.getRightChild();
		QueueIF<T> lTraverse = inorder(lTree);
		QueueIF<T> rTraverse = inorder(rTree);
		addAll(traverse,lTraverse);
		traverse.add(element);
		addAll(traverse,rTraverse);
		return traverse;		
	}
	
	private QueueIF<T> postorder (BTreeIF<T> tree){
		QueueIF<T> traverse = new QueueDynamic<T>();
		if(tree == null) return traverse;
		T element = tree.getRoot();
		BTreeIF<T> lTree = tree.getLeftChild();
		BTreeIF<T> rTree = tree.getRightChild();
		QueueIF<T> lTraverse = postorder(lTree);
		QueueIF<T> rTraverse = postorder(rTree);
		addAll(traverse,lTraverse);
		addAll(traverse,rTraverse);
		traverse.add(element);
		return traverse;		
	}
	
	private QueueIF<T> lrBreadth (BTreeIF<T> tree){
		QueueIF<T> traverse = new QueueDynamic<T>();
		QueueIF<BTreeIF<T>> aux = new QueueDynamic<BTreeIF<T>>();
		aux.add(tree);
		
		while(!aux.isEmpty()){
			BTreeIF<T> aTree = aux.getFirst();
			T element = aTree.getRoot ();
			BTreeIF<T> lTree = aTree.getLeftChild();
			BTreeIF<T> rTree = aTree.getRightChild();
			if(lTree != null) aux.add(lTree);
			if(rTree != null) aux.add(rTree);
			traverse.add(element);
			aux.remove();
		}
		return traverse;		
	}
	
	private QueueIF<T> rlBreadth (BTreeIF<T> tree){
		QueueIF<T> traverse = lrBreadth(tree);
		StackIF<T> aux = new StackDynamic<T> ();
		
		while(!traverse.isEmpty()){
			aux.push(traverse.getFirst());
			traverse.remove();
		}
		
		while(!aux.isEmpty()){
			traverse.add(aux.getTop());
			aux.pop();
		}
		return traverse;
		
	}
	
	
	private void addAll(QueueIF<T> p, QueueIF<T> q){
		
		while(!q.isEmpty()){
			p.add(q.getFirst());
			q.remove();
		}
	}

}
