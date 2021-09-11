
import Pathfinding.Graph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author eebe
 */
public class TestGraph {

    Graph graph = new Graph(10);

    @Test
    public void addStartPoint() {
        graph.putStartPoint(2, 2);
        assertEquals(2, graph.getStartX());
        assertEquals(2, graph.getStartY());

    }

    @Test
    public void noStartPointIsAddedOnObstacle() {
        graph.addObstacle(3, 3);
        graph.putStartPoint(3, 3);
        assertEquals('@', graph.getNode(3, 3));
    }

    @Test
    public void testPutEndPoint() {
        graph.putEndPoint(5, 5);
        assertEquals('F', graph.getNode(5, 5));
        assertTrue(graph.putEndPoint(5, 5));
    }

    @Test
    public void routePointIsAddedCorrectly() {
        graph.addRoutePoint(4, 5);
        assertEquals('+', graph.getNode(4, 5));
    }

    @Test
    public void JPSroutePointIsAddedCorrectly() {
        graph.addJPSRoutePoint(1, 1);
        assertEquals('?', graph.getNode(1, 1));
    }
    
    @Test
    public void rightPointIsPut() {
        graph.putPoint(2, 3, '&');
        assertEquals('&', graph.getNode(2, 3));
    }
    @Test
    public void jumpPointIsAddedCorrectly() {
        graph.addJumpPoint(7, 7);
        assertEquals('*', graph.getNode(7, 7));
    }
}
