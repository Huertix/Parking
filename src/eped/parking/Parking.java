package eped.parking;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.list.ListStatic;
import eped.parking.structure.ParkingArea;
import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingFloor;
import eped.parking.structure.ParkingSection;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleQueue;
import eped.stack.StackDynamic;
import eped.stack.StackIF;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;
import eped.tree.TreeIterator;

public class Parking {	
	
	private TreeIF<ParkingElement> parkingT;
	public static ListIF<Integer[]> sectionSearcher;
	private int currentFloor = 0;
	private int currentSpaceEven = 0;
	private int currentSpaceOdd = 0;
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
		currentSpaceEven = 1;
		currentSpaceOdd = 2;
		
		
		
		
		//System.out.println("Current Car: "+ID+" "+type.toString() +" "+ gate.toString());
		//System.out.println("----------------------------------");
		
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
			//System.out.print("Gate: "+currentGate.toString()+"\t");
				
			// 2º Selector de zonas ----------------------------------
			
			ParkingConf.TZone currentZone = zoneValues[areasPath[currentArea]-1];
			//System.out.println("	Zone: "+currentZone.toString()+"\n");
			
			
			while(parkingSpace==null && parkingChildrendIT.hasNext()){
				ParkingFloor floor = (ParkingFloor) parkingChildrendIT.getNext();
				currentFloor = floor.getFloor();								//Registra valor floor.
				//System.out.println("		Floor: "+currentFloor);
				parkingSpace = getSpace(currentGate,currentZone,type,floor.getIterator());
			
			
				
				if(parkingSpace==null){
					if(currentArea==1 ||  currentArea==5 || currentArea==9 ||  currentArea==13){
						int auxEve = currentSpaceEven;
						int auxOdd = currentSpaceOdd;
						if(currentFloor >= 6){
							currentSpaceOdd-=2;
							currentSpaceEven-=2;
							
						}
						parkingSpace = getSpace(currentGate,zoneValues[areasPath[currentArea+1]-1],type,floor.getIterator());
						
						currentSpaceEven = auxEve;
						currentSpaceOdd = auxOdd;
												
					}
				}
		
			}
			
			
			
			if(currentArea>=areasPath.length) // Resetea la posicion del vector para las areas
				currentArea=0;
				
						
			if((currentArea+1)%4==0 && currentSpaceEven>ParkingConf.SPACES*2){ // Pasa a la siguiente seccion impares
				currentSection++;
				currentArea++;
				currentSpaceEven=0;
			}
			
			if((currentArea+1)%4==0 && currentSpaceOdd>ParkingConf.SPACES*2){ // Pasa a la siguiente seccion pares
				currentSection++;
				currentArea++;
				currentSpaceOdd=0;
			}
			
		
			
			if(currentSpaceEven>ParkingConf.SPACES*2){ // Pasa a la siguiente area Impares
				currentArea++;
				if(currentFloor==ParkingConf.FLOORS)
					currentSpaceEven=0;
			}
			
			if(currentSpaceOdd>ParkingConf.SPACES*2){ // Pasa a la siguiente area pares
				currentArea++;
				if(currentFloor==ParkingConf.FLOORS)
					currentSpaceOdd=0;
			}
		
			parkingChildrendIT.reset();
					
		}
						
			// 3º Selector de nivel----------------------------------
		
			//if(parkingSpace!=null)
				//System.out.println("Parking ID: "+parkingSpace.getID());
			
			//System.out.println("Fin Busqueda");
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
			
				
				if(space.getType()!=type){
					space = (ParkingSpace)IT.getNext();
					space = (ParkingSpace)IT.getNext();
					space = (ParkingSpace)IT.getNext();
					
					while(space.getID()<currentSpaceEven){			// Comprueba el ID de plaza actual. Pasa a siguiente plaza si currentSpace no corresponde
						space = (ParkingSpace)IT.getNext();
					}
					currentSpaceEven= space.getID();
					
					
				}
				
				else{
					while(space.getID()<currentSpaceOdd){			// Comprueba el ID de plaza actual. Pasa a siguiente plaza si currentSpace no corresponde
						space = (ParkingSpace)IT.getNext();
					}
					currentSpaceOdd= space.getID();
					
				}
				
				
				
				//System.out.println("				Spaces:" +space.getID());
			
					
				
				if(space.getType() == type){						//	
					if(!space.hasVehicle()){						//	Asignacion de plaza libre.			
						pSpace = space;								//
						if(type == ParkingConf.TType.normal)
							ParkingState.updateNormalUsedSpaces(1);
						else
							ParkingState.updateFamiliarUsedSpaces(1);
					}
					
					
						
					
					if(currentFloor>=ParkingConf.FLOORS){
						
						if(type==ParkingConf.TType.familiar)
							currentSpaceEven+=2;
						else
							currentSpaceOdd+=2;
					}
									
				}
				
			}		
			return pSpace;
		}
	}
	
	
	public VehicleQueue getOverTimeVehicleQueue(VehicleQueue outQueue, int time){
		
		StackIF<Vehicle> auxStack = new StackDynamic<Vehicle>();
		VehicleQueue auxQueue = new VehicleQueue(outQueue);
		
		TreeIterator<ParkingElement> treeIT = new TreeIterator<ParkingElement>(parkingT,TreeIF.RLBREADTH);
		
		boolean stop = false;
		
		while(treeIT.hasNext() && !stop){
			ParkingElement element = treeIT.getNext();
			
			if(element.getClass()==ParkingSpace.class){
				ParkingSpace space = (ParkingSpace) element;
				
				Vehicle v = space.getCurrentVehicle();
				if(v!=null && v.getTimeToGo() <= time){				
					auxStack.push(v);					
					ParkingState.updateUsedSpaces(-1);
					
					if(v.getType()== ParkingConf.TType.familiar)
						ParkingState.updateFamiliarUsedSpaces(-1);
					else
						ParkingState.updateNormalUsedSpaces(-1);
					
					//System.out.println("Car: "+v.getId()+" "+"Time: "+v.getHour()+" add to StackOut");
					space.setCurrentVehicle(null);
				}
			}
			
			else
				stop = true;
				
				
		}
		
		while(!auxStack.isEmpty()){
			auxQueue.add(auxStack.getTop());
			auxStack.pop();
		}
		
		return auxQueue;
		
	
	}
	
	
	
	
	
	private boolean checkArea(ParkingSection section) {
		IteratorIF<TreeIF<ParkingElement>> sectionIT = section.getIterator();
		
		return false;
	}


	public boolean hasSpace(){
		return ParkingState.hasSpaces();
	}
	
	public boolean hasSpace(ParkingConf.TType type){
		return ParkingState.hasSpaces(type);
	}
	
}