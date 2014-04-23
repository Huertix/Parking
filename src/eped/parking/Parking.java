package eped.parking;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.list.ListStatic;
import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingFloor;
import eped.parking.structure.ParkingSection;
import eped.parking.structure.ParkingSpace;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;
import eped.tree.TreeIterator;

public class Parking {	
	
	private TreeIF<ParkingElement> parkingT;
	public static ListIF<Integer[]> sectionSearcher;
	

	
	
	public Parking(){
		parkingT = new TreeDynamic<ParkingElement>();
		parkingT.setRoot(null);
		setFloors(ParkingConf.FLOORS);
		initSearcher(); 
		
				
	}
	
	
	public void setFloors(int floors){
		
		for(int i=1; i<=floors;i++){
			parkingT.addChild(new ParkingFloor(i));
		}		
	}
	
	public ParkingSpace getSpace(ParkingConf.TType type, ParkingConf.TGate gate){
		System.out.println(type.toString() +" "+ gate.toString());
		
		Integer[] path  = ParkingConf.getSearchingPath(gate);
	
		boolean found = false;
		
		ListIF<TreeIF<ParkingElement>> childrenList = parkingT.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> childIT = childrenList.getIterator();
		
		for(int i=0; i<path.length;i++){
			
			childIT.reset();
			if(found)
				break;
		
			while(!found && childIT.hasNext()){
				
				ParkingFloor floor = (ParkingFloor) childIT.getNext();
				
				IteratorIF<TreeIF<ParkingElement>> sectionIT = floor.getIterator(path[i]);
				
				while(sectionIT.hasNext()){
					ParkingSection section = (ParkingSection) sectionIT.getNext();
					System.out.println(section.toString());
				}
			}
			
		}
		return null;
	}
	
	public boolean hasSpace(ParkingConf.TType type){
		return ParkingState.hasSpaces(type);
	}
	
	public void initSearcher(){
		sectionSearcher = new ListStatic<Integer[]>(4);
		sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.A));
		sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.D));
		sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.B));
		sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.C));
		
	}
	
	public static ListIF<Integer[]> getSectionSearcher(){
		return sectionSearcher;
	}
	
	

}
