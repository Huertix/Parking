package eped.parking;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.list.ListStatic;
import eped.parking.structure.ParkingArea;
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
	private boolean topFloorReached = false;
	private int currentFloor = 0;
	private int currentSpace = 0;
	private int currentSection;
	private int currentArea;
	
	

	
	
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
		
		currentSection = 0;
		currentArea = 0;
		
		
		
		
		System.out.println("Current Car: "+ID+" "+type.toString() +" "+ gate.toString());
		System.out.println("----------------------------------");
		
		Integer[] sectionsPath  = ParkingConf.getSearchingPath(gate); // vector rutas de busqueda de puertas en funcion de la puerta asignada a coche
		// A (1,2,4,3) B(2,3,1,4) C(3,4,2,1) D(4,1,3,2)  A=1 B=2 C=3 D=4
		
		Integer[] areasPath = ParkingConf.getAreasPath(); // vector ruta de busqueda de las areas
		// 1ºSeccion(I,II,IV,III) 2ºSeccion(IV,I,III,II) 3ºSeccion(II,III,I,IV) 4ºSeccion(III,IV,II,I)
		
		ParkingConf.TGate[] gateValues =  ParkingConf.TGate.values(); // vector valores de puertas
		ParkingConf.TZone[] zoneValues =  ParkingConf.TZone.values(); // vector valores de areas
	
		
		ListIF<TreeIF<ParkingElement>> parkingChildrenList = parkingT.getChildren(); 			//   Iterador de 
		IteratorIF<TreeIF<ParkingElement>> parkingChildrendIT = parkingChildrenList.getIterator();//	niveles
		
			
		
		// 1º Selector de seccion ----------------------------------
		
		
		
		while(parkingSpace==null && currentSection+1<=sectionsPath.length){
			ParkingConf.TGate currentGate =  gateValues[sectionsPath[currentSection]-1]; // Sección actual de busqueda
			System.out.print("Gate: "+currentGate.toString()+"\t");
				
			// 2º Selector de zonas ----------------------------------
			
			ParkingConf.TZone currentZone = zoneValues[areasPath[currentArea]-1];
			System.out.println("	Zone: "+currentZone.toString()+"\n");
			
			
			while(parkingSpace==null && parkingChildrendIT.hasNext()){
				ParkingFloor floor = (ParkingFloor) parkingChildrendIT.getNext();
				currentFloor = floor.getFloor();								//Registra valor floor.
				System.out.println("		Floor: "+currentFloor);
				parkingSpace = getSpace(currentGate,currentZone,type,floor.getIterator());
			}
			
		
			if(currentArea>=areasPath.length)
				currentArea=0;
				
						
			if((currentArea+1)%4==0 && currentSpace>ParkingConf.SPACES*2){
				currentSection++;
				currentArea++;
				currentSpace=0;
			}
			
			
			if(currentSpace>ParkingConf.SPACES*2){
				currentArea++;
				if(currentFloor==ParkingConf.FLOORS)
					currentSpace=0;
			}
			
			parkingChildrendIT.reset();
			
			
		}
						
			// 3º Selector de nivel----------------------------------
		
			if(parkingSpace!=null)
				System.out.println("Parking ID: "+parkingSpace.getID());
			System.out.println("Fin Busqueda");
			
			return parkingSpace;
	}
	
	
	
	private ParkingSpace getSpace(ParkingConf.TGate gate,ParkingConf.TZone zone, ParkingConf.TType type, IteratorIF<TreeIF<ParkingElement>> IT){
		
		ParkingSpace pSpace = null;

		if(IT==null || !IT.hasNext()){
			return pSpace;
		}
		
		else{
			ParkingElement nextParkingElement = (ParkingElement) IT.getNext();
			IteratorIF<TreeIF<ParkingElement>> nextParkingElementIT = nextParkingElement.getIterator();
			
			
			//---------------- Bloque recursividad Seccion ---------------------------------
			if((nextParkingElement.getClass() == ParkingSection.class)){
				ParkingSection section = (ParkingSection) nextParkingElement;
				if(section.getGate()==gate){
					// Si es la puerta correcta iterar sobre las areas
					pSpace = getSpace(gate,zone,type,nextParkingElementIT);
				}
				
				else{
					// Si no es la seccion correcta, pasar a la siguiente seccion
					pSpace = getSpace(gate,zone,type,IT);				
				}			
			}
			
			
			//---------------- Bloque recursividad Area ---------------------------------
			if((nextParkingElement.getClass() == ParkingArea.class)){
				ParkingArea area = (ParkingArea) nextParkingElement;
				if(area.getZone() == zone){
					// Si es el area correcta iterar sobre las plazas
					pSpace = getSpace(gate,zone,type,nextParkingElementIT);
				}
				
				else{
					// Si no es el area correcta, pasar al siguiente area
					pSpace = getSpace(gate,zone,type,IT);				
				}			
			}
			
			//---------------- Bloque Plazas ---------------------------------
			if((nextParkingElement.getClass() == ParkingSpace.class)){
				ParkingSpace space = (ParkingSpace) nextParkingElement;
			
				while(space.getID()<currentSpace)			//NullPointerException
					space = (ParkingSpace)IT.getNext();
				
				currentSpace= space.getID();
				
				System.out.println("				Spaces:" +space.getID());
			
					
				
				if(space.getType() == type){
					if(!space.hasVehicle())
						pSpace = space;
					
					
					if(currentFloor>=ParkingConf.FLOORS)
						currentSpace+=2;
					
									
				}
				else										// ?
					pSpace = getSpace(gate,zone,type,IT);	// ?
			}		
			return pSpace;
		}
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

