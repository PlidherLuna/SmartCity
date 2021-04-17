package Strategy;

import Database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**Classe di accesso per l'aggiunta di un report nel DB
 */
public class InsertReportDBImplement implements InsertReportDB{
    /**
     * Aggiunge il report con la data e l'ora della segnalazione.
     */
    @Override
    public void addReport(String plate, String address) {
        String sqlInsert = "INSERT INTO Report(reportDate,carPlate,roadAddress) VALUES (?,?,?)";
        try (PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlInsert)){
            LocalDateTime dateTime = LocalDateTime.now(); // Gets the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ps.setString(1,dateTime.format(formatter));
            ps.setString(2,plate);
            ps.setString(3,address);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
