package CardsForScrollers;

import ContextApplication.ContextApplication;
import Frames.RoadSensorFrame;
import Frames.SwitcherONOFF;
import Road.Road;
import Road.RoadRemover;
import Road.RoadRemoverImplement;
import RoadListPanel.RoadListPanel;
import SensorListPanel.RoadSensorList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**Panel contenente le informazioni di una strada con i suoi limiti di parametri e i parametri attuali
 */

public class RoadCard extends JPanel {
    private Road road;
    private JPanel cardPanel;
    private JLabel txtNomestrada;
    private JLabel pollutionPmt;
    private JLabel tempPmt;
    private JLabel carsPmt;
    private JLabel strategylbl;
    private JLabel txtStatus;

    public RoadCard(Road road){
        cardPanel = new JPanel();
        this.road = road;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{210, 114, 0};
        gridBagLayout.rowHeights = new int[]{18, 43, 24, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        cardPanel.setLayout(gridBagLayout);

        txtStatus = new JLabel();
        txtStatus.setForeground(new Color(0,102,0));
        txtStatus.setText("Status: " + road.getStatus());
        GridBagConstraints gbc_txtStatus = new GridBagConstraints();
        gbc_txtStatus.anchor = GridBagConstraints.NORTH;
        gbc_txtStatus.insets = new Insets(0, 0, 5, 5);
        gbc_txtStatus.gridx = 0;
        gbc_txtStatus.gridy = 0;
        cardPanel.add(txtStatus, gbc_txtStatus);

        pollutionPmt = new JLabel();
        pollutionPmt.setText("Limite Inquinamento: " + road.getPollutionLimit() + "          Attualmente: " + new DecimalFormat("##.##").format(road.getPollution()));
        GridBagConstraints gbc_txtParametro = new GridBagConstraints();
        gbc_txtParametro.anchor = GridBagConstraints.NORTHWEST;
        gbc_txtParametro.insets = new Insets(0, 0, 5, 0);
        gbc_txtParametro.gridx = 2;
        gbc_txtParametro.gridy = 0;
        cardPanel.add(pollutionPmt, gbc_txtParametro);


        txtNomestrada = new JLabel();
        txtNomestrada.setText("NomeStrada: " + road.getAddress() );
        GridBagConstraints gbc_txtNomestrada = new GridBagConstraints();
        gbc_txtNomestrada.fill = GridBagConstraints.BOTH;
        gbc_txtNomestrada.insets = new Insets(0, 0, 5, 5);
        gbc_txtNomestrada.gridx = 0;
        gbc_txtNomestrada.gridy = 1;
        cardPanel.add(txtNomestrada, gbc_txtNomestrada);

        tempPmt = new JLabel();
        tempPmt.setText("Limite Temperatura: " + road.getTempLimit() + "          Attualmente: " + new DecimalFormat("##.##").format(road.getTemp()));
        GridBagConstraints gbc_txtParametro_1 = new GridBagConstraints();
        gbc_txtParametro_1.anchor = GridBagConstraints.WEST;
        gbc_txtParametro_1.insets = new Insets(0, 0, 5, 0);
        gbc_txtParametro_1.gridx = 2;
        gbc_txtParametro_1.gridy = 1;
        cardPanel.add(tempPmt, gbc_txtParametro_1);

        JButton btnGestione = new JButton("Gestione");
        GridBagConstraints gbc_btnGestione = new GridBagConstraints();
        gbc_btnGestione.anchor = GridBagConstraints.WEST;
        gbc_btnGestione.insets = new Insets(0, 5, 5, 0);
        gbc_btnGestione.gridx = 0;
        gbc_btnGestione.gridy = 2;
        cardPanel.add(btnGestione, gbc_btnGestione);

        /**
         * Listener del button per la gestione della strada di riferimento
         */
        btnGestione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RoadSensorFrame.getInstance().getContentPane().removeAll();
                RoadSensorFrame.getInstance().setContentPane(RoadSensorList.reload(road).getTest());
                RoadSensorFrame.getInstance().revalidate();
            }
        });

        JButton btnRemove = new JButton("Rimuovi");
        GridBagConstraints gbc_btnRemove = new GridBagConstraints();
        gbc_btnRemove.anchor = GridBagConstraints.EAST;
        gbc_btnRemove.insets = new Insets(0, 0, 5, 5);
        gbc_btnRemove.gridx = 0;
        gbc_btnRemove.gridy = 2;
        cardPanel.add(btnRemove, gbc_btnRemove);

        //Button per la rimozione della strada di riferimento
        /**
         * Listener del bottone per la rimozione della strada di riferimento
         */
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!SwitcherONOFF.getInstance().getSensorStatus()) {
                    RoadRemover remover = new RoadRemoverImplement(road);
                    remover.remove();
                    ContextApplication.getInstance().reload();
                    RoadSensorFrame.getInstance().getContentPane().removeAll();
                    RoadSensorFrame.getInstance().setContentPane(RoadListPanel.reload().getTest());
                    RoadSensorFrame.getInstance().revalidate();
                }
                else JOptionPane.showMessageDialog(null,"Disattivare i sensori prima di proseguire!");
            }
        });

        carsPmt = new JLabel();
        carsPmt.setText("Limite Auto: " + road.getCarLimit() + "          Attualmente: " + road.getCars());
        GridBagConstraints gbc_txtParametro_2 = new GridBagConstraints();
        gbc_txtParametro_2.anchor = GridBagConstraints.WEST;
        gbc_txtParametro_2.gridx = 2;
        gbc_txtParametro_2.gridy = 2;
        cardPanel.add(carsPmt, gbc_txtParametro_2);

        strategylbl = new JLabel();
        strategylbl.setText(road.getStrategyTxt());
        GridBagConstraints gbc_strat = new GridBagConstraints();
        gbc_strat.anchor = GridBagConstraints.WEST;
        gbc_strat.gridx = 2;
        gbc_strat.gridy = 3;
        cardPanel.add(strategylbl, gbc_strat);
        cardPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    //Aggiornamento dei parametri della strada a livello visivo

    /**
     * Aggiorna i parametri della strada nei Panel
     */
    public void updatePmt(Road road){
        Color tmpColor;
        if(road.getStatus() == "RED")
            tmpColor = new Color(204,0,0);
        else if(road.getStatus() == "YELLOW")
            tmpColor = new Color(255,204,0);
        else
            tmpColor = new Color(0,102,0);
        pollutionPmt.setText("Limite Inquinamento: " + road.getPollutionLimit() + "          Attualmente: " + new DecimalFormat("##.##").format(road.getPollution()));
        tempPmt.setText("Limite Temperatura: " + road.getTempLimit() + "          Attualmente: " + new DecimalFormat("##.##").format(road.getTemp()));
        carsPmt.setText("Limite Auto: " + road.getCarLimit() + "          Attualmente: " + road.getCars());
        txtStatus.setText("Status: " + road.getStatus());
        txtStatus.setForeground(tmpColor);
        strategylbl.setText(road.getStrategyTxt());
    }

    public String getTxtNomestrada() {
        return road.getAddress();
    }
}
