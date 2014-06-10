package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class ParkingSection extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private ParkingConf.TGate gate;
	private int floor;

	
	public ParkingSection(ParkingConf.TGate gate, int floor){
		super();
		this.gate = gate;
		this.floor = floor;
		
		this.setRoot(this);
		
		setAreas();
	}

	
	public ParkingSection getSection(){
		return this;
	}
	
	public ParkingConf.TGate getGate(){
		return gate;
	}
	
	private void setAreas(){	
		ParkingConf.TZone[] zones = ParkingConf.TZone.values();	
		int zoneSize = zones.length;
		for(int i = 0;i<zoneSize;i++){
			this.addChild(new ParkingArea(zones[i],floor,gate));
		}
	}	

	@Override
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		ListIF<TreeIF<ParkingElement>> childrenList = this.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> childIT = childrenList.getIterator();
		
		return childIT;
	}
	
	public int getFloor(){
		return floor;
	}
	
	public String toString(){
		return gate.toString();
	}


}
