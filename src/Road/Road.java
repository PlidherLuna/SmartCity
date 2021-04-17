package Road;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

import Observer.Observable;
import Observer.Observer;
import Sensor.Sensor;
import Strategy.AlternatePlatesStrategy;
import Strategy.CloseRoadStrategy;
import Strategy.TrafficStrategy;

/**Contiene le informazioni della strada
 * Implementa l'observer del pattern Observer grazie al quale aggiorna il suo status dopo il cambiamento dei suoi osservabili.
 * Contiene i limiti dei parametri lo status della strada ed una strategia qualora ce ne fosse bisogno.
 */
public class Road implements Observer {
    private String address;
    private float length;
    private float pollutionLimit;
    private float pollution;
    private float tempLimit;
    private float temp;
    private int carLimit;
    private int cars;
    private String status;
    private ArrayList<Sensor> sensors;
    private TrafficStrategy strategy = null;
    private int sensorLimit;

    public Road(String address,float length, float pollutionLimit, float tempLimit, int carLimit){
        this.address = address;
        this.length = length;
        this.pollutionLimit = pollutionLimit;
        this.tempLimit = tempLimit;
        this.carLimit = carLimit;
        status = "GREEN";
        sensors = new ArrayList<Sensor>();
        sensorLimit = (int)length/50; //Limite di 1 sensore ogni 50mt.
        update();
    }

    public TrafficStrategy getStrategy() {
        return strategy;
    }

    //Fornisco le stringhe della strategia per i panel

    /**
     * Ritorna il nome della strategia (se utilizzata) ai Panel
     */
    public String getStrategyTxt(){
        if(strategy != null) {
            if (strategy instanceof CloseRoadStrategy)
                return "STRADA CHIUSA";
            else if (strategy instanceof AlternatePlatesStrategy)
                return "TRANSITO ALTERNATO";
        }
        return "";
    }

    public void setStrategy(TrafficStrategy strategy){
        this.strategy = strategy;
    }

    public String getAddress() {
        return address;
    }

    public float getLength() {
        return length;
    }

    public float getPollutionLimit() {
        return pollutionLimit;
    }

    public float getTempLimit() {
        return tempLimit;
    }

    public int getCarLimit() {
        return carLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addSensor(Sensor s){
        sensors.add(s);
    }

    /**
     * Calcolo del massimo valore di inquinamento tra i sensori
     * cosi da usarlo come valore di inquinamento generale della strada
     */
    public void maxPollution(){
        float maxP = 0;
        for(Sensor s: sensors){
            if(s.getPollutionP() > maxP)
                maxP = s.getPollutionP();
        }
        pollution = maxP;
    }

    public float getPollution() {
        return pollution;
    }

    /**
     * Calcolo della massima temperatura tra i sensori
     * cosi da usarla come temperatura generale della strada
     */
    public void maxTemp(){
        float maxT = 0;
        for(Sensor s: sensors){
            if(s.getTemperatureP() > maxT)
                maxT = s.getTemperatureP();
        }
        temp = maxT;
    }

    public float getTemp() {
        return temp;
    }

    //Calcolo numero massimo di auto transitate tra i sensori

    /**
     * Calcolo del numero massimo di auto transitate di ogni sensore
     * cosi da usarlo come valore generale della strada.
     */
    public void maxCars(){
        int maxC = 0;
        for(Sensor s: sensors){
            if(s.getCarCounter() > maxC)
                maxC = s.getCarCounter();
        }
        cars = maxC;
    }

    public int getCars() {
        return cars;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    //Rimozione di un sensore dalla lista di questa strada

    /**
     * Rimozione di un sensore dalla collezione di questa strada
     */
    public void removeSensor(Sensor s){
        int index = 0;
        for(Sensor temp:sensors){
            if(temp.getSensID() == s.getSensID()) {
                break;
            }
            index++;
        }
        sensors.remove(index);
    }

    //Calcolo dei valori massimi di ogni parametro per poter monitorare la situazione generale di una strada

    /**
     * Calcolo dello stato generale della strada in base al massimo stato dei sensori
     */
    @Override
    public void update() {
        maxPollution();
        maxTemp();
        maxCars();

        int maxStatus = 0;
        int status;
        for(Sensor s: sensors){
            if(s.getStatus().equals("RED"))
                status = 2;
            else if(s.getStatus().equals("YELLOW"))
                status = 1;
            else
                status = 0;
            if(status >= maxStatus) {
                maxStatus = status;
                setStatus(s.getStatus());
            }
        }
    }
}
