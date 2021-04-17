package DateStats;

import Database.Database;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Classe che fa da tramite per le richieste al DB per le statistiche di un periodo fissato
 */
public class DateStatsIntImplement implements DateStatsInterface{

    private DateStats myDate;

    public DateStatsIntImplement(String st, String st2){
        myDate = new DateStats(st,st2);
    }

    @Override
    public DateStats getParams() {
        String sqlQuery = "SELECT * FROM Status WHERE SUBSTR(dateUpdate,0,11) >= ? AND SUBSTR(dateUpdate,0,11) <= ?";
        try (PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlQuery)) {
            ps.setString(1, myDate.getStartDate());
            ps.setString(2, myDate.getEndDate());
            ResultSet rs = ps.executeQuery();
            float avrgPollution = 0F;
            float avrgTemp = 0F;
            int avrgCar = 0;
            int totRecords = 0;
            while (rs.next()) {
                avrgPollution += rs.getFloat("pollution");
                avrgTemp += rs.getFloat("temperature");
                avrgCar += rs.getInt("carCounter");
                myDate.setMaxPollution(rs.getFloat("pollution"));
                myDate.setMaxTemp(rs.getFloat("temperature"));
                myDate.setMaxCnt(rs.getInt("carCounter"));
                totRecords++;
            }
            myDate.setAvrgPollution(avrgPollution / totRecords);
            myDate.setAvrgTemp(avrgTemp / totRecords);
            myDate.setAvrgCnt(avrgCar / totRecords);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ArithmeticException e){
            JOptionPane.showMessageDialog(null,"Nessun risultato.");
        }
        return myDate;
    }
}
