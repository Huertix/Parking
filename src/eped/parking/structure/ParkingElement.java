package eped.parking.structure;

import eped.IteratorIF;
import eped.tree.TreeIF;



public interface ParkingElement {
	
	public IteratorIF<TreeIF<ParkingElement>> getIterator(Integer order);

}
