 /*
 * Clase que implementa las caracteristicas de los arboles binarios AVL.
 * Esta clase esta modificada para cumplir las espectativas de la gestión 
 * del turno de salidas de los vehículos.
 * 
 * La función, basicamente, es la de insertar los vehiculos de forma ordenada
 * teniendo en cuenta su tiempo de salida. Es por esta razón que los vehículos 
 * con menor tiempo se ubicar a la izquierda del arbol.
 * 
 * En caso de empate en tiempo, se verifica a que planta pertenece cada elemento 
 * para poder asignarlo a la hoja derecho o izquierda. De esta forma la salida de
 * elementos cuando se requiera siempre será ordenada.
 * 
 *Para la recuperación del primer vehñiculo en salir del parking se utiliza el
 *método findMin(); 
 *
 */


package eped.parking.vehicle;

import eped.ComparatorBase;
import eped.IteratorIF;

import eped.tree.BTreeDynamic;
import eped.tree.BTreeIF;
import eped.tree.BTreeIterator;




/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class VehicleTree implements BTreeIF<Vehicle>{
	
	Vehicle root;
	BTreeIF<Vehicle> left;
	BTreeIF<Vehicle> right;
	int height;
	
	public VehicleTree(){
		this.root = null;
		this.left = null;
		this.right = null;
	}
	
	public VehicleTree(Vehicle v){
		this();
		this.setRoot(v);
		
	}
	

	
	
	/**
	 * @return Retorna la estrutura arborera actualizada con el nuevo elemento insertado
	 */
	public BTreeIF<Vehicle> insert (Vehicle element){
		return insert(element, this);
	}
	
	
	
	
	/**
	 * Método auxiliar recurviso para la insercción de elementos
	 * @param element Elemento a insertar
	 * @param tree estructura arborera 
	 * @return Retorna la estrutura arborera actualizada con el nuevo elemento insertado 
	 */
	private BTreeIF<Vehicle> insert(Vehicle element, BTreeIF<Vehicle> tree){
		
		if(tree==null || tree.isEmpty()) return new VehicleTree(element);
		
		Comparator<Integer> comp =  new Comparator<Integer>();
		
		if(comp.isLess(element.getTimeToGo(), tree.getRoot().getTimeToGo())){
			
			tree.setLeftChild(insert(element,tree.getLeftChild()));
		}
		
		else if(comp.isGreater(element.getTimeToGo(), tree.getRoot().getTimeToGo())){
	
			tree.setRightChild(insert(element,tree.getRightChild()));
		}
		
		else{
			if(element.getSpace().getFloor()<tree.getRoot().getSpace().getFloor())
				tree.setLeftChild(insert(element,tree.getLeftChild()));
			
			else{
				tree.setRightChild(insert(element,tree.getRightChild()));
			}				
		}		
		return balance(tree);
	}
	
	
	
	
	/**
	 * @param element Elemento a eliminar
	 * @return Retorna la estrutura arborera actualizada depues de borrar el elemento
	 */
	public BTreeIF<Vehicle> remove(Vehicle element){
		return remove(element, this);
	}
	
	
	private BTreeIF<Vehicle> remove(Vehicle element, BTreeIF<Vehicle> tree){
		if(tree==null || tree.isEmpty()) return tree;
		
		Comparator<Integer> comp =  new Comparator<Integer>();
		
		if(comp.isLess(element.getTimeToGo(), tree.getRoot().getTimeToGo())){
				tree.setLeftChild(remove(element, tree.getLeftChild()));
		}
		
		
		else if(comp.isGreater(element.getTimeToGo(), tree.getRoot().getTimeToGo())){
			tree.setRightChild(remove(element,tree.getRightChild()));
		}
		
		else if(tree.getLeftChild()!=null && tree.getRightChild()!= null){
			
			if(tree.getLeftChild().getRoot().getTimeToGo()==element.getTimeToGo()){
				tree.setLeftChild(remove(element, tree.getLeftChild()));
			}
			
			else if(tree.getRightChild().getRoot().getTimeToGo()==element.getTimeToGo()){
				tree.setRightChild(remove(element, tree.getRightChild()));
			}
			
			else{
				tree.setRoot(findMin(tree.getRightChild()).getRoot());
				tree.setRightChild(remove(tree.getRoot(),tree.getRightChild()));
			}
		}
	
		
		else
			tree = (tree.getLeftChild() != null) ? tree.getLeftChild() : tree.getRightChild();
		
		
		return balance(tree);
	}

	
	
	/**
	 * @return Retorna la estrutura arborera balanceada
	 */
	public BTreeIF<Vehicle> balance(){
		return balance(this);
	}
	
	public BTreeIF<Vehicle> balance(BTreeIF<Vehicle> tree){
		
		if(tree == null) return tree;
		
		int left = getHeight(tree.getLeftChild());
		int right = getHeight(tree.getRightChild());
		
		if( left - right > 1){
			
			int lLeft = getHeight(tree.getLeftChild().getLeftChild());
			int lRight = getHeight(tree.getLeftChild().getRightChild());
			if( lLeft >= lRight){
				tree = rotateWithLeftChild(tree);
			}
			
			else{
				tree = doubleWithLeftChild(tree);
			}
		}
		
		else if(getHeight(tree.getRightChild()) - getHeight(tree.getLeftChild()) > 1){
			if(getHeight(tree.getRightChild().getRightChild()) >= getHeight(tree.getRightChild().getLeftChild())){
				tree = rotateWithRightChild(tree);
			}
			
			else{
				tree = doubleWithRightChild(tree);
			}	
			
		}
		else{}
		
		tree.setHeight(Math.max(getHeight(tree.getLeftChild()),getHeight(tree.getRightChild())) + 1);
		return tree;
		
		
		
	}

	
	/**
	 * Rotación simple con hijo izquierdo
	 * 
	 * @param tree
	 * @return
	 */
	public BTreeIF<Vehicle> rotateWithLeftChild(BTreeIF<Vehicle> tree){
		BTreeIF<Vehicle> leftTree =tree.getLeftChild();
		tree.setLeftChild(leftTree.getRightChild());
		leftTree.setRightChild(tree);

		int height = Math.max( getHeight(tree.getLeftChild()), getHeight(tree.getRightChild())) +1;
		tree.setHeight(height);

		height = Math.max(getHeight(tree.getLeftChild()), tree.getHeight()) + 1;
		leftTree.setHeight(height);
		
		return leftTree;
	}
	
	/**
	 * Rotación doble con hijo izquierdo
	 * @param tree
	 * @return
	 */
	public BTreeIF<Vehicle> doubleWithLeftChild(BTreeIF<Vehicle> tree){
		tree.setLeftChild(rotateWithRightChild((BTreeIF<Vehicle>) tree.getLeftChild()));
		return rotateWithLeftChild( (BTreeIF<Vehicle>) tree);
	}
	

	/**
	 * Rotación simple con hijo derecho
	 * @param tree
	 * @return
	 */
	public BTreeIF<Vehicle> rotateWithRightChild(BTreeIF<Vehicle>tree){
		BTreeIF<Vehicle> rightTree = tree.getRightChild();
		tree.setRightChild(rightTree.getLeftChild());
		rightTree.setLeftChild(tree);

		int height = Math.max(getHeight(tree.getLeftChild()), getHeight(tree.getRightChild())) +1;
		tree.setHeight(height);

		height = Math.max(getHeight(rightTree.getRightChild()), tree.getHeight()) + 1;
		rightTree.setHeight(height);
		
		return rightTree;
	}
		
	
	/**
	 * Rotación doble con hijo derecho
	 * @param tree
	 * @return
	 */
	public BTreeIF<Vehicle> doubleWithRightChild(BTreeIF<Vehicle> tree){
		tree.setRightChild(rotateWithLeftChild( (BTreeIF<Vehicle>) tree.getRightChild()));
		return rotateWithRightChild( (BTreeIF<Vehicle>) tree);
	}
	
	

	
	
	/**
	 * @return retorna elemento mínimo
	 */
	public BTreeIF<Vehicle> findMin(){
		
		return findMin(this);
	}
	
	public BTreeIF<Vehicle> findMin(BTreeIF<Vehicle> tree){
		if(tree.getLeftChild() == null) return tree;
		return findMin(tree.getLeftChild());
	}
	
	
	/**
	 * @return retorna elemento máximo
	 */
	public BTreeIF<Vehicle> findMax(){
		
		return findMax(this);
	}
	
	public BTreeIF<Vehicle> findMax(BTreeIF<Vehicle> tree){
		if(tree.getRightChild() == null) return tree;
		return findMax(tree.getRightChild());
	}
	
	
	@Override
	public Vehicle getRoot() {
		return this.root;
	}
	
	@Override
	public void setRoot(Vehicle element) {
		if(element != null)
			this.root = element;	
	}

	@Override
	public BTreeIF<Vehicle> getRightChild() {
		return this.right;
	}

	@Override
	public BTreeIF<Vehicle> getLeftChild() {
		return this.left;
	}
	
	
	public void setHeight(int i){
		this.height = i;
	}
	
	
	public int getHeight(){
		return this.height;
	}
	
	private int getHeight(BTreeIF<Vehicle> tree){
		
		return tree == null ? -1 : tree.getHeight();	
	}
	

	public void setLeftChild (BTreeIF<Vehicle> tree){
		this.left = tree;
	}
	
	public void setRightChild(BTreeIF<Vehicle> tree){
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
		return this.root==null;		
	}
	
	public boolean contains(Vehicle element){
		return this.root.equals(element) || this.left.contains(element) || this.right.contains(element);
		
	}
	
	public boolean containsPreorden(Vehicle element){
		if(element == null) return false;
		return element.equals(getRoot()) || 
				(getLeftChild() != null)? getLeftChild().contains(element) : false ||
				(getRightChild() != null)? getRightChild().contains(element) : false;
	}
	
	public boolean containsCentinela(Vehicle element){
		
		boolean found = false;
		IteratorIF<Vehicle> it = getIterator(BTreeIF.INORDER);
		
		while(!found && it.hasNext()){
			Vehicle anElement = it.getNext();
			found = anElement.equals(element);
		}
		
		return found;
	}
	
	public IteratorIF<Vehicle> getIterator(int type){
		BTreeIF<Vehicle> handler = new BTreeDynamic<Vehicle> (this);
		return new BTreeIterator<Vehicle> (handler, type);
	}
	
	public int hashCode(){
		return 31 * 31 * ((root == null) ? 0 : root.hashCode()) +
					31 * ((left == null) ? 0 : left.hashCode()) + 
					31 * ((right == null)? 0 : right.hashCode());
	}
	
	public class Comparator<T> extends ComparatorBase<T>{
		
		public Comparator(){
			super();
		}
	}
}