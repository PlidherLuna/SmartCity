package Frames;

import RoadListPanel.RoadListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**In questo Frame è possibile visualizzare le strade presenti nel sistema.
 * E' possibile gestirle tramite gli appositi pulsanti oppure visualizzare le statistiche,
 * aggiungere una nuova strada o rimuoverla.
 */

public class RoadSensorFrame extends JFrame {

    private static JFrame mainFrame = null;

    private RoadSensorFrame() {
        mainFrame = new JFrame("SmartCity");
        mainFrame.setContentPane(RoadListPanel.reload().getTest());
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(800, 500);

        //Rileva grandezza schermo
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        //Calcola e posiziona il frame al centro dello schermo
        int x = (screenSize.width - mainFrame.getWidth()) / 2;
        int y = (screenSize.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(x, y);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                mainFrame = null;
            }
        });
    }
    public static JFrame getInstance(){
        if (mainFrame == null){
            return new RoadSensorFrame().mainFrame;
        }
        else
            return mainFrame;
    }
}
