 /*
 *Estructura que encolar‡ consecutivamente en la
 *cola de entrada asociada a dicha clase representada por el artefacto VehicleQueue.
 */


package eped.parking.vehicle;


import eped.list.ListIF;
import eped.queue.*;

/**
 * @author David Huerta - 47489624Y - 1¼ EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class VehicleQueue extends QueueDynamic<Vehicle> {
	
	private QueueIF<Vehicle> queue;

	public VehicleQueue() {
		queue = new QueueDynamic<Vehicle>();
	}

	public VehicleQueue(ListIF<Vehicle> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	public VehicleQueue(QueueIF<Vehicle> queue) {
		super(queue);
		// TODO Auto-generated constructor stub
	}
	
	public void addVehicle(Vehicle v){
		queue.add(v);
		
	}
	
	public Vehicle getVehicle(){
		return queue.getFirst();
	}
	
	
	public boolean hasVehicles(){
		return !queue.isEmpty();
	}


	

}
