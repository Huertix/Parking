package eped.parking.structure;

import eped.IteratorIF;
import eped.parking.ParkingConf;
import eped.parking.vehicle.Vehicle;
import eped.tree.TreeDynamic;


public class ParkingSpace  extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private int spaceID;
	private Vehicle currentVehicle;
	private ParkingConf.TType vType;
	private TreeDynamic<ParkingElement> spaceT;
	
	public ParkingSpace(ParkingConf.TType type){
		vType = type;
		spaceID = ParkingConf.spaceID;
		ParkingConf.spaceID++;	
		spaceT =  new TreeDynamic<ParkingElement>();
		spaceT.setRoot(this);
		spaceT.addChild(null);
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
	
	
	public IteratorIF<Object> getIterator() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
