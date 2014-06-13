/*
*Esta clase dispone del m�todo main necesario para arrancar
*el sistema. Cuando se arranca, esta clase invoca a init y genera con �nimo simulativo una colecci�n
*de veh�culos aleatoria invocando iterativamente a los m�todos que a tal efecto dispone la clase
*VehicleGenerator. Cada llamada invoca al constructor de la clase Vehicle con unos par�metros
*reales de valor aleatorio sobre el n�mero de matricula, tipo de veh�culo y puerta de acceso
*objetivo. La clase ParkingDispatcher recoger� estos veh�culos y los encolar� consecutivamente en la
*cola de entrada asociada a dicha clase representada por el artefacto VehicleQueue.
*/ 

package eped.parking;

import java.io.IOException;

import eped.Writer;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;


/**
 * @author David Huerta - 47489624Y - 1� EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ParkingDispatcher {
	
	private static VehicleGenerator vGenerator;
	private VehicleQueue vQueueIn;
	private VehicleQueue vQueueOut;
	private ParkingScheduler pScheduler;
	private Parking parking;
	private int time;
	
	
	/**
	 * @param args 1� argumento: cantidad de veh�culos en la cola de entrada
	 * 			2� argumento: la semilla para generaci�n de veh�culos con datos aleatorios
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
	
		try{	

			if(args.length!=2)
				throw new Exception("Fallo Argumentos");
					
			ParkingDispatcher pDispatcher = new ParkingDispatcher();
			
			int n = Integer.parseInt(args[0]);
			int seed = Integer.parseInt(args[1]);
					
			pDispatcher.init(n, seed);
				
			pDispatcher.dispatch();
			
		}catch(Exception ex){
			ExceptionManager.getMessage(ex);	
		}	
	}
	

	/**
	 * @param n cantidad de veh�culos en la cola de entrada
	 * @param seed semilla para generaci�n de veh�culos con datos aleatorios
	 */
	private void init(int n, int seed){
				
		time = 0;
		
		vGenerator  = new VehicleGenerator(seed);
		pScheduler = new ParkingScheduler();
		
		vQueueIn   = new VehicleQueue();
		vQueueOut   = new VehicleQueue();
		
		for(int i=0; i<=n;i++)
			vQueueIn.add(vGenerator.generate());
		
		 parking = new Parking();	 
	}
	
	
		
	/**
	 *
	 * Para la gesti�n de la salida de veh�culos se apoya en encontrar el/los veh�culos
	 * con menor tiempo de permanencia dentro de la estructura arbol binario auxiliar.
	 * 
	 * @throws IOException
	 */
	private void dispatch() throws IOException{
				
		
		Writer w = new Writer();
		
		boolean bothQueuesEmpty = false;
		
		while(!bothQueuesEmpty){
			
			//------------------------- Gesti�n de entrada
			if(!vQueueIn.isEmpty()){
				Vehicle v = vQueueIn.getFirst();
				v.setTimeToGo(v.getHour()+time);
	
				if(parking.hasSpace(v.getType())){
	
					ParkingSpace s = parking.getTicket(v.getType(),v.getGate(),time);
									
					if(s!=null){
		
						v.setSpace(s);
						s.setCurrentVehicle(v);
						
						if(v.getType().equals(ParkingConf.TType.Familiar))
							ParkingState.updateFamiliarUsedSpaces(1);
						else
							ParkingState.updateNormalUsedSpaces(1);
						
					
						
						pScheduler.insertVehicle(v);

						vQueueIn.remove();				
						
						String line = "ENTRA: "+v.toString()+" - "+s.toString();
						w.write(line);
						System.out.println(line);	
					}	
				}
			}
			

			//------------ Bloque para encontrar los veh�culos con tiempo de permanecia superado.
			vQueueOut = pScheduler.getNext(vQueueOut, time);
			
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
			bothQueuesEmpty = vQueueIn.isEmpty() && !pScheduler.hasNext();
		}
	}

}