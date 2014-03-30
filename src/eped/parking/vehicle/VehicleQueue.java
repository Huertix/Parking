package eped.parking.vehicle;

import eped.IteratorIF;
import eped.list.ListIF;
import eped.queue.*;

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
