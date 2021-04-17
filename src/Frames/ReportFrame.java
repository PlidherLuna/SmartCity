package Frames;

import CardsForScrollers.SensorCard;
import ContextApplication.ContextApplication;
import Sensor.Sensor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**Frame che mostra le targhe delle auto segnalate
 * e la via in cui hanno compiuto la violazione.
 */
public class ReportFrame {

    private JFrame frame;
    private JScrollPane scrollPane;
    private JPanel contentCards;
    private static  ReportFrame instance = null;

    public static ReportFrame getInstance() {
        if(instance == null)
            instance = new ReportFrame();
        return instance;
    }

    //Classe che avvia un frame per monitorare le auto che non hanno rispettato le strategi
    private ReportFrame(){

        frame = new JFrame();
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        contentCards = new JPanel();
        contentCards.setLayout(new BoxLayout(contentCards, BoxLayout.PAGE_AXIS));
        scrollPane = new JScrollPane(contentCards);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(25, 22, 476, 190);
        frame.getContentPane().add(scrollPane);

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
                instance = null;
            }
        });
    }

    //Aggiunta della segnalazione al frame

    /**
     * Aggiunta della segnalazione al frame
     */
    public void addReport(String plate, String roadAddress){
        JLabel label = new JLabel();
        label.setText("STRADA: " + roadAddress + " TARGA: " + plate);
        label.setBounds(22, 224, 466, 14);
        contentCards.add(label);
        frame.revalidate();
    }
}
