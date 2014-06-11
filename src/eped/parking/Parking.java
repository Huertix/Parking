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
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;
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
	private VehicleQueue vQueueOut;
	//private int currentSection;
	//private int currentArea;
	
	

	
	
	public Parking(){
		parkingT = new TreeDynamic<ParkingElement>();
		parkingT.setRoot(null);
		
		setFloors(ParkingConf.FLOORS);
		vQueueOut   = new VehicleQueue();
		 
		
				
	}
	
	
	public void setFloors(int floors){
		
		for(int i=1; i<=floors;i++){
			parkingT.addChild(new ParkingFloor(i));
		}		
	}
	
	
	
	public void prueba(Vehicle v){
		
		
	//----------------------- PRUEBA ------------------------------
		TreeIterator<ParkingElement> treeIT = new TreeIterator<ParkingElement>(parkingT,TreeIF.LRBREADTH);
		
		System.out.println("VType: "+v.getType()+" VGate: "+v.getGate()); 
		System.out.println("");
		while(treeIT.hasNext()){
			ParkingElement element = treeIT.getNext();
			
			if(element.getClass()==ParkingSpace.class){
				ParkingSpace space = (ParkingSpace) element;
				if(v.getType()==space.getType()){
					setDistance(v.getGate(), space);
								
					System.out.println("Space: "+space.getType()+" "+space.toString() + " Value: "+space.getValue());
				}
			}
				
		
		}
	}
	
	
	
	
	
	
	
	// ----------------------------- Vieja Rutina busqueda mejor plaza -------------------------------------------------------
	public ParkingSpace getSpace(ParkingConf.TType type, ParkingConf.TGate gate){
		
		ParkingSpace parkingSpace = null;
		
		int currentSection = 0;
		int currentArea = 0;
		currentSpaceEven = 1;
		currentSpaceOdd = 2;
	
		//getTicket(type, gate);
		
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
						
						//currentSpaceEven = auxEve;
						//currentSpaceOdd = auxOdd;
												
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
	

	
	
	
	
	
	
//--------------------------------------  Nuevas Rutinas Asignacion pesos plazas ------------------------------- 
	
	public void setDistance(ParkingConf.TGate gate, ParkingSpace space){
		
		Integer[] path = ParkingConf.getSearchingPath(gate);
		
		ParkingConf.TGate spaceGate = space.getGate();
		ParkingConf.TZone spaceZone = space.getZone();
		
		ParkingConf.TGate[] gateValues =  ParkingConf.TGate.values(); 
		
		
			
		
		for(int i=0;i<4;i++){
			ParkingConf.TGate currentGate =  gateValues[path[i]-1];			
			if(currentGate==spaceGate){			
				if(i==0){space.setValue((space.getID()+setDistance(i,spaceZone)));}		
				else if(i==1){space.setValue((20+space.getID()+setDistance(i,spaceZone)));}		
				else if(i==2){space.setValue((40+space.getID()+setDistance(i,spaceZone)));}	
				else if(i==3){space.setValue((60+space.getID()+setDistance(i,spaceZone)));}	
				
			}
		}
				
				
	}
	
	private int setDistance(int loop, ParkingConf.TZone spaceZone){
		
		ParkingConf.TZone[] zoneValues =  ParkingConf.TZone.values();
			
		Integer[] areaPath = ParkingConf.getNextAreaPath(loop);
		int value = loop;
		for(int i = 0;i<4;i++){
			ParkingConf.TZone currentZone =  zoneValues[areaPath[i]-1];
			if(currentZone==spaceZone){
				if(i==0){value = 0;}
				else if(i==1){value = 5;}
				else if(i==2){value = 5;}
				else if(i==3){value = 10;}
			}
		}
		
		return value;
	}
	
	

	
	
	
	

    
	// ---------------------------------- Nuevas Rutinas busqueda mejor plaza ------------------------------------------
	
	public ParkingSpace getTicket(ParkingConf.TType type, ParkingConf.TGate gate, int time){
	
		ParkingSpace pSpace = null;
		
		QueueIF<ParkingElement> queueSpace = new QueueDynamic<ParkingElement> ();
	
		IteratorIF <TreeIF <ParkingElement>> floorsIT = parkingT.getChildren ().getIterator ();
			
		while(floorsIT.hasNext()){
			TreeIF<ParkingElement> floorTree = floorsIT.getNext(); // Arbol de Niveles
			IteratorIF<TreeIF<ParkingElement>> sectionsIT = floorTree.getChildren().getIterator(); // Iterador de Secciones
			QueueIF<ParkingElement> queueSections = new QueueDynamic<ParkingElement>();
			
			int count = 0;
			while(sectionsIT.hasNext()){ // While que pasa por las secciones
				ParkingElement result = null;
				if(count>1)
					 result = getTicket(sectionsIT.getNext(),TreeIF.RLBREADTH, gate,type, time);
				else
					result = getTicket(sectionsIT.getNext(),TreeIF.PREORDER, gate,type, time);
					
				
				
				//quickSort(queueSections);// Llamada 
				queueSections.add(result);
				count++;
				
			}
			
			
			quickSort(queueSections);
			
			if(!queueSections.isEmpty())
				queueSpace.add(queueSections.getFirst());			
		}	
		
		while(!queueSpace.isEmpty()){
			if(pSpace==null){
				pSpace = (ParkingSpace) queueSpace.getFirst();
				
			}
			else{
				ParkingSpace auxSpace = (ParkingSpace) queueSpace.getFirst();
				if(pSpace.getValue() > auxSpace.getValue())
					pSpace = auxSpace;
			}
			queueSpace.remove();
		}
		
		
		
		
		
		
		return pSpace;
	}
	
	

	private ParkingElement getTicket(TreeIF<ParkingElement> tree, int order, ParkingConf.TGate gate, ParkingConf.TType type, int time){
		
		QueueIF<ParkingElement> queue = new QueueDynamic();
		StackIF<Vehicle> auxStack = new StackDynamic<Vehicle>();

		
		TreeIterator<ParkingElement> treeIT = new TreeIterator<ParkingElement>(tree,order);
		
		while(treeIT.hasNext()){
			ParkingElement element = treeIT.getNext();
		
			if(element.getClass()==ParkingSpace.class){
				ParkingSpace space = (ParkingSpace) element;
				if(!space.hasVehicle() && space.getType()==type){
					setDistance(gate,space);
					queue.add(space);			
				}					
			}			
		}
	
		if(queue.isEmpty())
			return null;
		else{
			quickSort(queue);
			return queue.getFirst();
		}
	}
	
	public VehicleQueue getQueueOut(int time){
		updateQueueOut(parkingT, time);
		return vQueueOut;
	}
	
		
	public QueueIF<ParkingElement> updateQueueOut(TreeIF<ParkingElement> tree, int time){
		QueueIF<ParkingElement> traverse = new QueueDynamic<ParkingElement> ();
		ParkingElement element = tree.getRoot ();
		
		if(element!=null && element.getClass()==ParkingSpace.class){
			ParkingSpace space = (ParkingSpace) element;
			
			Vehicle v = space.getCurrentVehicle();
			if(v!=null && v.getTimeToGo() <= time){				
				vQueueOut.add(v);					
				ParkingState.updateUsedSpaces(-1);
				
				if(v.getType()== ParkingConf.TType.familiar)
					ParkingState.updateFamiliarUsedSpaces(-1);
				else
					ParkingState.updateNormalUsedSpaces(-1);
				
				//System.out.println("Car: "+v.getId()+" "+"Time: "+v.getHour()+" add to StackOut");
				space.setCurrentVehicle(null);
			}
		}
		
		
        traverse.add (element);
        IteratorIF <TreeIF <ParkingElement>> childrenIt = tree.getChildren ().getIterator ();
        while (childrenIt.hasNext ()) {
            TreeIF <ParkingElement> aChild = childrenIt.getNext ();
            QueueIF <ParkingElement> aTraverse = updateQueueOut (aChild,time);
            addAll (traverse, aTraverse);
        }
        return traverse;
		
	}
	
	
	//------------------------------ Busqueda de los vehiculos con tiempo agotado --------------------------------------
	/*public VehicleQueue getOverTimeVehicleQueue(VehicleQueue outQueue, int time){
		
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
		
	
	}*/

    private void addAll (QueueIF<ParkingElement> q, QueueIF<ParkingElement> p){
        while (!p.isEmpty ()) {
        	ParkingElement element = p.getFirst ();
            q.add (element);
            p.remove ();
        }
    }
    
    
    public void quickSort(QueueIF<ParkingElement> queue) {
	    // Caso base
	    if (queue.getLength() <=1 )
	      return;
	  
	    // Caso recursivo
	    QueueIF<ParkingElement> menores = new QueueDynamic<ParkingElement>(); // Una cola para los menores que x
	    QueueIF<ParkingElement> mayores = new QueueDynamic<ParkingElement>(); // Una cola para los mayores que x
	    QueueIF<ParkingElement> iguales = new QueueDynamic<ParkingElement>(); // Una cola para los mayores que x
	    // Particionamiento
	   
	    iguales.add(queue.getFirst());
	    queue.remove();
	    
	    
	    while (!queue.isEmpty()) {
	    	ParkingSpace pivot = (ParkingSpace) iguales.getFirst();
	    	ParkingSpace x = (ParkingSpace) queue.getFirst();
	    	queue.remove();
	    	
	    	if(x.getValue() < pivot.getValue()) menores.add(x);
	    	else if (x.getValue() > pivot.getValue()) mayores.add(x);
	    	else iguales.add(x);
	    	
	    	
	    }
	    quickSort(menores); // Se ordenan los menores recursivamente
	    quickSort(mayores); // Se ordenan los mayores recursivamente
	    
	    // Se regresan los valores a la cola inicial
	    while (!menores.isEmpty()){
	      queue.add(menores.getFirst());
	      menores.remove();
	    }
	    //queue.add(pivot);
	    while (!iguales.isEmpty()){
		      queue.add(iguales.getFirst());
		      iguales.remove();
		    }
	    while (!mayores.isEmpty()){
	      queue.add(mayores.getFirst());
	      mayores.remove();
	    }
	    
	}
	  
	
    
    
    
    
    
    
    
	// Bloque sin uso --------------------------------
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