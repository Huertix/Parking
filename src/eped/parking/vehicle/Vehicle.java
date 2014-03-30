package eped.parking.vehicle;

import eped.parking.tdef.TSection;
import eped.parking.tdef.TType;

public class Vehicle {
	
	private int id;
	private TType type;
	private TSection gate;
	private int hour;
	
	
	public Vehicle(int id, TType type, TSection gate, int hour) {
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
	public TType getType() {
		return type;
	}
	public void setType(TType type) {
		this.type = type;
	}
	public TSection getGate() {
		return gate;
	}
	public void setGate(TSection gate) {
		this.gate = gate;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	

}
