package eped.parking;

import eped.list.ListIF;
import eped.list.ListStatic;

public final class ParkingConf {
	
	public static final int FLOORS = 6;
	public static final int AREAS = 4;
	public static final int SPACES = 3;
	
	private static Integer[] a= {1,2,4,3};
	private static Integer[] b= {2,3,1,4};
	private static Integer[] c= {3,4,2,1};
	private static Integer[] d= {4,1,3,2};
	private static Integer[] areasPath = {1,2,4,3,4,1,3,2,2,3,1,4,3,4,2,1};
	

	public enum TGate{
		A,
		B,
		C,
		D
	}
	
	public enum TZone {
		I,
		II,
		III,
		IV
	}
	
	public enum TType {
		normal,
		familiar
	}
	
	public static Integer[] getAreasPath(){
		return areasPath;
		
	}

	
	public static Integer[] getSearchingPath(TGate gate){
		
		switch(gate){
		case A: return a;
		case B: return b;
		case C: return c;
		case D: return d;
		default: return null;
		
		}
	}
}
