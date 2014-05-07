package eped.parking.structure;

import eped.IteratorIF;
import eped.parking.ParkingConf;
import eped.parking.ParkingState;
import eped.parking.vehicle.Vehicle;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;


public class ParkingSpace  extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private int spaceID;
	private int floor;
	private Vehicle currentVehicle;
	private ParkingConf.TType vType;
	private ParkingConf.TGate gate;
	private ParkingConf.TZone zone;
	//private TreeDynamic<ParkingElement> spaceT;
	
	public ParkingSpace(ParkingConf.TType type, int floor, ParkingConf.TGate gate, ParkingConf.TZone zone){
		super();
		vType = type;
		spaceID = ParkingState.getNextSpaceID(type);
		this.floor = floor;
		this.gate = gate;
		this.zone = zone;
		//spaceT =  new TreeDynamic<ParkingElement>();
		this.setRoot(this);
		this.addChild(null);
	}
	
	
	public Vehicle getCurrentVehicle() {
		return currentVehicle;
	}

	public void setCurrentVehicle(Vehicle v) {
		currentVehicle = v;
	}
	
	public void removeVehicle(){
		currentVehicle = null;
	}
	
	public boolean hasVehicle(){
		return currentVehicle!=null;
	}
	
	public ParkingConf.TType getType(){
		return vType;
	}
	
	public ParkingConf.TGate getGate(){
		return gate;
	}
	
	public ParkingConf.TZone getZone(){
		return zone;
	}
	
	public int getID(){
		return spaceID;
	}
	
	
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		return null;
	}
	
	public int getFloor(){
		return floor;
	}
	
	
	
	public String toString(){
		return ""+floor+" - "+gate+" - "+zone+" - "+spaceID;
	}

	

}
