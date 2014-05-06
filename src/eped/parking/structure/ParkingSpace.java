package eped.parking.structure;

import eped.IteratorIF;
import eped.parking.ParkingConf;
import eped.parking.ParkingState;
import eped.parking.vehicle.Vehicle;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;


public class ParkingSpace  extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private int spaceID;
	private Vehicle currentVehicle;
	private ParkingConf.TType vType;
	//private TreeDynamic<ParkingElement> spaceT;
	
	public ParkingSpace(ParkingConf.TType type){
		super();
		vType = type;
		spaceID = ParkingState.getNextSpaceID(type);
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
	
	public int getID(){
		return spaceID;
	}
	
	
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		return null;
	}
	
	public String toString(){
		return ""+vType+" "+ spaceID;
	}

	

}
