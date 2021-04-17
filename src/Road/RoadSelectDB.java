package Road;

import java.util.ArrayList;

/**Interfaccia per prelevare le strade dal DB
 */
public interface RoadSelectDB {
    public ArrayList<Road> selectAllRoads();
}
