package ContextApplication;

import Database.Database;
import Road.Road;
import Sensor.Sensor;

import java.util.ArrayList;
import Road.RoadSelectDB;
import Road.RoadSelectDBImplement;

/**SINGLETON
 * Classe utilizzata per contenere i sensori e le strade i quali sono presenti nel database.
 * Permette di fornire un accesso a tutte le classi che richiedono queste informazioni.
 */

public class ContextApplication {
    private ArrayList<Sensor> sensors;
    private ArrayList<Road> roads;
    private static ContextApplication instance = null;

    private ContextApplication(){
        reload();
    }

    /**
     * Aggiorna i sensori e le strade richiedendoli al DB
     */
    public void reload(){
        sensors = new ArrayList<Sensor>();
        RoadSelectDB roadSelector = new RoadSelectDBImplement();
        roads = roadSelector.selectAllRoads();
        for(Road r:roads) {
            for(Sensor s:r.getSensors()) {
                sensors.add(s);
            }
        }
    }

    public static ContextApplication getInstance() {
        if(instance == null)
            instance = new ContextApplication();
        return instance;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public Road getRoadByAddress(String address){
        for(Road r: roads){
            if(r.getAddress().equals(address)) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }
}
