/*
 * Esta clase se encarga del control de los mensajes de erros producidos 
 * por interacciones con los argumentos a la hora de ejecutar la aplicación
 * 
 */ 



package eped.parking;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingFloor;
import eped.parking.structure.ParkingSpace;
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;
import eped.tree.TreeDynamic;
import eped.tree.TreeIF;
import eped.tree.TreeIterator;



/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class Parking {	
	
	private TreeIF<ParkingElement> parkingT;
	public static ListIF<Integer[]> sectionSearcher;


	public Parking(){
		parkingT = new TreeDynamic<ParkingElement>();
		parkingT.setRoot(null);	
		setFloors(ParkingConf.FLOORS);
		
	}
	
	
	public void setFloors(int floors){
		
		for(int i=1; i<=floors;i++){
			parkingT.addChild(new ParkingFloor(i));
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
			
				queueSections.add(result);
				count++;			
			}
			
			
			quickSort(queueSections);
			
			if(!queueSections.isEmpty())
				queueSpace.add(queueSections.getFirst());			
		}	
		
		while(!queueSpace.isEmpty()){
			if(pSpace==null)
				pSpace = (ParkingSpace) queueSpace.getFirst();
					
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
		
		QueueIF<ParkingElement> queue = new QueueDynamic<ParkingElement>();
	
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
	
   
    
    public void quickSort(QueueIF<ParkingElement> queue) {
	    // Caso base
	    if (queue.getLength() <=1)
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

	    while (!iguales.isEmpty()){
		      queue.add(iguales.getFirst());
		      iguales.remove();
		    }
	    while (!mayores.isEmpty()){
	      queue.add(mayores.getFirst());
	      mayores.remove();
	    }    
	}
	   

	public boolean hasSpace(){
		return ParkingState.hasSpaces();
	}
	
	public boolean hasSpace(ParkingConf.TType type){
		return ParkingState.hasSpaces(type);
	}	
}