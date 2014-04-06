package eped.parking.vehicle;

import eped.parking.ParkingConf;


public class Vehicle {
	
	private int id;
	private ParkingConf.TType type;
	private ParkingConf.TGate gate;
	private int hour;
	
	
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
	

}
