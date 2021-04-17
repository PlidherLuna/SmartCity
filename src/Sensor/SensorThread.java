package Sensor;

import Car.Car;
import RoadListPanel.ListUpdater;
import RoadListPanel.RoadListUpdaterImplement;
import SensorListPanel.SensorListUpdaterImplement;
import Strategy.AlternatePlatesStrategy;
import Strategy.CloseRoadStrategy;
import Strategy.TrafficStrategy;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//Classe utilizzata come thread di un sensore

/**Classe utilizzata come comportamento di un sensore.
 * Simula il comportamento di un sensore al passare di ogni auto.
 * Le auto vengono create ogni 4 secondi.
 * In base alla categoria dell'auto si avrà un impatto diverso sull'inquinamento e la temperatura.
 * Verranno incrementati tutti i parametri, e di conseguenza verranno aggiornati tutti i componenti del sistema dipendenti dai sensori.
 * I panels, le strade ed il DB.
 * In caso di Codice ROSSO verrà stabilita una strategia per la strada.
 */
public class SensorThread extends Thread{

    private Sensor s;
    private TrafficStrategy strat = null;

    @Override
    public void run() { //Permette di simulare un sensore
        super.run();

        while(true){
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Dopo tot secondi ogni thread crea un'auto randomica
            Car randomCar = new Car();
            int category = randomCar.getCategory();
            boolean passed = true;
            if(strat != null) {
                if(s.getRoad().getStatus() == "GREEN"){
                    s.setStrategy(null);
                    strat = null;
                }
                else
                    passed = strat.limitTraffic(randomCar,s.getRoad().getAddress());
            }
            if(passed == true) {
                //In base alla categoria dell'auto, si avra' un impatto diverso sull'inquinamento
                if (category == 1) { //PETROL
                    s.addPollution(0.5F);
                    s.increaseCnt();
                } else if (category == 0) { //DIESEL
                    s.addPollution(0.3F);
                    s.increaseCnt();
                } else if (category == 2) { //GAS
                    s.addPollution(0.1F);
                    s.increaseCnt();
                } else {//ELETTRICA
                    s.increaseCnt();
                }
            }
            //Si aggiorna lo stato del sensore in base ai limiti della strada
            s.statusUpdate();

            //Si aggiorna il sensore nel DB
            SensorStatusDB sensorUpdater = new SensorStatusDBImplement(s);
            sensorUpdater.update();

            //Si aggiorna la grafica di ogni Panel
            ListUpdater roadUpd = new RoadListUpdaterImplement(s.getRoad());//ROADS
            roadUpd.update();
            ListUpdater sensorUpd = new SensorListUpdaterImplement(s);//SENSORS
            sensorUpd.update();

            //Inizia una strategia se si arriva al codice ROSSO
            strat = s.getStrategy();
            if (s.getStatus() == "RED" && strat == null) {
                startStrategy();
            }
        }
    }

    /**
     * Avviata in caso di codice ROSSO stabilisce con il 50% di possibilità una tra le due strategie (Auto alterne o Strada chiusa).
     */
    public void startStrategy(){
        Random rn = new Random();
        int choice = 1 + rn.nextInt(2);
        if(choice == 1)
            strat = new AlternatePlatesStrategy();
        else
            strat = new CloseRoadStrategy();
        s.setStrategy(strat);
    }

    public SensorThread(Sensor sensor){
        s = sensor;
    }
}
