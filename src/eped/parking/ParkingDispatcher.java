package eped.parking;

import java.io.IOException;
import eped.Writer;
import java.util.Date;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;



public class ParkingDispatcher {
	
	
	private static VehicleGenerator vGenerator;
	private VehicleQueue vQueueIn;
	private VehicleQueue vQueueOut;
	private Parking parking;
	private int time;
	

	public static void main(String[] args) {	
		try{
			
			long lStartTime = new Date().getTime();
			ParkingDispatcher ps = new ParkingDispatcher();
			
			int n = Integer.parseInt(args[0]);
			int seed = Integer.parseInt(args[1]);
					
			ps.init(n, seed);
				
			ps.dispatch();
			
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
		
		for(int i=0; i<n;i++)
			vQueueIn.add(vGenerator.generate());
		
		 parking = new Parking();	 
	}
	
	
	private void dispatch() throws IOException{
			
		Writer w = new Writer();	
		boolean bothQueuesEmpty = false;
		
		while(!bothQueuesEmpty){		
			//------------------------- GEstion de entrada
			if(!vQueueIn.isEmpty()){
				Vehicle v = vQueueIn.getFirst();
				v.setTimeToGo(v.getHour()+time);
	
				if(parking.hasSpace(v.getType())){
			
					ParkingSpace s = parking.getTicket(v.getType(),v.getGate(),time);
									
					if(s!=null){
					
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
					
					else
						vQueueIn.remove();					
				}		
			}
				
			//---------- devuelve cola de las vehiculos en cola de salida. libera plaza.
			vQueueOut = parking.getQueueOut(time);
		
			// ------------------------- Gestion de salida
			if(!vQueueOut.isEmpty()){
				
				Vehicle v = vQueueOut.getFirst();
				String line = "SALE: "+v.getId()+
						" - "+v.getType()+
						" - "+v.getGate();
				w.write(line);
				System.out.println(line);			
				vQueueOut.remove();
			}
			time++;

			bothQueuesEmpty = vQueueIn.isEmpty() && ParkingState.getUsedSpaces()==0;

		}
		
		System.out.println("Parking Vacio");
	}

}
