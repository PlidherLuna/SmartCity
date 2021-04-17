package Road;

import Database.Database;
import Sensor.Sensor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**Classe di accesso per prelevare le strade ed i sensori dal DB
 */
public class RoadSelectDBImplement implements RoadSelectDB{
    /**Preleva le strade dal DB
     */
    @Override
    public ArrayList<Road> selectAllRoads() {
        String sqlQuery = "SELECT * FROM Road";

        try (Statement statement = Database.getInstance().getConnection().createStatement()){
            ResultSet rs = statement.executeQuery(sqlQuery);
            ArrayList<Road> roads = new ArrayList<Road>();

            while(rs.next()){
                String address = rs.getString(1);
                float roadLength = rs.getFloat(2);
                float pollutionLimit = rs.getFloat(3);
                float tempLimit = rs.getFloat(4);
                int carLimit = rs.getInt(5);

                Road queryRoad = new Road(address, roadLength, pollutionLimit, tempLimit, carLimit);

                //Prelievo dei sensori della strada appena rilevata
                ArrayList<Sensor> sensors = new ArrayList<Sensor>();
                sensors = selectRoadSensors(queryRoad);
                if(sensors != null) {
                    for (Sensor s : sensors) {
                        s.setRoad(queryRoad);
                        queryRoad.addSensor(s);
                    }
                }
                roads.add(queryRoad);
            }

            rs.close();
            return roads;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**Preleva i sensori delle strade dal DB
     * @param road
     * @return
     */
    public ArrayList<Sensor> selectRoadSensors(Road road) {
        String sqlQuery = "SELECT * FROM Sensor WHERE address = ?";

        try(PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlQuery)){
            ps.setString(1,road.getAddress());
            ResultSet rs = ps.executeQuery();
            ArrayList<Sensor> sensors = new ArrayList<Sensor>();
            while(rs.next()){
                String sensorID = rs.getString(1);
                float sensorP = rs.getFloat(3);
                float sensorT = rs.getFloat(4);
                int sensorC = rs.getInt(5);
                Sensor querySensor = new Sensor(sensorID,sensorP,sensorT,sensorC,road);
                sensors.add(querySensor);
            }
            rs.close();
            return sensors;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
