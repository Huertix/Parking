 /*
 * Clase correspondiente a la estructura de çrea, elemento de parking.
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
public class ParkingArea extends TreeDynamic<ParkingElement> implements ParkingElement, TreeIF<ParkingElement>{
	
	private int floor;
	private ParkingConf.TZone zone;
	private ParkingConf.TGate gate;

	
	
	/**
	 * Contructor de Parking
	 * @param zone Asignaci—n de Zona
	 * @param floor Asignaci—n de la planta a la que pertenece
	 * @param gate Asignaci—n de la puerta a la que pertenece
	 */
	public ParkingArea(ParkingConf.TZone zone, int floor,ParkingConf.TGate gate ){
		super();
		this.zone = zone;
		this.gate = gate;
		this.floor = floor;
		this.setRoot(this);
		
		setSpaces();
	}
	
	
	/**
	 * @return Retorna que tipo de la zona / area es.
	 */
	public ParkingArea getArea(){
		return this;
	}
	
	
	/**
	 * @return Retorna secci—n a la que pertenece
	 */
	public ParkingConf.TZone getZone(){
		return zone;
	}
	
	
	
	/**
	 * MŽtodo para contru’r las plazas que corresponden a este area.
	 */
	private void setSpaces(){
		ParkingConf.TType[] types = ParkingConf.TType.values();	
		int typeSize = types.length;
		for(int i = 0;i<typeSize;i++){
			for(int j = 0;j<ParkingConf.SPACES;j++){
				this.addChild(new ParkingSpace(types[i],floor,gate,zone));
			}
		}
			
	}

	
	
	
	/** 
	 * @return Retorna iterador con las plazas de este area.
	 */
	@Override
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		ListIF<TreeIF<ParkingElement>> childrenList = this.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> childIT = childrenList.getIterator();
		
		return childIT;
	}
	
	
	/**
	 * @return Retorna planta a la que pertenece
	 */
	public int getFloor(){
		return floor;
	}
	
	
	/**
	 * @return Retorna Seccion a la que pertenece
	 */
	public ParkingConf.TGate getGate(){
		return gate;
	}
	
	
	public String toString(){
		return "" + zone; 
	}
	
	 public int hashCode(){
	        return 31 * ((getRoot() == null) ? 0 : getRoot().hashCode ()) +
	                                    ((getChildren() == null) ? 0 : getChildren().getFirst().hashCode ());
	 }
	    

}
