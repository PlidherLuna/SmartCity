package Frames;

import ContextApplication.ContextApplication;
import Sensor.Sensor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Sensor.SensorResetThread;

/**Frame di partenza con gli switcher ON e OFF dei sensori, qui è possibile attivarli e disattivarli.
 */

public class SwitcherONOFF extends JFrame{

    private static SwitcherONOFF frame = null;
    private boolean status = false;
    private SensorResetThread resetter = null;

    private SwitcherONOFF(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        frame.getContentPane().setLayout(gridBagLayout);

        JLabel lblSensoriAttivi = new JLabel("Sensori: OFFLINE");
        GridBagConstraints gbc_lblSensoriAttivi = new GridBagConstraints();
        gbc_lblSensoriAttivi.insets = new Insets(0, 0, 20, 0);
        //gbc_lblSensoriAttivi.anchor = GridBagConstraints.CENTER;
        gbc_lblSensoriAttivi.gridx = 5;
        gbc_lblSensoriAttivi.gridy = 0;
        frame.getContentPane().add(lblSensoriAttivi, gbc_lblSensoriAttivi);

        JButton btnDeactive = new JButton("Disattiva");
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 10);
        gbc_btnNewButton_1.gridx = 3;
        gbc_btnNewButton_1.gridy = 2;
        frame.getContentPane().add(btnDeactive, gbc_btnNewButton_1);

        /**Listener del bottone per disattivare i sensori.
         */
        btnDeactive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(lblSensoriAttivi.getText() != "Sensori: OFFLINE") {
                    lblSensoriAttivi.setText("Sensori: OFFLINE");
                    status = false;
                    for(Sensor s : ContextApplication.getInstance().getSensors())
                        s.stopper();
                    resetter.stopper();
                }
                else
                    JOptionPane.showMessageDialog(null,"Sensori già OFFLINE!");
            }
        });

        JButton btnActive = new JButton("Attiva");
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.insets = new Insets(0, 10, 5, 0);
        gbc_btnNewButton_2.gridx = 7;
        gbc_btnNewButton_2.gridy = 2;
        frame.getContentPane().add(btnActive, gbc_btnNewButton_2);

        /**Listener del bottoner per attivare i sensori.
         */
        btnActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(lblSensoriAttivi.getText() != "Sensori: ONLINE") {
                    ContextApplication.getInstance().reload();
                    lblSensoriAttivi.setText("Sensori: ONLINE");
                    status = true;
                    for(Sensor s : ContextApplication.getInstance().getSensors())
                        s.starter();
                    /**Avvio il resetter che simula il passare del tempo diminuinendo l'inquinamento e decrementando i parametri.
                     */
                    resetter = new SensorResetThread();
                    resetter.run();
                }
                else
                    JOptionPane.showMessageDialog(null,"Sensori già ONLINE!");
            }
        });

        JButton btnRoad = new JButton("Gestione Strade");
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(20, 0, 5, 0);
        gbc_btnNewButton.gridx = 5;
        gbc_btnNewButton.gridy = 4;
        frame.getContentPane().add(btnRoad, gbc_btnNewButton);
        frame.setVisible(true);
        frame.setResizable(false);

        /**Listener del bottone per visualizzare le strade esistenti.
         */
        btnRoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RoadSensorFrame.getInstance();
            }
        });
    }

    public static SwitcherONOFF getInstance() {
        if(frame == null){
            frame = new SwitcherONOFF();
        }
        return frame;
    }

    /**Utilizzata per ritornare lo stato dei sensori,
     * se attivi non è possibile effetuare delle azioni come modifica, aggiunta e rimozione dei sensori
     */
    public boolean getSensorStatus(){
        return status;
    }
}
