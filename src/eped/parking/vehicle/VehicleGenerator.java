package eped.parking.vehicle;



import java.util.Random;

import eped.parking.ParkingConf;



public class VehicleGenerator {
    
    private int seed;
    private Random random;
    private static int ids;
    
   /**
    * Constructor for VehicleGenerator.
    * @param seed The generator seed.
    */
    public VehicleGenerator (int seed) {
    
        this.seed = seed;
        this.random = new Random (seed);
        ids = 0;
    }
    
   /**
    * Creates a new vehicle.
    * @return a new vehicle.
    */
    public Vehicle generate () {
    
        int id      = nextId ();
        ParkingConf.TType type  = nextType ();
        ParkingConf.TGate gate  = nextGate ();
        int   hours = nextHours ();
        
        return new Vehicle (id, type, gate, hours);
    }
    
   /**
    * Returns an id.
    * @return an id.
    */
    private int nextId () {
    
        return ids++;
    }
    
   /**
    * Returns a type.
    * @return a type.
    */
    private ParkingConf.TType nextType () {
    
        int rType = this.random.nextInt (2);
        return ParkingConf.TType.values ()[rType];
    }
    
   /**
    * Returns a gate.
    * @return a gate.
    */
    private ParkingConf.TGate nextGate () {
    
        int rGate = this.random.nextInt (4);
        return ParkingConf.TGate.values ()[rGate];
    }
    
   /**
    * Returns the hours.
    * @return the hours.
    */
    private int nextHours () {
    
        return this.random.nextInt (24);
    }

}
