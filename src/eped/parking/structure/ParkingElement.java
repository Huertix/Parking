package eped.parking.structure;

import eped.IteratorIF;
import eped.tree.TreeIF;



public interface ParkingElement extends TreeIF{
	
	public IteratorIF<Object> getIterator();

}
