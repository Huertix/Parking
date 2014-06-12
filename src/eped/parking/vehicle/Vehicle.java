/*
 * Clase que define la estructura de los veh’culos
 */

package eped.parking.vehicle;

import eped.parking.ParkingConf;
import eped.parking.structure.ParkingSpace;


/**
 * @author David Huerta - 47489624Y - 1¼ EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class Vehicle {
	
	private int id;
	private ParkingConf.TType type;
	private ParkingConf.TGate gate;
	private int hour;
	private int timeToGo;
	private ParkingSpace spaceAsigned;
	
	
	/**
	 * @param id Identificador del Veh’culo
	 * @param type Tipo de veh’culo
	 * @param gate Puerta Objetivo
	 * @param hour Tiempo de permanencia
	 */
	public Vehicle(int id, ParkingConf.TType type, ParkingConf.TGate gate, int hour) {
		super();
		this.id = id;
		this.type = type;
		this.gate = gate;
		this.hour = hour;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ParkingConf.TType getType() {
		return type;
	}
	public void setType(ParkingConf.TType type) {
		this.type = type;
	}
	public ParkingConf.TGate getGate() {
		return gate;
	}
	public void setGate(ParkingConf.TGate gate) {
		this.gate = gate;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * @param space Relaciona el veh’culo con la plaza que le ha sido asignada
	 */
	public void setSpace(ParkingSpace space){
		spaceAsigned = space;
	}
	
	/**
	 * @return Retorna la plaza asignada
	 */
	public ParkingSpace getSpace(){
		return spaceAsigned;
	}


	/**
	 * @return retorna el tiempo en el cual debe abandonar el parking
	 */
	public int getTimeToGo() {
		return timeToGo;
	}


	/**
	 * @param timeToGo Asigna el momento en el que tiene que abandonar el parking
	 */
	public void setTimeToGo(int timeToGo) {
		this.timeToGo = timeToGo;
	}
	
	public String toString(){
		return ""+id+" - "+type+" - "+gate+" - "+hour;
	}
	
	public int hashCode(){
        return 31 *id + timeToGo;
 }
	

}
