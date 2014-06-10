package eped.parking;

import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;
import eped.stack.StackDynamic;
import eped.stack.StackIF;

public class SpaceQueue extends QueueDynamic<ParkingSpace>{
	QueueIF<ParkingSpace> queue;

	public SpaceQueue() {
		queue = new QueueDynamic<ParkingSpace>();
	}

	
	public void addSort(ParkingSpace space){
		QueueIF<ParkingSpace> queueAux = new QueueDynamic();
		
		if(!queue.isEmpty()){
			while(!queue.isEmpty()){
				if(queue.getFirst().getValue()>space.getValue())
					queueAux.add(queue.getFirst());
				else
					queueAux.add(space);
			}
		}
		
		else
			queueAux.add(space);
			
		queue = queueAux;
	}
	
	
	
	
	

}
