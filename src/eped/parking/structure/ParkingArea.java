package eped.parking.structure;

import eped.IteratorIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class ParkingArea extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private ParkingConf.TZone zone;
	private TreeIF<ParkingElement> areaT;
	
	
	public ParkingArea(ParkingConf.TZone zone){
		this.zone=zone;
		areaT = new TreeDynamic<ParkingElement>();
		areaT.setRoot(this);
		
		setSpaces();
	}
	
	public ParkingArea getArea(){
		return this;
	}
	
	public ParkingConf.TZone getZone(){
		return zone;
	}
	
	private void setSpaces(){
		ParkingConf.TType[] types = ParkingConf.TType.values();	
		int typeSize = types.length;
		for(int i = 0;i<typeSize;i++){
			for(int j = 0;j<ParkingConf.getSpaces();j++){
				areaT.addChild(new ParkingSpace(types[i]));
			}
		}
			
	}

	@Override
	public IteratorIF<Object> getIterator() {
		// TODO Auto-generated method stub
		return null;
	}



}