package eped.parking.vehicle;



import java.util.Random;

import eped.parking.tdef.TGate;
import eped.parking.tdef.TType;


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
        TType type  = nextType ();
        TGate gate  = nextGate ();
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
    private TType nextType () {
    
        int rType = this.random.nextInt (2);
        return TType.values ()[rType];
    }
    
   /**
    * Returns a gate.
    * @return a gate.
    */
    private TGate nextGate () {
    
        int rGate = this.random.nextInt (4);
        return TGate.values ()[rGate];
    }
    
   /**
    * Returns the hours.
    * @return the hours.
    */
    private int nextHours () {
    
        return this.random.nextInt (24);
    }

}
