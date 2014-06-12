/*
 * Esta clase sirve como configuraci—n de los parametros del programa en tiempo de compilaci—n
 * Aqu’ se pueden definir parametros como:
 * 
 * 		- Nœmero de plantas, areas, secci—n plazas de cada tipo
 * 		- Asignar valores a las zonas, secci—nes y tipos de plaza
 * 		- Definir las rutas de busqueda segœn la puerta objetivo.
 * 
 */ 

package eped.parking;

/**
 * @author David Huerta - 47489624Y - 1Âº EPED 2013-2014 - Las Tablas
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
		Normal,
		Familiar
	}
	
	
	
	/**
	 * @return Retorna la ruta de busqueda a nivel de areas.
	 */
	public static Integer[] getAreasPath(){
		return areasPath;
		
	}

	
	/**
	 * @param gate Puerta Objetivo
	 * @return Retorna la ruta de busqueda en funci—n de la puerta objetivo.
	 */
	public static Integer[] getSearchingPath(TGate gate){
		
		switch(gate){
		case A: return a;
		case B: return b;
		case C: return c;
		case D: return d;
		default: return null;
		
		}
	}
	
	
	
	/**
	 * @param a Valor que corresponde al area a examinar en ese momento
	 * @return Retorna la ruta de busqueda anivel de area en funci—n de la puerta objetivo.
	 **/
	public static Integer[] getNextAreaPath(int k){
		
		switch(k){
		case 0: return a;
		case 1: return d;
		case 2: return b;
		case 3: return c;
		default: return null;
		
		}
	}
}
