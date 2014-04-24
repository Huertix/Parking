package eped.parking;

import eped.IteratorIF;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;
import eped.queue.QueueIF;


public class ParkingDispatcher {
	
	private static VehicleGenerator vGenerator;
	private static QueueIF<Vehicle> vQueue;
	private static Parking parking;

	
	public static void main(String[] args) {
		
		try{
			
			int n = Integer.parseInt(args[0]);
			int seed = Integer.parseInt(args[1]);
					
			init(n, seed);
			
			
			dispatch();
				
		}catch(Exception ex){
			ExceptionManager.getMessage(ex);	
		}
		
	}
	
	private static void init(int n, int seed){
		
		vGenerator  = new VehicleGenerator(seed);
		
		vQueue   = new VehicleQueue();
		
		for(int i=0; i<=n;i++)
			vQueue.add(vGenerator.generate());
		
		 parking = new Parking();
	}
	
	
	private static void dispatch(){
		
		IteratorIF<Vehicle> vQueueIT = vQueue.getIterator();
		
		while(vQueueIT.hasNext()){
		
			Vehicle v = vQueueIT.getNext();
					
			if(parking.hasSpace(v.getType())){
				ParkingSpace s = parking.getSpace(v.getType(),v.getGate(),v.getId());
			}
		}
		
		
		
		

	}
	
	
	private static void toPrint(){
		while(!vQueue.isEmpty()){
			Vehicle v = vQueue.getFirst();
			System.out.println("["+
								v.getId()+
								","+
								v.getType()+
								","+
								v.getGate()+
								","+
								v.getHour()+
								"]");
			vQueue.remove();
		}
	}

}
