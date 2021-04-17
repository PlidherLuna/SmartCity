package CardsForScrollers;

import ContextApplication.ContextApplication;
import Frames.RoadSensorFrame;
import Frames.SwitcherONOFF;
import Sensor.Sensor;
import Sensor.SensorRemover;
import Sensor.SensorRemoverImplement;
import SensorListPanel.RoadSensorList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

//Panel contenente le informazioni di un sensore con i suoi parametri ed il suo status attuale

/**Panel contenente le informazioni di un sensore.
 * Ne contiene i parametri e lo status, si può anche rimuovere.
 */
public class SensorCard extends JPanel {
    private Sensor s;
    private JLabel lblSensid;
    private JPanel cardPanel;
    private JLabel lblStatus;
    private JLabel lblAirparametro;
    private JLabel lblTempparametro;
    private JLabel lblCarsparametro;


    public SensorCard(Sensor s){
        this.s = s;

        cardPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{150, 58, 150, 0};
        gridBagLayout.rowHeights = new int[]{14, 14, 14, 14, 24, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        cardPanel.setLayout(gridBagLayout);

        lblSensid = new JLabel("SENSID: " + s.getSensID());
        GridBagConstraints gbc_lblSensid = new GridBagConstraints();
        gbc_lblSensid.anchor = GridBagConstraints.NORTH;
        gbc_lblSensid.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblSensid.insets = new Insets(5, 10, 5, 5);
        gbc_lblSensid.gridx = 0;
        gbc_lblSensid.gridy = 0;
        cardPanel.add(lblSensid, gbc_lblSensid);

        lblStatus = new JLabel("STATUS: " + s.getStatus());
        GridBagConstraints gbc_lblStatus = new GridBagConstraints();
        gbc_lblStatus.anchor = GridBagConstraints.NORTH;
        gbc_lblStatus.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblStatus.insets = new Insets(5, 0, 5, 0);
        gbc_lblStatus.gridx = 2;
        gbc_lblStatus.gridy = 0;
        cardPanel.add(lblStatus, gbc_lblStatus);

        JLabel lblAirlimit = new JLabel("Inquinamento: ");
        GridBagConstraints gbc_lblAirlimit = new GridBagConstraints();
        gbc_lblAirlimit.anchor = GridBagConstraints.NORTH;
        gbc_lblAirlimit.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblAirlimit.insets = new Insets(0, 10, 5, 5);
        gbc_lblAirlimit.gridx = 0;
        gbc_lblAirlimit.gridy = 2;
        cardPanel.add(lblAirlimit, gbc_lblAirlimit);

        lblAirparametro = new JLabel(new DecimalFormat("##.##").format(s.getPollutionP()));
        GridBagConstraints gbc_lblAirparametro = new GridBagConstraints();
        gbc_lblAirparametro.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblAirparametro.insets = new Insets(0, 0, 5, 0);
        gbc_lblAirparametro.gridx = 2;
        gbc_lblAirparametro.gridy = 2;
        cardPanel.add(lblAirparametro, gbc_lblAirparametro);

        JLabel lblTemplimit = new JLabel("Temperatura: ");
        GridBagConstraints gbc_lblTemplimit = new GridBagConstraints();
        gbc_lblTemplimit.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblTemplimit.insets = new Insets(0, 10, 5, 5);
        gbc_lblTemplimit.gridx = 0;
        gbc_lblTemplimit.gridy = 3;
        cardPanel.add(lblTemplimit, gbc_lblTemplimit);

        lblTempparametro = new JLabel("+(" + new DecimalFormat("##.##").format(s.getTemperatureP()) + ")°");
        GridBagConstraints gbc_lblTempparametro = new GridBagConstraints();
        gbc_lblTempparametro.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblTempparametro.insets = new Insets(0, 0, 5, 0);
        gbc_lblTempparametro.gridx = 2;
        gbc_lblTempparametro.gridy = 3;
        cardPanel.add(lblTempparametro, gbc_lblTempparametro);

        JLabel lblCarslimit = new JLabel("Auto: ");
        GridBagConstraints gbc_lblCarslimit = new GridBagConstraints();
        gbc_lblCarslimit.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblCarslimit.insets = new Insets(0, 10, 5, 5);
        gbc_lblCarslimit.gridx = 0;
        gbc_lblCarslimit.gridy = 4;
        cardPanel.add(lblCarslimit, gbc_lblCarslimit);

        lblCarsparametro = new JLabel(String.valueOf(s.getCarCounter()));
        GridBagConstraints gbc_lblCarsparametro = new GridBagConstraints();
        gbc_lblCarsparametro.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblCarsparametro.insets = new Insets(0, 0, 5, 0);
        gbc_lblCarsparametro.gridx = 2;
        gbc_lblCarsparametro.gridy = 4;
        cardPanel.add(lblCarsparametro, gbc_lblCarsparametro);

        JButton btnRemovesensor = new JButton("RemoveSensor");
        GridBagConstraints gbc_btnRemovesensor = new GridBagConstraints();
        gbc_btnRemovesensor.anchor = GridBagConstraints.NORTH;
        gbc_btnRemovesensor.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnRemovesensor.gridx = 2;
        gbc_btnRemovesensor.gridy = 5;
        cardPanel.add(btnRemovesensor, gbc_btnRemovesensor);
        cardPanel.setBorder(new LineBorder(Color.GRAY, 2, true));

        //Button per la rimozione del sensore dalla strada e dal sistema
        /**
         * Listener del button per la rimozione del sensore dalla strada e dal sistema
         */
        btnRemovesensor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Se i sensori sono attivi non e' possibile rimuovere il sensore
                if(!SwitcherONOFF.getInstance().getSensorStatus()) {
                    SensorRemover remover = new SensorRemoverImplement(s);
                    remover.remove();
                    ContextApplication.getInstance().reload();
                    RoadSensorFrame.getInstance().getContentPane().removeAll();
                    RoadSensorFrame.getInstance().setContentPane(RoadSensorList.reload(s.getRoad()).getTest());
                    RoadSensorFrame.getInstance().revalidate();
                }
                else JOptionPane.showMessageDialog(null,"Disattivare i sensori prima di proseguire!");
            }
        });
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    /**
     * Aggiorna i parametri dei Panel
     */
    public void updatePmt(Sensor sensor){
        this.s = sensor;
        lblAirparametro.setText(new DecimalFormat("##.##").format(s.getPollutionP()));
        lblTempparametro.setText(new DecimalFormat("##.##").format(s.getTemperatureP()));
        lblCarsparametro.setText(Integer.toString(s.getCarCounter()));
        lblStatus.setText("STATUS: " + s.getStatus());
    }

    public String getLblSensid() {
        return s.getSensID();
    }
}
