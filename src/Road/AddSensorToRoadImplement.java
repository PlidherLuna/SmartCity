package Road;

import Database.Database;
import Sensor.Sensor;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Classe di accesso per aggiungere un sensore di una strada nel DB

/** Classe di accesso al DB per aggiungere un sensore di una strada nel sistema
 */
public class AddSensorToRoadImplement implements AddSensorToRoad{
    private Road road;

    public AddSensorToRoadImplement(Road road){
        this.road = road;
    }

    /**
     * Inserimento del sensore nel DB
     */
    @Override
    public void insertSensor() {
        //Database.getInstance().insertSensor(road);
        int maxID = getSensID();
        maxID++;
        String sensID = "S" + maxID;

        String sqlInsert = "INSERT INTO Sensor(sensorID, address, pollution, temperature, carCounter) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlInsert)) {
            ps.setString(1,sensID);
            ps.setString(2,road.getAddress());
            ps.setFloat(3,0);
            ps.setFloat(4,0);
            ps.setInt(5,0);
            int num = ps.executeUpdate();
            if(num > 0) {
                Sensor s = new Sensor();
                s.setID(sensID);
                s.setRoad(road);
                road.addSensor(s);
            }
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Errore!!");
        }
    }

    /**
     * Seleziona il massimo ID nel DB così da creare un nuovo sensore con ID univoco
     */
    public int getSensID(){
        String sqlQuery = "SELECT Max(CAST(substr(sensorID,2) AS INTEGER)) FROM Sensor;";

        try(Statement s = Database.getInstance().getConnection().createStatement()){
            ResultSet rs = s.executeQuery(sqlQuery);
            while(rs.next()){
                int maxID = rs.getInt(1);
                if(!(maxID > 0))
                    maxID = 0;
                rs.close();
                return maxID;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
