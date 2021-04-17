package RoadListPanel;

import Road.Road;
import ContextApplication.ContextApplication;
import CardsForScrollers.RoadCard;

import java.util.ArrayList;

//Classe per l'aggiornamento visivo di tutte le strade nel sistema

/**Classe per l'aggiornamento visivo di tutte le strade nel sistema
 */
public class RoadListUpdaterImplement implements ListUpdater {

    private Road road;

    public RoadListUpdaterImplement(Road road){
        this.road = road;
    }

    /**
     * Richiama i metodi di aggiornamento dei panel di ogni strada
     */
    @Override
    public void update() {
        ArrayList<RoadCard> cards = RoadListPanel.getInstance().getCards();
        for(RoadCard rc : cards){
            if(rc.getTxtNomestrada().equals(road.getAddress())) {
                rc.updatePmt(road);
                break;
            }
        }
    }
}
