package eped.tree;

import java.util.Iterator;

import eped.IteratorIF;

public class BTreeDynamic<T> implements BTreeIF<T> {
	
	private T root;
	private	BTreeIF<T> left;
	private BTreeIF<T> right;
	
	public BTreeDynamic(){
		this.root = null;
		this.left = null;
		this.right = null;
	}


	public BTreeDynamic(T element){
		this();
		this.setRoot(element);
	}
	
	public BTreeDynamic(BTreeIF<T> tree){
		this();
		T tRoot = tree.getRoot();
		BTreeIF<T> tLeft = tree.getLeftChild();
		BTreeIF<T> tRight = tree.getRightChild();
		
		this.setRoot(tRoot);
		this.setLeftChild(new BTreeDynamic<T> (tLeft));
		this.setRightChild(new BTreeDynamic<T> (tRight));
	}
	
	
	public T getRoot(){
		return this.root;
	}
	
	public BTreeIF <T> getRightChild(){
		return this.right;
	}
	
	public BTreeIF <T> getLeftChild(){
		return this.left;
	}
	
	public void setRoot (T element){
		if(element != null)
			this.root = element;
	}
	
	public void setLeftChild (BTreeIF<T> tree){
		this.left = tree;
	}
	
	public void setRightChild(BTreeIF<T> tree){
		this.right = tree;
	}
	
	public void removeLeftChild(){
		this.left = null;
		
	}
	
	public void removeRightChild(){
		this.right = null;
	}
	
	public boolean isLeaf(){
		return this.root != null && left==null && right==null;
	}
	
	public boolean isEmpty(){
		return this.root == null;
		
	}
	
	public boolean contains(T element){
		return this.root.equals(element) || this.left.contains(element) || this.right.contains(element);
		
	}
	
	public boolean containsPreorden(T element){
		if(element == null) return false;
		return element.equals(getRoot()) || 
				(getLeftChild() != null)? getLeftChild().contains(element) : false ||
				(getRightChild() != null)? getRightChild().contains(element) : false;
	}
	
	public boolean containsCentinela(T element){
		
		boolean found = false;
		IteratorIF<T> it = getIterator (BTreeIF.INORDER);
		
		while(!found && it.hasNext()){
			T anElement = it.getNext();
			found = anElement.equals(element);
		}
		
		return found;
	}
	
	public IteratorIF<T> getIterator(int type){
		BTreeIF<T> handler = new BTreeDynamic<T> (this);
		return new BTreeIterator<T> (handler, type);
	}
	
	public int hashCode(){
		return 31 * 31 * ((root == null) ? 0 : root.hashCode()) +
					31 * ((left == null) ? 0 : left.hashCode()) + 
					31 * ((right == null)? 0 : right.hashCode());
	}


	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setHeight(int i) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public BTreeIF<T> findMin() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BTreeIF<T> findMax() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BTreeIF<T> insert(T element) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BTreeIF<T> remove(T element) {
		// TODO Auto-generated method stub
		return null;
	}
}
