package Strategy;

import Car.Car;

import java.util.Random;

/**STRATEGY Classe che rappresenta una delle due strategie proposte
 * Rappresenta la strategia della strada chiusa, qualsiasi auto che transiterà verrà segnalata.
 */
public class CloseRoadStrategy implements TrafficStrategy {

    public CloseRoadStrategy(){}

    /**
     * Con una probabilità del 10% l'auto passa e viene segnalata
     */
    @Override
    public boolean limitTraffic(Car car,String address) {
        PoliceReporter reporter = new PoliceReporter();
        Random rand = new Random();
        int choice = 1 + rand.nextInt(10);
        //Se esce 1 l'auto passa nonostante non sia il suo turno e viene segnalata
        if (choice == 1) {
            reporter.report(car.getPlate(),address);
            return true;
        }
        return false;
    }
}
