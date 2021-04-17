package Sensor;

import Observer.Observable;
import Observer.Observer;
import Road.Road;
import Car.Car;
import Strategy.AlternatePlatesStrategy;
import Strategy.CloseRoadStrategy;
import Strategy.TrafficStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**Dipositivi che rilevano l'inquinamento, la temperatura e le auto della strada.
 * Implementano un pattern Observer e fungono da osservabili ovvero che al cambiare del loro stato lo notificano
 * agli observer (che in questo caso sono le strade).
 */
public class Sensor implements Observable {
    private String sensID;
    private Road road;
    private float pollutionP;
    private float temperatureP;
    private int carCounter;
    private String status;
    /**
     * Ogni sensore avrà un Thread che simula il suo comportamento.
     */
    private SensorThread executor;
    private final int DIESEL = 0;
    private final int PETROL = 1;
    private final int GAS = 2;
    private ArrayList<Observer> observers;


    public Sensor(){
        pollutionP = 0;
        temperatureP = 20;
        carCounter = 0;
        status = "GREEN";
        executor = new SensorThread(this);
    }

    public Sensor(String sensID, float pollutionP, float temperatureP, int carCounter,Road road){
        this.sensID = sensID;
        this.pollutionP = pollutionP;
        this.temperatureP = temperatureP;
        this.carCounter = carCounter;
        this.road = road;
        observers = new ArrayList<>();
        attach(road);
        statusUpdate();
        executor = new SensorThread(this);
    }

    public TrafficStrategy getStrategy(){
        return road.getStrategy();
    }

    /**
     * Ogni sensore in base al suo stato avrà la possibilità di avviare una strategia per limitare il traffico
     */
    public void setStrategy(TrafficStrategy strategy){
        road.setStrategy(strategy);
    }

    /**
     * Permette l'avvio del Thread del sensore
     */
    public void starter(){
        executor.start();
    }

    /**
     * Permette lo stop del Thread del sensore
     */
    public void stopper(){
        executor.stop();
    }

    /**
     * Aggiorna lo stato del sensore in base ai limiti indicati dalla strada
     */
    public void statusUpdate(){
        if(pollutionP <= road.getPollutionLimit() && temperatureP <= road.getTempLimit() && carCounter <= road.getCarLimit())
            status = "GREEN";
        else if(pollutionP > road.getPollutionLimit() && temperatureP > road.getTempLimit() && carCounter > road.getCarLimit())
            status = "RED";
        else if((pollutionP > road.getPollutionLimit() || temperatureP > road.getTempLimit()))
            status = "YELLOW";
        else
            status = "GREEN";
        notifyObs();
    }

    /**
     * Aggiunta dell'inquinamento e temperatura.
     * Sono correlati, ogni 30 sull'indice di inquinamento equivale ad 1 grado celsius
     */
    public void addPollution(float p){
        pollutionP += p;
        temperatureP += (0.003F * p * 10);
    }

    /**
     * Rimozione del sensora dalla strada di appartenenza
     */
    public void removeFromRoad(){
        detach(road);
        road.removeSensor(this);
    }

    /**
     * Aumento contatore delle auto
     */
    public void increaseCnt(){
        carCounter++;
    }

    /**
     * Decremento contatore delle auto
     */
    public void decreaseCnt(int n){
        if(n <= carCounter)
            carCounter -= n;
        else
            carCounter = 0;
    }

    public void setID(String sensID) {
        this.sensID = sensID;
    }

    public String getSensID() {
        return sensID;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Road getRoad() {
        return road;
    }

    public String getStatus() {
        return status;
    }

    public float getPollutionP() {
        return pollutionP;
    }

    public float getTemperatureP() {
        return temperatureP;
    }

    public int getCarCounter() {
        return carCounter;
    }

    /**
     * Rimozione inquinamento e temperatura
     */
    public void removePollution(float p) {
        float tempToRemove = 0.003F * p * 10;
        if(p <= pollutionP && tempToRemove <= temperatureP) {
            pollutionP -= p;
            temperatureP -= tempToRemove;
        }
        else if(p > pollutionP || tempToRemove > temperatureP){
            pollutionP = 0;
            temperatureP = 0;
        }
    }

    /**
     * Aggiunta degli observer
     */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifica agli observer
     */
    @Override
    public void notifyObs() {
        for(Observer obs: observers)
            obs.update();
    }

    /**
     * Rimozione degli observer
     */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }
}
