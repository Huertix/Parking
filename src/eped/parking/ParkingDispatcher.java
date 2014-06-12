/*
 * Esta clase se encarga del control de los mensajes de erros producidos 
 * por interacciones con los argumentos a la hora de ejecutar la aplicación
 * 
 */ 

package eped.parking;

import java.io.IOException;

import eped.Writer;

import java.util.Date;

import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;
import eped.parking.vehicle.VehicleTree;
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;
import eped.tree.BTreeIF;


/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ParkingDispatcher {
	
	private static VehicleGenerator vGenerator;
	private VehicleQueue vQueueIn;
	private VehicleQueue vQueueOut;
	private BTreeIF<Vehicle> VehicleTimeTreeAVL;
	private Parking parking;
	private int time;
	
	
	public static void main(String[] args) {
	
		try{
			
			long lStartTime = new Date().getTime();
			ParkingDispatcher pDispatcher = new ParkingDispatcher();
			
			int n = Integer.parseInt(args[0]);
			int seed = Integer.parseInt(args[1]);
					
			pDispatcher.init(n, seed);
				
			pDispatcher.dispatch();
			
			long lEndTime = new Date().getTime(); // end time
	        
			long difference = lEndTime - lStartTime; // check different
			 
			System.out.println("Elapsed milliseconds: " + difference);
				
		}catch(Exception ex){
			ExceptionManager.getMessage(ex);	
		}
		
		
		
	}
	
	private void init(int n, int seed){
				
		time = 0;
		
		vGenerator  = new VehicleGenerator(seed);
		
		vQueueIn   = new VehicleQueue();
		vQueueOut   = new VehicleQueue();
		
		for(int i=0; i<=n;i++)
			vQueueIn.add(vGenerator.generate());
		
		 parking = new Parking();
		 
		 
	}
	
	
	private void dispatch() throws IOException{
				
		VehicleTimeTreeAVL = new VehicleTree();
		
		Writer w = new Writer();
		
		boolean bothQueuesEmpty = false;
		
		while(!bothQueuesEmpty){
			
		
			//------------------------- Gestión de entrada
			if(!vQueueIn.isEmpty()){
				Vehicle v = vQueueIn.getFirst();
				v.setTimeToGo(v.getHour()+time);
					
								
				if(parking.hasSpace(v.getType())){
	
					ParkingSpace s = parking.getTicket(v.getType(),v.getGate(),time);
									
					if(s!=null){
		
						v.setSpace(s);
						s.setCurrentVehicle(v);
						VehicleTimeTreeAVL = VehicleTimeTreeAVL.insert(v);

						vQueueIn.remove();
						String line = "ENTRA: "+v.getId()+
								" - "+v.getType()+
								" - "+v.getGate()+
								" - "+v.getHour()+
								" - "+s.toString();
						w.write(line);
						System.out.println(line);	
					}	
				}
			}
			

			boolean stop = VehicleTimeTreeAVL==null;
			
			while(!stop){
				
				if(!VehicleTimeTreeAVL.isEmpty()){
					Vehicle v2out = VehicleTimeTreeAVL.findMin().getRoot();
					
					
					
					if(v2out.getTimeToGo()<=time){
						ParkingSpace s2out = v2out.getSpace();
						s2out.setCurrentVehicle(null);
						vQueueOut.add(v2out);

						VehicleTimeTreeAVL = VehicleTimeTreeAVL.remove(v2out);
						if(VehicleTimeTreeAVL==null){
							stop=true;
							continue;
						}
					}
					else
						stop=true;
				}		
				else
					stop=true;		
			}
			
			// ------------------------- Gestion de salida
			if(!vQueueOut.isEmpty()){
				
				//vQueueOut = queueSort(vQueueOut);
				
				Vehicle v = vQueueOut.getFirst();			
				String line = "SALE: "+v.getId()+
						" - "+v.getType()+
						" - "+v.getGate();
				w.write(line);
				System.out.println(line);	
				
				vQueueOut.remove();
			}
			time++;
			bothQueuesEmpty = vQueueIn.isEmpty() && VehicleTimeTreeAVL==null;
		}
	}
	
	
	public VehicleQueue queueSort(VehicleQueue q){
		
		
		
		if(q.isEmpty()) return q;
		
		VehicleQueue aux = new VehicleQueue();
		
		Vehicle v = (Vehicle) q.getFirst();
		q = (VehicleQueue) q.remove();
		VehicleQueue returnedQueue = queueSort(q);
		
		if(returnedQueue.isEmpty())
			return (VehicleQueue) returnedQueue.add(v);
		else{
		
			while(!returnedQueue.isEmpty()){
				if(v.getSpace().getFloor()<=returnedQueue.getFirst().getSpace().getFloor())
					aux.add(v);
				else
					aux.add(returnedQueue.getFirst());
				
				
				returnedQueue = (VehicleQueue) returnedQueue.remove();
				
			}
			
			return aux;
		}
	}
	
}
