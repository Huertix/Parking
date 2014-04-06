package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class ParkingSection implements ParkingElement{
	
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
			sectionT.addChild((TreeIF) new ParkingArea(zones[i]));
		}
	}	

	@Override
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
