package eped.parking;

public class GZElement{
	
	private ParkingConf.TGate gate;
	private ParkingConf.TZone zone;
	
	GZElement(ParkingConf.TGate gate, ParkingConf.TZone zone){
		this.gate = gate;
		this.zone = zone;
	}
	
	public ParkingConf.TGate getGate(){
		return gate;
	}
	
	public ParkingConf.TZone getZone(){
		return zone;
	}

}
