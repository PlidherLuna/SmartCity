package Sensor;

import Database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//Classe per la rimozione di un sensore dal sistema

/**Classe di accesso per la rimozione di un sensore dal DB
 */
public class SensorRemoverImplement implements SensorRemover{

    private Sensor sensor;

    public SensorRemoverImplement(Sensor s){
        sensor = s;
    }

    /**
     * Rimuove un sensore dal DB e dalla strada di cui ne fa parte
     */
    @Override
    public void remove() {
        String sqlRemove = "DELETE FROM Sensor WHERE sensorID = ?";
        try(PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlRemove)){
            ps.setString(1,sensor.getSensID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sensor.removeFromRoad();
    }
}
