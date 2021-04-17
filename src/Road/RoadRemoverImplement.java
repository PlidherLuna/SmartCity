package Road;

import Database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//Classe per la rimozione di una strada dal DB

/**Classe di accesso per la rimozione di una strada dal DB
 */
public class RoadRemoverImplement implements RoadRemover{

    private Road road;

    public RoadRemoverImplement(Road r){
        road = r;
    }

    /**
     * Rimuove la strada dal DB
     */
    @Override
    public void remove() {
        String sqlRemove = "DELETE FROM Road WHERE address = ?";
        try(PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(sqlRemove)){
            ps.setString(1,road.getAddress());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
