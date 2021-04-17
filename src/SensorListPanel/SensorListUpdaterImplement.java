package SensorListPanel;

import CardsForScrollers.SensorCard;
import ContextApplication.ContextApplication;
import Road.Road;
import RoadListPanel.ListUpdater;
import Sensor.Sensor;
import SensorListPanel.RoadSensorList;

import java.util.ArrayList;

/**Classe per l'aggiornamento del panel della lista dei sensori
 */
public class SensorListUpdaterImplement implements ListUpdater {

    private Sensor sensor;

    public SensorListUpdaterImplement(Sensor s){
        sensor = s;
    }

    /**
     * Aggiorna i panel di ogni sensore
     */
    @Override
    public void update() {
        ArrayList<SensorCard> cards = RoadSensorList.getInstance(sensor.getRoad()).getCards();
        for(SensorCard sc : cards) {
            if(sc.getLblSensid() == sensor.getSensID()) {
                sc.updatePmt(sensor);
                break;
            }
        }
    }
}
