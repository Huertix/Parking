package eped.parking;

import java.util.Scanner;

import eped.IteratorIF;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.CopyOfVehicleGenerator;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;
import eped.queue.QueueIF;


public class ParkingDispatcher {
	
	//private static CopyOfVehicleGenerator vGenerator;
	private static VehicleGenerator vGenerator;
	private static VehicleQueue vQueueIn;
	private static VehicleQueue vQueueOut;
	private static Parking parking;
	private static int time;

	
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
		
		time = 0;
		
		//vGenerator  = new CopyOfVehicleGenerator(seed);
		vGenerator  = new VehicleGenerator(seed);
		
		vQueueIn   = new VehicleQueue();
		vQueueOut   = new VehicleQueue();
		
		for(int i=0; i<n;i++)
			vQueueIn.add(vGenerator.generate());
		
		 parking = new Parking();
		 
		 
	}
	
	
	private static void dispatch(){
		
		boolean bothQueuesEmpty = false;
		
		while(!bothQueuesEmpty){
		
			if(!vQueueIn.isEmpty()){
				Vehicle v = vQueueIn.getFirst();
			
			
				if(v.getHour() <= time){
					vQueueIn.remove();
					Scanner in = new Scanner(System.in);
				}
						
				if(parking.hasSpace(v.getType())){
			
					ParkingSpace s = parking.getSpace(v.getType(),v.getGate(),v.getId());
					
					if(s!=null){
						s.setCurrentVehicle(v);
						ParkingState.updateUsedSpaces(1);
						vQueueIn.remove();
						System.out.println("Car ID: "+v.getId()+" to Space: "+s.toString());
					}
					else{
						vQueueIn.remove();				
					}
					System.out.println("Queue Length: "+vQueueIn.getLength());
					System.out.println("SpaceLeft: "+ParkingState.getSpaces());
					System.out.println(v.getType().toString()+"Space Left: "+ParkingState.getSpaces(v.getType()));
					
					System.out.println();
				}
			}
		
			
			vQueueOut = parking.getOverTimeVehicleQueue(vQueueOut, time);
			vQueueOut.remove();
			time++;
			System.out.println("time: "+time);
			System.out.println("Queue Out Length: "+vQueueOut.getLength());
			
			
			bothQueuesEmpty = vQueueIn.isEmpty() && vQueueOut.isEmpty();
		}
		
		
		
		
		
		toPrint();
		
		

	}
	
	
	private static void toPrint(){
		while(!vQueueIn.isEmpty()){
			Vehicle v = vQueueIn.getFirst();
			System.out.println("["+
								v.getId()+
								","+
								v.getType()+
								","+
								v.getGate()+
								","+
								v.getHour()+
								"]");
			vQueueIn.remove();
		}
	}

}
