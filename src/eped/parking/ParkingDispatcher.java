package eped.parking;

import java.io.IOException;

import eped.Writer;

import java.util.Scanner;

import eped.IteratorIF;
import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.CopyOfVehicleGenerator;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;
import eped.queue.QueueIF;
import eped.tree.TreeIF;
import eped.tree.TreeIterator;


public class ParkingDispatcher {
	
	private static CopyOfVehicleGenerator vGenerator;
	//private static VehicleGenerator vGenerator;
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
		
		vGenerator  = new CopyOfVehicleGenerator(seed);
		//vGenerator  = new VehicleGenerator(seed);
		
		vQueueIn   = new VehicleQueue();
		vQueueOut   = new VehicleQueue();
		
		for(int i=0; i<n;i++)
			vQueueIn.add(vGenerator.generate());
		
		 parking = new Parking();
		 
		 
	}
	
	
	private static void dispatch() throws IOException{
		
		Writer w = new Writer();
		
		boolean bothQueuesEmpty = false;
		
		while(!bothQueuesEmpty){
			
			
		
			
			//------------------------- GEstion de entrada
			if(!vQueueIn.isEmpty()){
				Vehicle v = vQueueIn.getFirst();
				v.setTimeToGo(v.getHour()+time);
				//System.out.println("V In - ID: "+v.getId()+" Type: "+v.getType()+" Time: "+v.getHour()); 
			
				

				//parking.prueba(v);
				
				
				
				
				
				
				
				if(parking.hasSpace(v.getType())){
			
					//ParkingSpace s = parking.getSpace(v.getType(),v.getGate());
					ParkingSpace s = parking.getTicket(v.getType(),v.getGate());
					
					if(s!=null){
						
						//parking.setDistance(v.getGate(), s);
						//System.out.println(s.getValue());
						
						v.setSpace(s);
						s.setCurrentVehicle(v);
						ParkingState.updateUsedSpaces(1);
						vQueueIn.remove();
						String line = "ENTRA: "+v.getId()+
								" - "+v.getType()+
								" - "+v.getGate()+
								" - "+v.getHour()+
								" - "+s.toString();
						w.write(line);
						System.out.println(line);
					}
					else{
						vQueueIn.remove();				
					}
					
					//System.out.println("Queue Length: "+vQueueIn.getLength());
					//System.out.println("SpaceLeft: "+ParkingState.getSpaces());
					//System.out.println(v.getType().toString()+"Space Left: "+ParkingState.getSpaces(v.getType()));
					
					//System.out.println();
				}
				
				
				//if(v.getId()==999)
					//System.out.println(vQueueOut.getLength());
			}
		
			
			//---------- devuelve cola de las vehiculos en cola de salida. libera plaza.
			vQueueOut = parking.getOverTimeVehicleQueue(vQueueOut, time);
			
			// ------------------------- Gestion de salida
			if(!vQueueOut.isEmpty()){
				//if(vQueueOut.getLength()> 1)
					//System.out.println(time);
				Vehicle v = vQueueOut.getFirst();
				String line = "SALE: "+v.getId()+
						" - "+v.getType()+
						" - "+v.getGate();
				w.write(line);
				System.out.println(line);			
				vQueueOut.remove();
			}
			time++;
			//System.out.println();
			//System.out.println();
			//VehicleQueue vAux = new VehicleQueue(vQueueOut);
			
			//while(!vAux.isEmpty()){
			//	System.out.println("Car: "+vAux.getFirst().getId()+" Time: "+vAux.getFirst().getHour());
			//	vAux.remove();
			//}
			//System.out.println("time: "+time);
			//System.out.println("Queue Out Length: "+vQueueOut.getLength());
			
			
			
			
			
			//bothQueuesEmpty = vQueueIn.isEmpty() && vQueueOut.isEmpty();
			bothQueuesEmpty = vQueueIn.isEmpty() && ParkingState.getUsedSpaces()==0;
			//System.out.println("Plazas ocupadas: "+ParkingState.getUsedSpaces());
		}
		
		
		System.out.println("Parking Vacio");
		
		
		//toPrint();
		
		

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
