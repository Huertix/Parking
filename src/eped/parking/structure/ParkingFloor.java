 /*
 * Clase correspondiente a la estructura de Planta, elemento de parking.
 */


package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;


/**
 * @author David Huerta - 47489624Y - 1¼ EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ParkingFloor extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private int floorLevel;

	
	/**
	 * @param level Identificacion de planta
	 */
	public ParkingFloor (int level){
		super();
		floorLevel = level;
		
		this.setRoot(this);
		
		setSections();
		
	}

	
	/**
	 * @return Retorna identificacion de planta
	 */
	public int getFloor(){
		return floorLevel;
	}
	
	
	/**
	 * MŽtodo para la generaci—n de secciones
	 */
	private void setSections(){	
		ParkingConf.TGate[] gates = ParkingConf.TGate.values();	
		int gateSize = gates.length;
		for(int i = 0;i<gateSize;i++){
			this.addChild(new ParkingSection(gates[i],floorLevel));
		}	
	
	}
	


	/** 
	 * @return Retorna iterador con las secciones de esta planta.
	 */
	@Override
	public IteratorIF<TreeIF<ParkingElement>> getIterator(){
		ListIF<TreeIF<ParkingElement>> childrenList = this.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> childIT = childrenList.getIterator();
		
		return childIT;
	}
	
	public String toString(){
		return "" + floorLevel;
	}
	
	public int hashCode(){
        return 37 * ((getRoot() == null) ? 0 : getRoot().hashCode ()) +
                                    ((getChildren() == null) ? 0 : getChildren().getFirst().hashCode ());
 }
}