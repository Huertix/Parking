package eped.parking.structure;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.ParkingConf;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;

public class ParkingFloor implements ParkingElement{
	
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
			floorT.addChild((TreeIF) new ParkingSection(gates[i]));
		}	
	
	}
	



	@Override
	public IteratorIF<Object> getIterator(){
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