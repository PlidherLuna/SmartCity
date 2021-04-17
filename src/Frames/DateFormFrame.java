package Frames;

import DateStats.DateStatsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**Form per la scelta delle date
 * E' possibile scegliere delle date con le statistiche, verranno mostrate le statistiche all'interno del periodo scelto.
 * Se non ci saranno statistiche non verranno mostrate.
 */
public class DateFormFrame {

    private static DateFormFrame instance = null;

    public static DateFormFrame getInstance() {
        if(instance == null)
            instance = new DateFormFrame();
        return instance;
    }

    public static DateFormFrame reload(){
        instance = new DateFormFrame();
        return instance;
    }

    private DateFormFrame(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblDatelable = new JLabel("Inserire le date inizio e fine (dd-mm-yyyy)");
        lblDatelable.setHorizontalAlignment(SwingConstants.CENTER);
        lblDatelable.setBounds(55, 44, 290, 14);
        frame.getContentPane().add(lblDatelable);

        JTextField txtStartdate = new JTextField();
        txtStartdate.setText("");
        txtStartdate.setBounds(55, 90, 114, 18);
        frame.getContentPane().add(txtStartdate);
        txtStartdate.setColumns(10);

        JTextField txtEnddate = new JTextField();
        txtEnddate.setText("");
        txtEnddate.setBounds(231, 90, 114, 18);
        frame.getContentPane().add(txtEnddate);
        txtEnddate.setColumns(10);

        JButton btnSend = new JButton("Send");
        btnSend.setBounds(160, 139, 98, 24);
        frame.getContentPane().add(btnSend);

        /**
         * Listener del button per inviare le date al DB
         */
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!txtStartdate.getText().equals("")  && !txtEnddate.getText().equals("")) {
                    DateStatsInterface myDate = new DateStats.DateStatsIntImplement(txtStartdate.getText(), txtEnddate.getText());
                    if(myDate.getParams().getAvrgPollution() > 0) {
                        DateStatisticsFrame.reload(myDate.getParams());
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    }
                }
                else
                    JOptionPane.showMessageDialog(null,"Inserire delle date");
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
                instance = null;
            }
        });
    }
}
