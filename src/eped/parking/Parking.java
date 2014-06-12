/*
 * Esta clase es la base de la estructura jerarquica del parking. Aquí se instancia la clase
 * TreeIF como "parkingT", que será el elemento raiz de la estructura arborera del parking.
 * 
 * Descripción de los métodos principales:
 * 
 *   - setDistance: Este método se utiliza para asignar pesos a las plazas en función
 *   				de la puerta objetivo. Cada plaza tiene una variable "value" donde se 
 *   				acumula el valor. Para lograr este objetivo, se hace uso de arreglos 
 *   				pre-establecidos en la clase estática "ParkingConfig", los cuales definen
 *   				el  path o la ruta de busqueda en cada planta. 
 *   
 *   
 *   - getTicket: Método encargado de encontrar la mejor plaza. Se encolan las plazas con menor distancia
 *  			  a la puerta objetivo de cada sección, se clasifican y se retorna la mejor plaza para la
 *   			  demanda en curso.
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
 * @author David Huerta - 47489624Y - 1¬∫ EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class Parking {	
	
	private TreeIF<ParkingElement> parkingT;
	public static ListIF<Integer[]> sectionSearcher;


	/**
	 *Constructor Parking. Llama al método "setFloors" para instancias las plantas.
	 *el número de plantas está definido en la clase ParkingConfig 
	 */
	public Parking(){
		parkingT = new TreeDynamic<ParkingElement>();
		parkingT.setRoot(null);	
		setFloors(ParkingConf.FLOORS);
		
	}
	
	
	/**
	 * @param floors Define el número de plantas de la estructura
	 */
	public void setFloors(int floors){
		
		for(int i=1; i<=floors;i++){
			parkingT.addChild(new ParkingFloor(i));
		}		
	}
	
		
//--------------------------------------  Nuevas Rutinas Asignacion pesos plazas ------------------------------- 
	
	/**
	 * Este método se utiliza para asignar pesos a las plazas en función
	 * de la puerta objetivo. Cada plaza tiene una variable "value" donde se 
	 * acumula el valor. Para lograr este objetivo, se hace uso de arreglos 
	 * pre-establecidos en la clase estática "ParkingConfig", los cuales definen
	 * el  path o la ruta de busqueda en cada planta.
	 * @param gate Puerta objetivo
	 * @param space Plaza a valorar
	 */
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
	
	
	/**
	 * Este método es una función auxiliar al método "setDistance(gate, space)". Se valora todas las zonas dentro de cada planta.
	 * @param loop Corresponde al area a supervisar en el método "setDistance(gate, space)"
	 * @param spaceZone la zona a la que pertenece la plaza que se esta valorando.
	 * @return
	 */
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
	
	
	/**
	 * Método encargado de encontrar la mejor plaza. Se encolan las plazas con menor distancia
	 * a la puerta objetivo de cada sección, se clasifican y se retorna la mejor plaza para la
	 * demanda en curso.
	 * @param type Tipo de planza normal o familiar
	 * @param gate Puerta Objetivo
	 * @param time Tiempo actual del parking
	 * @return retorna la mejor plaza libre en función de los parametros "type" y "gate" 
	 */
	public ParkingSpace getTicket(ParkingConf.TType type, ParkingConf.TGate gate, int time){
	
		ParkingSpace pSpace = null;
		
		QueueIF<ParkingElement> queueSpace = new QueueDynamic<ParkingElement> ();
	
		IteratorIF <TreeIF <ParkingElement>> floorsIT = parkingT.getChildren ().getIterator ();
			
		// Primero Itera las plantas
		while(floorsIT.hasNext()){
			TreeIF<ParkingElement> floorTree = floorsIT.getNext(); // Arbol de Niveles
			IteratorIF<TreeIF<ParkingElement>> sectionsIT = floorTree.getChildren().getIterator(); // Iterador de Secciones
			QueueIF<ParkingElement> queueSections = new QueueDynamic<ParkingElement>();
			
			// En cada planta, se itera sobre las areas / secciones
			while(sectionsIT.hasNext()){ 
				ParkingElement result = getTicket(sectionsIT.getNext(),TreeIF.PREORDER, gate,type, time);
	
				// acumula las plazas en una estructura de cola
				queueSections.add(result);
						
			}
			
			// Se ordenan las plazas en función de peso definido en el método "setDistance"
			quickSort(queueSections);
			
			
			// En la estructura queueSpace acumula las mejores plazas encontradas en cada planta
			if(!queueSections.isEmpty())
				queueSpace.add(queueSections.getFirst());			
		}	
		
		
		// Se valora cual es la plaza dentro de queueSpace con planta más baja
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
	
	

	/**
	 * 
	 * Método auxilar  a getTicket(gate, type, time). Recorre las plazas del parking, distinguiendo las que están libres.
	 * En el caso de encontrar una plaza libre, esta se somete a una valoración de la distancia a la puerta objetivo.
	 * 
	 * @param tree Argumento estructura arborera. La raiz es la sección dentro de la planta a valorar, dependiente del método getTicket(gate, type, time).
	 * @param order Orden de creación del Iterador
	 * @param gate Puerta Objetivo
	 * @param type	Tipo de plaza
	 * @param time	Tiempo del parking
	 * @return Retorna la plaza libre de menor distancia a la puerta objetivo.
	 */
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
	
   
    
    /**
     * Ordena la estructura cola en función del peso definido en el método "setDistance"
     * @param queue Cola de plazas con distancia a puerta objetivo valorada.
     */
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
	   

	/**
	 * @return Retorna si quedan espacios libres en el parking.
	 */
	public boolean hasSpace(){
		return ParkingState.hasSpaces();
	}
	
	
	/**
	 * @param type Tipo de plaza: normal o familiar
	 * @return Retorna si quedan espacios libres en el parking para el tipo de plaza.
	 */
	public boolean hasSpace(ParkingConf.TType type){
		return ParkingState.hasSpaces(type);
	}	
}