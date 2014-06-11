package eped.parking;

import eped.IteratorIF;
import eped.parking.structure.ParkingElement;
import eped.parking.structure.ParkingSpace;
import eped.parking.vehicle.Vehicle;
import eped.parking.vehicle.VehicleQueue;
import eped.queue.QueueDynamic;
import eped.queue.QueueIF;
import eped.tree.TreeIF;



/**
 * @author David Huerta - 47489624Y - 1¼ EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ParkingScheduler {

	
	private VehicleQueue vQueueOut;
	
	public ParkingScheduler() {
	vQueueOut   = new VehicleQueue();
	}
	
	
	
	
	public VehicleQueue getQueueOut(int time, TreeIF<ParkingElement> tree){
		
		
		updateQueueOut(tree, time);
		return vQueueOut;
	}
	
		
	public QueueIF<ParkingElement> updateQueueOut(TreeIF<ParkingElement> tree, int time){
		QueueIF<ParkingElement> traverse = new QueueDynamic<ParkingElement> ();
		ParkingElement element = tree.getRoot ();
		
		if(element!=null && element.getClass()==ParkingSpace.class){
			ParkingSpace space = (ParkingSpace) element;
			
			Vehicle v = space.getCurrentVehicle();
			if(v!=null && v.getTimeToGo() <= time){				
				vQueueOut.add(v);					
				ParkingState.updateUsedSpaces(-1);
				
				if(v.getType()== ParkingConf.TType.familiar)
					ParkingState.updateFamiliarUsedSpaces(-1);
				else
					ParkingState.updateNormalUsedSpaces(-1);
				
				space.setCurrentVehicle(null);
			}
		}
		
		
        traverse.add (element);
        IteratorIF <TreeIF <ParkingElement>> childrenIt = tree.getChildren ().getIterator ();
        while (childrenIt.hasNext ()) {
            TreeIF <ParkingElement> aChild = childrenIt.getNext ();
            QueueIF <ParkingElement> aTraverse = updateQueueOut (aChild,time);
            addAll (traverse, aTraverse);
        }
        return traverse;
		
	}
	
	
    private void addAll (QueueIF<ParkingElement> q, QueueIF<ParkingElement> p){
        while (!p.isEmpty ()) {
        	ParkingElement element = p.getFirst ();
            q.add (element);
            p.remove ();
        }
    }
    
}
