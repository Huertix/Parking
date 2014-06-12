/*
 * Clase correspondiente a la estructura de Secciones, elemento de parking.
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
public class ParkingSection extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private ParkingConf.TGate gate;
	private int floor;

	
	
	/**
	 * @param gate Identificacion de seccion
	 * @param floor Planta a la que pertenece
	 */
	public ParkingSection(ParkingConf.TGate gate, int floor){
		super();
		this.gate = gate;
		this.floor = floor;
		
		this.setRoot(this);
		
		setAreas();
	}

	
	/**
	 * @return Retorna la el idetificar secci—n
	 */
	public ParkingConf.TGate getGate(){
		return gate;
	}
	
	
	/**
	 * MŽtodo para la generaci—n de areas
	 */
	private void setAreas(){	
		ParkingConf.TZone[] zones = ParkingConf.TZone.values();	
		int zoneSize = zones.length;
		for(int i = 0;i<zoneSize;i++){
			this.addChild(new ParkingArea(zones[i],floor,gate));
		}
	}	

	
	/** 
	 * @return Retorna iterador con las secciones de esta planta.
	 */
	@Override
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		ListIF<TreeIF<ParkingElement>> childrenList = this.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> childIT = childrenList.getIterator();
		
		return childIT;
	}
	
	public int getFloor(){
		return floor;
	}
	
	public String toString(){
		return gate.toString();
	}
	
	public int hashCode(){
        return 41 * ((getRoot() == null) ? 0 : getRoot().hashCode ()) +
                                    ((getChildren() == null) ? 0 : getChildren().getFirst().hashCode ());
 }


}
