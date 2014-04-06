package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.parking.vehicle.Vehicle;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class ParkingSpace implements ParkingElement{
	
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
	
	
	public IteratorIF<Object> getIterator() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addChild(TreeIF arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ListIF getChildren() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IteratorIF getIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getRoot() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void removeChild(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setRoot(Object arg0) {
		// TODO Auto-generated method stub
		
	}


}
