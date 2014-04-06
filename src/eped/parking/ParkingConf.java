package eped.parking;

public final class ParkingConf {
	
	private static final int floors = 6;
	private static final int sections = 4;
	private static final int areas = 4;
	private static final int spaces = 3;

	
	public static int spaceID = 1;
	
	public static int getFloors() {
		return floors;
	}
	public static int getSections() {
		return sections;
	}
	public static int getAreas() {
		return areas;
	}
	public static int getSpaces() {
		return spaces;
	}

	
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
	
	

	
	

}
