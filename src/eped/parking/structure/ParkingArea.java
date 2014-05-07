package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;
import eped.tree.TreeIterator;

public class ParkingArea extends TreeDynamic<ParkingElement> implements ParkingElement, TreeIF<ParkingElement>{
	
	private int floor;
	private ParkingConf.TZone zone;
	private ParkingConf.TGate gate;
	//private TreeIF<ParkingElement> areaT;
	
	
	public ParkingArea(ParkingConf.TZone zone, int floor,ParkingConf.TGate gate ){
		super();
		this.zone = zone;
		this.gate = gate;
		this.floor = floor;
		//areaT = new TreeDynamic<ParkingElement>();
		this.setRoot(this);
		
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
			for(int j = 0;j<ParkingConf.SPACES;j++){
				this.addChild(new ParkingSpace(types[i],floor,gate,zone));
			}
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
	
	public ParkingConf.TGate getGate(){
		return gate;
	}
	
	public String toString(){
		return "" + zone; 
	}



}
