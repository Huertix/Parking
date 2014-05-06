package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;
import eped.tree.TreeIterator;

public class ParkingArea extends TreeDynamic<ParkingElement> implements ParkingElement, TreeIF<ParkingElement>{
	
	private ParkingConf.TZone zone;
	//private TreeIF<ParkingElement> areaT;
	
	
	public ParkingArea(ParkingConf.TZone zone){
		super();
		this.zone=zone;
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
				this.addChild(new ParkingSpace(types[i]));
			}
		}
			
	}

	@Override
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		ListIF<TreeIF<ParkingElement>> childrenList = this.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> childIT = childrenList.getIterator();
		
		return childIT;
	}
	
	public String toString(){
		return "" + zone; 
	}



}
