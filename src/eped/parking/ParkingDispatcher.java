/*
*Esta clase dispone del método main necesario para arrancar
*el sistema. Cuando se arranca, esta clase invoca a init y genera con ánimo simulativo una colección
*de vehículos aleatoria invocando iterativamente a los métodos que a tal efecto dispone la clase
*VehicleGenerator. Cada llamada invoca al constructor de la clase Vehicle con unos parámetros
*reales de valor aleatorio sobre el número de matricula, tipo de vehículo y puerta de acceso
*objetivo. La clase ParkingDispatcher recogerá estos vehículos y los encolará consecutivamente en la
*cola de entrada asociada a dicha clase representada por el artefacto VehicleQueue.
*/ 

package eped.parking;

import java.io.IOException;

import eped.Writer;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleGenerator;
import eped.parking.vehicle.VehicleQueue;
import eped.parking.vehicle.VehicleTree;
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
	
	
	/**
	 * @param args 1º argumento: cantidad de vehículos en la cola de entrada
	 * 			2º argumento: la semilla para generación de vehículos con datos aleatorios
	 */
	public static void main(String[] args) {
	
		try{	

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
	 * @param n cantidad de vehículos en la cola de entrada
	 * @param seed semilla para generación de vehículos con datos aleatorios
	 */
	private void init(int n, int seed){
				
		time = 0;
		
		vGenerator  = new VehicleGenerator(seed);
		
		vQueueIn   = new VehicleQueue();
		vQueueOut   = new VehicleQueue();
		
		for(int i=0; i<=n;i++)
			vQueueIn.add(vGenerator.generate());
		
		 parking = new Parking();	 
	}
	
	
	
	
	/**
	 * Método encargado de gestionar la entrada y la salida de vehículos.
	 * Se utiliza una estructura de arbol binario AVL auxiliar, implementada
	 * especificamente para este ejercicio, donde se insertan los vehículos 
	 * en función del tiempo de permanencia. El método entra en un bucle while
	 * del que se sale cuando se acaban los vehículos en la cola de entrada y
	 * no quedan vehículos dentro del parking.
	 * 
	 * Para la gestión de la salida de vehículos se apoya en encontrar el/los vehículos
	 * con menor tiempo de permanencia dentro de la estructura arbol binario auxiliar.
	 * 
	 * @throws IOException
	 */
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
			

			//------------ Bloque para encontrar los vehículos con tiempo de permanecia superado.
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

}