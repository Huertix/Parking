package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;
import eped.tree.TreeIterator;

public class ParkingFloor extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private int floorLevel;
	private TreeIF<ParkingElement> floorT;
	
	public ParkingFloor (int level){
		floorLevel = level;
		floorT = new TreeDynamic<ParkingElement>();
		floorT.setRoot(this);
		
		setSections();
		
	}

	
	public int getFloor(){
		return floorLevel;
	}
	
	private void setSections(){	
		ParkingConf.TGate[] gates = ParkingConf.TGate.values();	
		int gateSize = gates.length;
		for(int i = 0;i<gateSize;i++){
			floorT.addChild(new ParkingSection(gates[i]));
		}	
	
	}
	



	@Override
	public IteratorIF<TreeIF<ParkingElement>> getIterator(){
		ListIF<TreeIF<ParkingElement>> childrenList = floorT.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> childIT = childrenList.getIterator();
		
		return childIT;
	}
	
	public String toString(){
		return "" + floorLevel;
	}
}