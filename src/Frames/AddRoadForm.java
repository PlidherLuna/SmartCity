package Frames;

import ContextApplication.ContextApplication;
import Road.Road;
import Tools.JTextFieldLimit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Road.RoadInsertDB;
import Road.RoadInsertDBImplement;
import RoadListPanel.RoadListPanel;

//Frame che contiene un form per poter aggiungere una strada al sistema

/** Form aggiunta di una strada
 * E' possibile aggiungere una strada al sistema fornendone i dati.
 */
public class AddRoadForm {

    private static JFrame frame = null;

    private AddRoadForm(){
        frame = new JFrame();
        frame.setBounds(100, 100, 471, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblAddress = new JLabel("Indirizzo");
        lblAddress.setBounds(36, 12, 146, 14);
        frame.getContentPane().add(lblAddress);

        JTextField addressTxt = new JTextField();
        addressTxt.setDocument(new JTextFieldLimit(40));
        addressTxt.setBounds(200, 10, 238, 18);
        frame.getContentPane().add(addressTxt);
        addressTxt.setColumns(10);

        JLabel lblPollution = new JLabel("Limite inquinamento");
        lblPollution.setBounds(36, 38, 146, 14);
        frame.getContentPane().add(lblPollution);

        JTextField pollutionTxt = new JTextField();
        pollutionTxt.setBounds(200, 36, 114, 18);
        frame.getContentPane().add(pollutionTxt);
        pollutionTxt.setColumns(10);

        JLabel lblTemp = new JLabel("Limite temperatura");
        lblTemp.setBounds(36, 64, 146, 14);
        frame.getContentPane().add(lblTemp);

        JTextField tempTxt = new JTextField();
        tempTxt.setBounds(200, 62, 114, 18);
        frame.getContentPane().add(tempTxt);
        tempTxt.setColumns(10);

        JLabel lblCars = new JLabel("Limite auto");
        lblCars.setBounds(36, 90, 146, 14);
        frame.getContentPane().add(lblCars);

        JTextField carsTxt = new JTextField();
        carsTxt.setBounds(200, 88, 114, 18);
        frame.getContentPane().add(carsTxt);
        carsTxt.setColumns(10);

        JLabel lblLunghezza = new JLabel("Lunghezza in mt.");
        lblLunghezza.setBounds(36, 116, 146, 14);
        frame.getContentPane().add(lblLunghezza);

        JTextField lengthTxt = new JTextField();
        lengthTxt.setBounds(200, 114, 114, 18);
        frame.getContentPane().add(lengthTxt);
        lengthTxt.setColumns(10);

        JButton btnAdd = new JButton("Aggiungi");
        btnAdd.setBounds(200, 170, 98, 24);
        frame.getContentPane().add(btnAdd);

        /**
         * Listener del button per inviare le informazioni al DB
         */
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //I campi devono essere compilati
                if(addressTxt.getText().length() < 1 || tempTxt.getText().length() < 1 || carsTxt.getText().length() < 1 || pollutionTxt.getText().length() < 1)
                    JOptionPane.showMessageDialog(null,"Compilare tutti i campi");
                else if(addressTxt.getText().length() < 6)
                    JOptionPane.showMessageDialog(null,"Inserire un indirizzo di almeno 6 caratteri");
                else if(Float.parseFloat(tempTxt.getText()) <= 0)
                    JOptionPane.showMessageDialog(null,"Inserire una temperatura > 0");
                else if(Integer.parseInt(carsTxt.getText()) <= 0)
                    JOptionPane.showMessageDialog(null,"Inserire un limite di auto > 0");
                else if(Float.parseFloat(pollutionTxt.getText()) <= 0)
                    JOptionPane.showMessageDialog(null,"Inserire un limite di inquinamento > 0");
                else if(Float.parseFloat(lengthTxt.getText()) < 10)
                    JOptionPane.showMessageDialog(null,"Inserire una lunghezza > 10");
                else {
                    //Se i sensori sono attivi non sarà possibile aggiungere la strada
                    if (!SwitcherONOFF.getInstance().getSensorStatus()) {

                        String p1 = addressTxt.getText();
                        float p2 = Float.parseFloat(lengthTxt.getText());
                        float p3 = Float.parseFloat(pollutionTxt.getText());
                        float p4 = Float.parseFloat(tempTxt.getText());
                        int p5 = Integer.parseInt(carsTxt.getText());
                        Road road = new Road(p1, p2, p3, p4, p5);

                        //Interfaccia per l'aggiunta della strada nel DB
                        RoadInsertDB roadDB = new RoadInsertDBImplement(road);
                        roadDB.insertRoad(road);
                        ContextApplication.getInstance().reload();

                        RoadSensorFrame.getInstance().getContentPane().removeAll();
                        RoadSensorFrame.getInstance().setContentPane(RoadListPanel.reload().getTest());
                        RoadSensorFrame.getInstance().revalidate();

                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    } else JOptionPane.showMessageDialog(null, "Disattivare i sensori prima di proseguire!");
                }
            }
        });

        //Rileva grandezza schermo
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        //Calcola e posiziona il frame al centro dello schermo
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                frame = null;
            }
        });
    }

    public static JFrame getFrame() {
        if(frame == null) {
            return new AddRoadForm().frame;
        }
        else
            return frame;
    }
}
