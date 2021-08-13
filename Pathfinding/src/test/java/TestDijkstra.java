
import static org.junit.Assert.*;
import org.junit.Test;
import Pathfinding.Dijkstra;
import Pathfinding.Graph;

/**
 *
 * @author eebe
 */
public class TestDijkstra {
    Graph graph = new Graph(1,1,2,5,11);
    Dijkstra dijkstra = new Dijkstra(graph);
    
    
    @Test
    public void createsRightSizedGraph() {
        dijkstra.createGraph();
        Graph graph = dijkstra.getGraph();
        assertEquals(10, graph.getMapSize());
    }
    @Test
    public void findsShortestPathWithoutObstacles() {
        dijkstra.findRoute();
        assertEquals(5, dijkstra.getRouteList().size());
    }
}
