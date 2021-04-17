package Strategy;

import Car.Car;

/**STRATEGY Interfaccia delle strategie con il metodo in comune
 */
public interface TrafficStrategy {
    public boolean limitTraffic(Car car,String address);
}
