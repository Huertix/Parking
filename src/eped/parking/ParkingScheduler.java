 /*
 * Esta clase, encargada de gestionar ‡gilmente el tiempo de permanencia 
 * de los veh’culos dentro del parking, utiliza una estructura de arbol binario 
 * AVL auxiliar, implementada especificamente para este ejercicio, donde se insertan 
 * los veh’culos en funci—n del tiempo de permanencia.
 */

package eped.parking;

import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleQueue;
import eped.parking.vehicle.VehicleTree;
import eped.tree.BTreeIF;


/**
 * @author David Huerta - 47489624Y - 1¼ EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ParkingScheduler{
	
	private BTreeIF<Vehicle> VehicleTimeTreeAVL;
	
	public ParkingScheduler(){
		VehicleTimeTreeAVL = new VehicleTree();	
	}
	
	
	
	/**
	 * @param v Veh’culo a insertar
	 */
	public void insertVehicle(Vehicle v){
		VehicleTimeTreeAVL = VehicleTimeTreeAVL.insert(v);
	}
	
	
	/**
	 * @param queue Cola de salida existente
	 * @param time Tiempo del parking
	 * @return Retorna la cola de veh’culos actualizada.
	 */
	public VehicleQueue getNext(VehicleQueue queue, int time){
		
		boolean stop = VehicleTimeTreeAVL==null;
		
		while(!stop){
			
			if(!VehicleTimeTreeAVL.isEmpty()){
				Vehicle v2out = VehicleTimeTreeAVL.findMin().getRoot();
		
				if(v2out.getTimeToGo()<=time){
					ParkingSpace s2out = v2out.getSpace();
					s2out.setCurrentVehicle(null);
					queue.add(v2out);

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
		
		return queue;
		
	}
	
	
	/**
	 * @return Retorna verdad 
	 */
	public boolean hasNext(){
		return VehicleTimeTreeAVL!=null;
	}
}
