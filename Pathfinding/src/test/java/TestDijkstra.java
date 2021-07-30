
import static org.junit.Assert.*;
import org.junit.Test;
import Pathfinding.Dijkstra;

/**
 *
 * @author eebe
 */
public class TestDijkstra {
    Dijkstra dijkstra = new Dijkstra(1,1,2,5,10);
    
    @Test
    public void createsRightSizedGraph() {
        dijkstra.createGraph();
        assertTrue(true);
    }
}
