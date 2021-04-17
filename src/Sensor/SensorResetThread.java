package Sensor;

import ContextApplication.ContextApplication;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**Simula il passare dell'inquinamento nel tempo
 * Ogni 40 secondi il Thread diminuirà i parametri di tutti i sensori così da simulare una situazione reale.
 */
public class SensorResetThread {
    private ArrayList<Sensor> sensors;
    Thread executor;

    public SensorResetThread(){
        sensors = ContextApplication.getInstance().getSensors();
    }

    public void run() {
        executor = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Resetting");
                    for (Sensor s : sensors) {
                        s.removePollution(5);
                        s.decreaseCnt(10);
                    }
                }
            }
        });

        executor.start();
    }

    public void stopper(){
        executor.stop();
    }
}
