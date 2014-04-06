package eped.parking;

import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingFloor;
import eped.parking.structure.ParkingSpace;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class Parking {	
	
	private TreeIF<ParkingElement> parkingT;
	
	
	public Parking(){
		parkingT = new TreeDynamic<ParkingElement>();
		parkingT.setRoot(null);
		setFloors(ParkingConf.getFloors());
			
	}
	
	
	public void setFloors(int floors){
		
		for(int i=1; i<=floors;i++){
			parkingT.addChild((TreeIF) new ParkingFloor(i));
		}		
	}
	
	public ParkingSpace getSpace(ParkingConf.TType type){
		return null;
	}
	
	public boolean hasSpace(ParkingConf.TType type){
		return (Boolean) null;
	}
	
	

}
