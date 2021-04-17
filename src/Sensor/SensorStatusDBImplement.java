package Sensor;

import Database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Classe per l'aggiornamento di un sensore nel DB

/**Classe di accesso per l'aggiornamento di un sensore nel DB
 */
public class SensorStatusDBImplement implements SensorStatusDB{
    private Sensor s;

    public SensorStatusDBImplement(Sensor sensor){
        s = sensor;
    }

    /**
     * Inserisce nella tabella Status un sensore con tutti i suoi parametri e la data (con il tempo) del suo stato.
     */
    @Override
    public void update() {
        sensorUpdate(s);
        String sqlInsert = "INSERT INTO Status(dateUpdate,sensID,pollution,temperature,carCounter) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlInsert)) {
            LocalDateTime dateTime = LocalDateTime.now(); // Gets the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ps.setString(1, dateTime.format(formatter));
            ps.setString(2, s.getSensID());
            ps.setFloat(3, s.getPollutionP());
            ps.setFloat(4, s.getTemperatureP());
            ps.setInt(5, s.getCarCounter());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Aggiornamento di stato di un sensore

    /**
     * Aggiorna lo stato di un sensore, quindi ne cambia i parametri fissi nel DB
     */
    public void sensorUpdate(Sensor s){
        String sqlUpdate = "UPDATE Sensor SET pollution = ?, temperature = ?, carCounter = ? WHERE sensorID = ?";
        try(PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlUpdate)){
            ps.setFloat(1,s.getPollutionP());
            ps.setFloat(2,s.getTemperatureP());
            ps.setInt(3,s.getCarCounter());
            ps.setString(4,s.getSensID());
            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
