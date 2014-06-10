package eped.tree;

import eped.ComparatorBase;

public class BTreeSearch<T> extends BTreeDynamic<T> implements BTreeIF<T>{
	
	BTreeIF<T> bTree;
	
	Comparator comp = new Comparator();
	
	public BTreeSearch(){
		bTree = new BTreeDynamic<T>();
	}
	
	public BTreeSearch(T element){
		bTree = new BTreeDynamic<T>(element);
	}
	
	public BTreeIF<T> getBTree(){
		return bTree;
	}
	

	public BTreeIF<T> insert(T element){
		return null;
	}
	

	
	public boolean insert(T element, BTreeIF<T> tree){
		
		boolean inserted = false;
		
		
		if(tree.getRoot() == null){
			tree.setRoot(element);
			tree.setLeftChild(new BTreeSearch());
			tree.setRightChild(new BTreeSearch());
			inserted = true;
		}
		
		else if(tree.getRoot().equals(element))
			inserted = false;
		
		
		
			 
		
		else if(comp.isGreater(tree.getRoot(), element))
			inserted =  insert(element, tree.getLeftChild());
		
		else
			inserted = insert(element,tree.getRightChild());		
		
		return inserted;
	}
	
	
	
	public BTreeIF<T> remove(T element){
		
		this.bTree = remove(element, bTree);
		int a = 0;
		
		return null;
		
	}
	
	private BTreeIF<T> remove(T element, BTreeIF<T> tree){
		
		if(tree==null || tree.isEmpty()){
			return tree;
		}
		
		else if(tree.getRoot().equals(element)){
			
			if((tree.getLeftChild()==null || tree.getLeftChild().isEmpty()) && (tree.getRightChild()==null || tree.getRightChild().isEmpty()))
				return null;
			
			else if(tree.getLeftChild()==null || tree.getLeftChild().isEmpty()){
				//tree = tree.getRightChild();
				return tree.getRightChild();
			}
			
			else if(tree.getRightChild()==null || tree.getRightChild().isEmpty()){
				return tree.getLeftChild();
			}
			
			else{
				T maxValueLeftChild = findMax(tree.getLeftChild()).getRoot();
				tree.setRoot(maxValueLeftChild);
				tree.setLeftChild(remove(maxValueLeftChild,tree.getLeftChild()));
			}
		}
		
		else if(comp.isGreater(tree.getRoot(), element)){
			tree.setLeftChild(remove(element, tree.getLeftChild()));
			
		}
		
		else{
			//return remove(element,tree.getRightChild());
			tree.setRightChild(remove(element, tree.getRightChild()));
		
		}
		return tree;
		
		

		
	}
	
	
	public BTreeIF<T> finMin(){
		return findMin(bTree);
	}
	
	private BTreeIF<T> findMin(BTreeIF<T> tree){
		
		if(tree.getLeftChild().getRoot()==null) return tree;
		
		else
			return findMin(tree.getLeftChild());
		
	}
	
	
	public BTreeIF<T> findMax(){
		return findMax(bTree);
	}
	
	private BTreeIF<T> findMax(BTreeIF<T> tree){
		if(tree.getRightChild().getRoot()==null) return tree;
		
		else
			return findMax(tree.getRightChild());
		
	}
	
	

	
	public BTreeIF<T> find(T element, BTreeIF<T> tree){
		if( tree == null)
			return null;
		
		else if(tree.getRoot()==element)
			return tree;
		
		else if(comp.isGreater(tree.getRoot(), element))
			return find(element, tree.getLeftChild());
		
		else
			return find(element,tree.getRightChild());
				
	}
	
	
	
	private class Comparator extends ComparatorBase<T>{
		
		private Comparator(){
			super();
		}
		
	}

}
