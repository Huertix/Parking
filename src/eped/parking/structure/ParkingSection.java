package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class ParkingSection extends TreeDynamic<ParkingElement> implements ParkingElement{
	
	private ParkingConf.TGate gate;
	private TreeIF<ParkingElement> sectionT;
	
	
	public ParkingSection(ParkingConf.TGate gate){
		this.gate=gate;
		sectionT = new TreeDynamic<ParkingElement>();
		sectionT.setRoot(this);
		
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
			sectionT.addChild(new ParkingArea(zones[i]));
		}
	}	

	@Override
	public IteratorIF<TreeIF<ParkingElement>> getIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		return gate.toString();
	}


}
