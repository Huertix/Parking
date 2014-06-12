/*
 * Clase correspondiente a la estructura de Secciones, elemento de parking.
 */

package eped.parking.structure;

import eped.IteratorIF;
import eped.parking.ParkingConf;
import eped.parking.ParkingState;
import eped.parking.vehicle.Vehicle;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;


/**
 * @author David Huerta - 47489624Y - 1¼ EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ParkingSpace  extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private int spaceID;
	private int floor;
	private Vehicle currentVehicle;
	private ParkingConf.TType vType;
	private ParkingConf.TGate gate;
	private ParkingConf.TZone zone;
	private int value;

	

	public ParkingSpace(ParkingConf.TType type, int floor, ParkingConf.TGate gate, ParkingConf.TZone zone){
		super();
		vType = type;
		spaceID = ParkingState.getNextSpaceID(type);
		this.floor = floor;
		this.gate = gate;
		this.zone = zone;
		this.setRoot(this);
		this.addChild(null);
	}
	
	
	/**
	 * @return Retorna vehiculo actual
	 */
	public Vehicle getCurrentVehicle() {
		return currentVehicle;
	}

	/**
	 * @param Asigna veh’culo a la plaza
	 */
	public void setCurrentVehicle(Vehicle v) {
		currentVehicle = v;
	}
	
	/**
	 * Elimina veh’culo de la plaza
	 */
	public void removeVehicle(){
		currentVehicle = null;
	}
	
	/**
	 * @return Retorna verdad si tiene veh’culo
	 */
	public boolean hasVehicle(){
		return currentVehicle!=null;
	}
	
	/**
	 * @return Retorna Tipo de plaza Normal o Familiar
	 */
	public ParkingConf.TType getType(){
		return vType;
	}
	
	/**
	 * @return Retorna secci—n a la que pertenece
	 */
	public ParkingConf.TGate getGate(){
		return gate;
	}
	
	/**
	 * @return Retorna Zona a la que pertence
	 */
	public ParkingConf.TZone getZone(){
		return zone;
	}
	
	/**
	 * @return Retorna Identificaci—n de plaza
	 */
	public int getID(){
		return spaceID;
	}
	
	
	
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		return null;
	}
	
	/**
	 * @return
	 */
	public int getFloor(){
		return floor;
	}
	
	/**
	 * @return
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * @param value
	 */
	public void setValue(int value){
		this.value = value;
	}
	
	
	
	public String toString(){
		return ""+floor+" - "+gate+" - "+zone+" - "+spaceID;
	}
	
	public int hashCode(){
        return 43 * ((getRoot() == null) ? 0 : getRoot().hashCode ()) +
                                    ((getChildren() == null) ? 0 : getChildren().getFirst().hashCode ());
 }

	

}
