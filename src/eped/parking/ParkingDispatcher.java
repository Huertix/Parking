package eped.parking;

import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;


public class ParkingDispatcher {

	public static void main(String[] args) {
		
		QueueIF<Vehicle> vQueue = new VehicleQueue();
		
		try{
			
			
			int seed = Integer.parseInt(args[0]);
			
			VehicleGenerator vGenerator = new VehicleGenerator(seed);
			
			for(int i=0; i<100;i++){
				vQueue.add(vGenerator.generate());
			}
			
			
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
			
		}catch(Exception ex){
			 ex.printStackTrace();
			 System.out.println("Introduce valor nœmerico");
			
		}
		

	}

}
