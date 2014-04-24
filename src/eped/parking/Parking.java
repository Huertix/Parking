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
		//initSearcher(); 
		
				
	}
	
	
	public void setFloors(int floors){
		
		for(int i=1; i<=floors;i++){
			parkingT.addChild(new ParkingFloor(i));
		}		
	}
	
	public ParkingSpace getSpace(ParkingConf.TType type, ParkingConf.TGate gate, int ID){
		
		ParkingSpace parkingSpace = null;
		
		
		
		System.out.println("Current Car: "+ID+" "+type.toString() +" "+ gate.toString());
		System.out.println("----------------------------------");
		
		Integer[] sectionsPath  = ParkingConf.getSearchingPath(gate); // vector rutas de busqueda de puertas en funcion de la puerta asignada a coche
		// A (1,2,4,3) B(2,3,1,4) C(3,4,2,1) D(4,1,3,2)  A=1 B=2 C=3 D=4
		
		Integer[] areasPath = ParkingConf.getAreasPath(); // vector ruta de busqueda de las areas
		// 1ºSeccion(I,II,IV,III) 2ºSeccion(IV,I,III,II) 3ºSeccion(II,III,I,IV) 4ºSeccion(III,IV,II,I)
		
		ParkingConf.TGate[] gateValues =  ParkingConf.TGate.values(); // vector valores de puertas
		ParkingConf.TZone[] zoneValues =  ParkingConf.TZone.values(); // vector valores de areas
	
		boolean found = false;
		
		ListIF<TreeIF<ParkingElement>> parkingChildrenList = parkingT.getChildren(); 			//   Iterador de 
		IteratorIF<TreeIF<ParkingElement>> parkingChildrendIT = parkingChildrenList.getIterator();//	niveles
		
			
		
		// 1º Selector de seccion ----------------------------------
		int currentSection = 0;
		int currentArea = 0;
		int currentLevel = 1;
		
		while(!found && currentSection+1<=sectionsPath.length){
			ParkingConf.TGate currentGate =  gateValues[sectionsPath[currentSection]-1]; // Sección actual de busqueda
			System.out.println("Gate: "+currentGate.toString());
				
			// 2º Selector de zonas ----------------------------------
			
			ParkingConf.TZone currentZone = zoneValues[areasPath[currentArea]-1];
			System.out.println("Zone: "+currentZone.toString());
			
			
			if(currentArea>=areasPath.length){
				currentArea=0;
				
			}
						
			if((currentArea+1)%4==0)
				currentSection++;
			
			
			currentArea++;
			
		}
						
			// 3º Selector de nivel----------------------------------
		
		
			System.out.println("Fin Busqueda");
			System.out.println();
			return parkingSpace;
	}
	
	private boolean checkArea(ParkingSection section) {
		IteratorIF<TreeIF<ParkingElement>> sectionIT = section.getIterator();
		
		return false;
	}


	public boolean hasSpace(ParkingConf.TType type){
		return ParkingState.hasSpaces(type);
	}
	
}







/*
 * 		
		for(int i=0; i<path.length;i++){ // section bucle for
			
			
			
			
			parkingChildrendIT.reset();
			
			
			
			
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
		
 */






/*
 * 
 * 
 	IteratorIF<Integer[]> sectionSearcherIT = sectionSearcher.getIterator();  //Iterador rutas de areas. Cada iteracion contiene un vector
		// 1º (I,II,IV,III) 	2º(IV,I,III,II) 	3º(II,III,I,IV) 	4º(III,IV,II,I)
		 
	Integer[]  nextSectionSearcher = null;
	if(sectionSearcherIT.hasNext()){
		nextSectionSearcher = sectionSearcherIT.getNext();
  
public void initSearcher(){
	sectionSearcher = new ListStatic<Integer[]>(4);
	sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.C));
	sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.B));
	sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.D));
	sectionSearcher.insert(ParkingConf.getSearchingPath(ParkingConf.TGate.A));
	
}

public static ListIF<Integer[]> getSectionSearcher(){
	return sectionSearcher;
}
*/

