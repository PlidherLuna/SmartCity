package Road;

import Database.Database;
import Road.Road;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Classe per l'inserimento di una strada nel DB

/**Classe di accesso per l'inserimento di una strada nel DB
 */
public class RoadInsertDBImplement implements RoadInsertDB {
    Road road;

    public RoadInsertDBImplement(Road road){
        this.road = road;
    }

    /**
     * Inserisce la strada passata nel DB
     */
    @Override
    public void insertRoad(Road road) {
        String sqlInsert = "INSERT INTO Road(address,roadLength, pollutionLimit, tempLimit, carLimit) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlInsert)) {
            ps.setString(1,road.getAddress());
            ps.setFloat(2,road.getLength());
            ps.setFloat(3,road.getPollutionLimit());
            ps.setFloat(4,road.getTempLimit());
            ps.setInt(5,road.getCarLimit());
            int num = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Errore!!");
        }
    }
}
