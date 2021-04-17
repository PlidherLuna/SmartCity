package SensorListPanel;

import CardsForScrollers.SensorCard;
import ContextApplication.ContextApplication;
import Frames.RoadSensorFrame;
import Frames.SwitcherONOFF;
import Road.*;
import RoadListPanel.RoadListPanel;
import Sensor.Sensor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

//Panel contenente le informazioni dei sensori di una specifica strada

/**SINGLETON Panel contenente le informazioni dei sensori
 * Ci sarà una lista di sensori con i relativi parametri e una sezione per la strada con i relativi limiti
 */
public class RoadSensorList extends JPanel {
    private static RoadSensorList instance = null;
    private JPanel test;
    private JPanel contentCards;
    private ArrayList<SensorCard> cards;
    private Road road;
    private JScrollPane scrollPane;

    public static RoadSensorList getInstance(Road road) {
        if(instance == null)
            instance = new RoadSensorList(road);
        return instance;
    }

    //Funzione utile per l'aggiornamento grafico della lista dei sensori

    /**Funzione utile per l'aggiornamento grafico della lista dei sensori
     * Viene ricreato il frame (per motivi di estetica) con la nuova lista dei sensori
     */
    public static RoadSensorList reload(Road road){
        instance = new RoadSensorList(road);
        return instance;
    }

    private RoadSensorList(Road road){
        this.road = road;
        test = new JPanel();
        test.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 306, 250);
        panel.setLayout(null);
        test.add(panel);

        JLabel lblIndirizzo = new JLabel(road.getAddress());
        lblIndirizzo.setBounds(12, 12, 282, 14);
        panel.add(lblIndirizzo);

        JLabel lblPmt = new JLabel("Limite inquinamento: " + road.getPollutionLimit());
        lblPmt.setBounds(12, 54, 282, 14);
        panel.add(lblPmt);

        JLabel lblPmt_1 = new JLabel("Limite temperatura: +(" + road.getTempLimit()+ ")°");
        lblPmt_1.setBounds(12, 80, 282, 14);
        panel.add(lblPmt_1);

        JLabel lblPmt_2 = new JLabel("Limite auto: " + road.getCarLimit());
        lblPmt_2.setBounds(12, 106, 282, 14);
        panel.add(lblPmt_2);

        int nSensori;

        if(road.getSensors() == null)
            nSensori = 0;
        else
            nSensori = road.getSensors().size();

        JLabel lblNumsensori = new JLabel("Numero sensori: " + nSensori);
        lblNumsensori.setBounds(12, 132, 282, 14);
        panel.add(lblNumsensori);

        JButton btnAddsensor = new JButton("AddSensor");
        btnAddsensor.setBounds(25, 170, 98, 24);
        panel.add(btnAddsensor);

        JButton btnIndietro = new JButton("Indietro");
        btnIndietro.setBounds(12, 438, 98, 24);
        test.add(btnIndietro);

        /**
         * Listener del button per tornare al Panel precedente
         */
        btnIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RoadSensorFrame.getInstance().getContentPane().removeAll();
                RoadSensorFrame.getInstance().setContentPane(RoadListPanel.reload().getTest());
                RoadSensorFrame.getInstance().revalidate();
            }
        });

        //Button per aggiungere un sensore alla strada
        /**
         * Listener del button per aggiungere un sensore alla strada
         */
        btnAddsensor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(road.getSensors().size() == (int)road.getLength()/50) JOptionPane.showMessageDialog(null, "Limite sensori per questa strada raggiunto (1 ogni 50mt.)");
                else {
                    //Se i sensori sono attivi non è possibile aggiungere il sensore
                    if(!SwitcherONOFF.getInstance().getSensorStatus()) {
                        AddSensorToRoad addSensorToRoad = new AddSensorToRoadImplement(road);
                        addSensorToRoad.insertSensor();
                        ContextApplication.getInstance().reload();
                        RoadSensorFrame.getInstance().getContentPane().removeAll();
                        Road newRoad = ContextApplication.getInstance().getRoadByAddress(road.getAddress());
                        instance = new RoadSensorList(newRoad);
                        RoadSensorFrame.getInstance().setContentPane(instance.getTest());
                        RoadSensorFrame.getInstance().revalidate();
                    }
                    else JOptionPane.showMessageDialog(null,"Disattivare i sensori prima di proseguire!");
                }
            }
        });
        cardsConstructor();
    }

    /**
     * Funzione utilizzata per la costrunzione del panel scrollabile contenente i panel di ogni sensore
     */
    public void cardsConstructor(){
        road = ContextApplication.getInstance().getRoadByAddress(road.getAddress());
        contentCards = new JPanel();
        contentCards.setLayout(new BoxLayout(contentCards, BoxLayout.PAGE_AXIS));
        cards = new ArrayList<SensorCard>();
        if(road.getSensors() != null) {
            for (Sensor s : road.getSensors()) {
                SensorCard sc = new SensorCard(s);
                cards.add(sc);
                contentCards.add(sc.getCardPanel());
                contentCards.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        scrollPane = new JScrollPane(contentCards);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(318, 12, 400, 377);
        test.add(scrollPane);

    }

    public JPanel getTest() {
        return test;
    }

    public ArrayList<SensorCard> getCards(){
        return cards;
    }

}
