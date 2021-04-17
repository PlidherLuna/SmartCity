package Strategy;

import Car.Car;
import Frames.SwitcherONOFF;
import Road.Road;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Random;

/**STRATEGY Classe che rappresenta una delle due strategie proposte
 * Rappresenta la strategia delle auto alternate, esse possono transitare solo se passano nel loro turno rispettivo.
 */
public class AlternatePlatesStrategy implements TrafficStrategy {

    private int lastNumber;
    private static int counter;
    private String turn;

    public AlternatePlatesStrategy(){
        turn = "PARI";
        counter = 0;
    }

    /**
     * Limita il traffico con la seguente strategia
     * Turno = "PARI" possono passare solo le auto con numero pari es. AA002AA
     * Turno = "DISPARI" possono passare solo le auto con numero dispari es. AA003AA
     * Ogni turno è settato per durare 10 auto che passano
     * Se il turno non viene rispettato viene creato un reporter che si occuperà della segnalazione
     * Se l'auto si trova al sensore e non è il suo turno, essa passa con una probabilità del 10% e verrà segnalata.
     */
    @Override
    public boolean limitTraffic(Car car,String address) {
        counter++;
        if(counter%10 == 0){
            changeTurn();
            counter = 0;
        }

        PoliceReporter reporter = new PoliceReporter();
        String value = car.getPlate();
        String intValue = value.replaceAll("[^0-9]", ""); //Preleva solo i numeri della targa
        lastNumber = Integer.parseInt(intValue);
        //Se la targa è pari ma il turno è dispari l'auto viene segnalata
        if(lastNumber%2 == 0 && turn.equals("DISPARI")) {
            //Stabilisco randomicamente se l'auto scegle di passare o meno con una percentuale del 10%
            Random rand = new Random();
            int choice = 1 + rand.nextInt(10);
            //Se esce 1 l'auto passa nonostante non sia il suo turno e viene segnalata
            if(choice == 1) {
                reporter.report(car.getPlate(),address);
                return true;
            }
        }
        //Se la targa è dispari ma il turno è pari l'auto viene segnalata
        else if(lastNumber%2 > 0 && turn.equals("PARI")) {
            //Stabilisco randomicamente se l'auto scegle di passare o meno con una percentuale del 10%
            Random rand = new Random();
            int choice = 1 + rand.nextInt(10);
            //Se esce 1 l'auto passa nonostante non sia il suo turno e viene segnalata
            if(choice == 1) {
                reporter.report(car.getPlate(),address);
                return true;
            }
        }
        return false;
    }

    /**
     * Funzione per il cambio di turno
     */
    public void changeTurn(){
        if(turn.equals("PARI"))
            turn = "DISPARI";
        else
            turn = "PARI";
    }
}
