package Strategy;

import Database.Database;
import Frames.ReportFrame;

import javax.swing.*;

/**Classe di segnalazione
 * Si occupa di segnalare le auto che violano una strategia
 */
public class PoliceReporter {
    public PoliceReporter(){}

    //Crea la segnalazione

    /**
     * Crea la segnalazione tramite targa ed indirizzo della strada e chiama l'opportuno metodo per l'inserimento nel DB
     */
    public void report(String plate, String roadAddress){
        ReportFrame.getInstance().addReport(plate,roadAddress);
        InsertReportDB reporter = new InsertReportDBImplement();
        reporter.addReport(plate,roadAddress);
    }
}
