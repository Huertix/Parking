package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class ParkingArea implements ParkingElement{
	
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
				areaT.addChild((TreeIF<ParkingElement>) new ParkingSpace(types[i]));
			}
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
