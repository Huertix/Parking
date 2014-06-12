/*
 *Clase con método estatico main. Esta clase es la encargada de inicilizar la cola de entrada de vehículos,
 *la estructura del parking. Despues de la inicialización para a llamar al método "dispatch", encargado de 
 *gestionar la entrada y la salida de vehículos.
 * 
 */ 


package eped.parking;

import eped.parking.ParkingConf.TType;


/**
 * @author David Huerta - 47489624Y - 1º EPED 2013-2014 - Las Tablas
 * @version Version 1
 */
public class ParkingState {

	private static int usedSpaces = 0;
	private static int usedNormalSpaces = 0;
	private static int usedFamiliarSpaces = 0;
	private static int absolutSpaceID = 0;
	private static int nextSpaceID = 1;

	
	
	/**
	 * @return Retorna los espacios disponibles
	 */
	public static int getSpaces(){
			return absolutSpaceID - usedSpaces;
	}
	
	
	
	/**
	 * @param type Tipo de Vehículo. Normal o Familiar
	 * @return Retorna los espacios disponibles en funcion del tipo de vehículo
	 */
	public static int getSpaces(ParkingConf.TType type){
		 int a = (absolutSpaceID/2);		
		 
		 
		 if(type.equals(ParkingConf.TType.Normal)){
			 int b =  usedNormalSpaces;
			 return a - b;
		 }
		 else{
			 int b = usedFamiliarSpaces;
			 return a - b;
		 }
	}
	
	
	
	/**
	 * @return Retorna verdadero si dispone de plazas libres
	 */
	public static boolean hasSpaces(){
		return getUsedSpaces() < getAbsolutSpaces();	
	}
	
	
	
	/**
	 * @param type Tipo de Vehículo. Normal o Familiar
	 * @return Retorna verdadero si dispone de plazas libres para tipo de vehículo
	 */
	public static boolean hasSpaces(ParkingConf.TType type){
		
		int value = getSpaces(type);
		return  value > 0;		
	}
	
	
	
	/**
	 * @param update actualiza las plazas usadas (1 plaza usada / -1 plaza liberada)
	 */
	public static void updateUsedSpaces(int update){
		usedSpaces += update;
	}
	
	
	
	/**
	 * @param update update actualiza las plazas Normales usadas (1 plaza usada / -1 plaza liberada)
	 */
	public static void updateNormalUsedSpaces(int update){
		usedNormalSpaces += update;
	}
	
	
	
	/**
	 * @param update update actualiza las plazas Familiares usadas (1 plaza usada / -1 plaza liberada)
	 */
	public static void updateFamiliarUsedSpaces(int update){
		usedFamiliarSpaces += update;
	}
	
	

	/**
	 * @return número de plazas ocupadas
	 */
	public static int getUsedSpaces(){
		return getNormalSpaces() + getFamiliarSpaces();
	}
	
	
	/**
	 * @return Número total de plazas en el parking
	 */
	public static int getAbsolutSpaces(){
		return absolutSpaceID;
	}
	

	/**
	 * 
	 * @param Vehicle type (familiar or normal)
	 * @return next space ID (1,3,5) or (2,4,6)
	 */
	public static int getNextSpaceID(TType type){
		int next = 0;
		
		if(nextSpaceID > ParkingConf.SPACES*2)
			nextSpaceID = 1;
		
		if(nextSpaceID<2 && type.equals(TType.Normal))
			nextSpaceID ++;
				
		next = nextSpaceID;
		absolutSpaceID++;
		nextSpaceID += 2;
		return next;
			
			
		
	}

	
	/**
	 * @return Retorna las plazas normales usadas
	 */
	public static int getNormalSpaces() {
		return usedNormalSpaces;
	}


	/**
	 * @return Retorna las plazas familiares usadas
	 */
	public static int getFamiliarSpaces() {
		return usedFamiliarSpaces;
	}

	

}
