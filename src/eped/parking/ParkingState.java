package eped.parking;

import eped.parking.ParkingConf.TType;

public class ParkingState {

	

	private static int usedSpaces = 0;
	private static int usedNormalSpaces = 0;
	private static int usedFamiliarSpaces = 0;
	private static int absolutSpaceID = 0;
	private static int nextSpaceID = 1;

	public static int getSpaces(){
			return absolutSpaceID - usedSpaces;
	}
	
	public static int getSpaces(ParkingConf.TType type){
		 int a = (absolutSpaceID/2);		
		 
		 
		 if(type.equals(ParkingConf.TType.normal))
			  return a - usedNormalSpaces;
		 else
			 return a - usedFamiliarSpaces;
	
	}
	
	public static boolean hasSpaces(){
		return getNormalSpaces() > 0;	
	}
	
	public static boolean hasSpaces(ParkingConf.TType type){
		
		return getSpaces(type) > 0;		
	}
	
	
	public static void updateUsedSpaces(int update){
		usedSpaces += update;
	}
	
	public static void updateNormalUsedSpaces(int update){
		usedNormalSpaces += update;
	}
	
	public static void updateFamiliarUsedSpaces(int update){
		usedFamiliarSpaces += update;
	}
	
	public static void addUsedSpaces(){
		usedSpaces++;
	}
	
	public static int getUsedSpaces(){
		return usedSpaces;
	}
	
	
	public static int getAbsolutSpaces(){
		return absolutSpaceID;
	}
	

	/**
	 * 
	 * @param Vehicle type (familiar or normal)
	 * @return next space ID (1,3,5) or (2,4,6)
	 */
	public static int getNextSpaceID( TType type){
		int next = 0;
		
		if(nextSpaceID > ParkingConf.SPACES*2)
			nextSpaceID = 1;
		
		if(nextSpaceID<2 && type.equals(TType.normal))
			nextSpaceID ++;
		
		if(type.equals(TType.normal))
			usedNormalSpaces++;
		else
			usedFamiliarSpaces++;
		
		next = nextSpaceID;
		absolutSpaceID++;
		nextSpaceID += 2;
		return next;
			
			
		
	}

	public static int getNormalSpaces() {
		return usedNormalSpaces;
	}


	public static int getFamiliarSpaces() {
		return usedFamiliarSpaces;
	}

	

}
