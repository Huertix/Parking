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
		ParkingConf.TGate[] gateValues =  ParkingConf.TGate.values();
		ParkingConf.TZone[] zoneValues =  ParkingConf.TZone.values();
	
		boolean found = false;
		
		ListIF<TreeIF<ParkingElement>> parkingChildrenList = parkingT.getChildren();
		
		IteratorIF<TreeIF<ParkingElement>> parkingChildrendIT = parkingChildrenList.getIterator();
		IteratorIF<Integer[]> sectionSearcherIT = sectionSearcher.getIterator();
		
		
		for(int i=0; i<path.length;i++){ // section bucle 
			
			
			if(found)
				break;
			
			parkingChildrendIT.reset();
			Integer[]  nextSectionSearcher = null;
			
			if(sectionSearcherIT.hasNext()){
				nextSectionSearcher = sectionSearcherIT.getNext();
			}
			
			ParkingConf.TZone zone = zoneValues[nextSectionSearcher[i]];
			System.out.println(zone.toString());
			
			while(!found && parkingChildrendIT.hasNext()){
				
				ParkingFloor floor = (ParkingFloor) parkingChildrendIT.getNext();
				
				IteratorIF<TreeIF<ParkingElement>> sectionIT = floor.getIterator();
				
				
				
				while(sectionIT.hasNext()){
					ParkingSection section = (ParkingSection) sectionIT.getNext();
					System.out.println("path "+ path[i] + " Section "+ section.toString());
					if(gateValues[path[i]-1] == section.getGate()){
						
						boolean ocu = checkArea(section);
						
						System.out.println("igual ");
						
					}				
				}			
			}		
		} // for bucle end
		return null;
	}
	
	private boolean checkArea(ParkingSection section) {
		IteratorIF<TreeIF<ParkingElement>> sectionIT = section.getIterator();
		
		return false;
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
