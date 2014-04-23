package eped.parking;

public final class ParkingConf {
	
	public static final int FLOORS = 6;
	public static final int AREAS = 4;
	public static final int SPACES = 3;
	

	
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
