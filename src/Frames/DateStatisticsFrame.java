package Frames;

import DateStats.DateStats;
import DateStats.DateStatsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

/**Frame che mostra le statistiche del periodo scelto
 */
public class DateStatisticsFrame {

    private static DateStatisticsFrame instance = null;

    public static DateStatisticsFrame getInstance(DateStats myDate){
        if(instance == null)
            instance = new DateStatisticsFrame(myDate);
        return instance;
    }

    public static DateStatisticsFrame reload(DateStats myDate){
        instance = new DateStatisticsFrame(myDate);
        return instance;
    }

    private DateStatisticsFrame(DateStats myDate){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblStartdate = new JLabel("Data Inizio: " + myDate.getStartDate());
        lblStartdate.setBounds(40, 12, 140, 14);
        frame.getContentPane().add(lblStartdate);

        JLabel lblEnddate = new JLabel("Data Fine: " + myDate.getEndDate());
        lblEnddate.setBounds(40, 38, 140, 14);
        frame.getContentPane().add(lblEnddate);

        JLabel lblMediaInquinamento = new JLabel("Media Inquinamento: " + new DecimalFormat("##.##").format(myDate.getAvrgPollution()));
        lblMediaInquinamento.setBounds(40, 80, 180, 14);
        frame.getContentPane().add(lblMediaInquinamento);

        JLabel label = new JLabel("Media Temperatura: " + new DecimalFormat("##.##").format(myDate.getAvrgTemp()));
        label.setBounds(40, 107, 200, 14);
        frame.getContentPane().add(label);

        JLabel label_1 = new JLabel("Media Auto: " + myDate.getAvrgCnt());
        label_1.setBounds(40, 133, 200, 14);
        frame.getContentPane().add(label_1);

        JLabel label_3 = new JLabel("Max Inquinamento: " + new DecimalFormat("##.##").format(myDate.getMaxPollution()));
        label_3.setBounds(228, 80, 200, 14);
        frame.getContentPane().add(label_3);

        JLabel label_4 = new JLabel("Max Temperatura: " + new DecimalFormat("##.##").format(myDate.getMaxTemp()));
        label_4.setBounds(228, 107, 200, 14);
        frame.getContentPane().add(label_4);

        JLabel label_5 = new JLabel("Max Auto: " + myDate.getMaxCnt());
        label_5.setBounds(228, 133, 200, 14);
        frame.getContentPane().add(label_5);

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
