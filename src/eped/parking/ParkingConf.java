/*
 * Esta clase se encarga del control de los mensajes de erros producidos 
 * por interacciones con los argumentos a la hora de ejecutar la aplicación
 * 
 */ 

package eped.parking;

/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */

public final class ParkingConf {
	
	public static final int FLOORS = 6;
	public static final int AREAS = 4;
	public static final int SPACES = 3;
	
	private static Integer[] a= {1,2,4,3};
	private static Integer[] b= {2,3,1,4};
	private static Integer[] c= {3,4,2,1};
	private static Integer[] d= {4,1,3,2};
	private static Integer[] areasPath = {1,2,4,3,4,1,3,2,2,3,1,4,3,4,2,1}; 
	private static Integer[] areasPath0 = {1,2,4,3};
	private static Integer[] areasPath1 = {4,1,3,2};
	private static Integer[] areasPath2 = {2,3,1,4};
	private static Integer[] areasPath3 = {3,4,2,1};
	
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
	
public static Integer[] getNextAreaPath(int a){
		
		switch(a){
		case 0: return areasPath0;
		case 1: return areasPath1;
		case 2: return areasPath2;
		case 3: return areasPath3;
		default: return null;
		
		}
	}
}
