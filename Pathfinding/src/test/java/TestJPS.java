
import Pathfinding.Graph;
import Pathfinding.JPS;
import Pathfinding.JPSNode;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author eebe
 */
public class TestJPS {

    Graph graph = new Graph(1, 1, 2, 5, 10);
    JPS jps = new JPS(graph);

    @Test
    public void estimatesRightDistanceToEnd() {
        JPSNode node = new JPSNode(1, 1);
        double distance = jps.estimateDistanceToEnd(node);
        Assert.assertEquals(distance, 4.414, 0.001);
    }

    @Test
    public void rightLengthOfShortestPathWithoutObstacles() {
        jps.shortestPath();
        assertEquals(4.414, jps.getRouteDistance(), 0.001);
    }

    // ...........
    // .S.........
    // @@@........
    // ...........
    // ..F........
    // ...........
    // ...........
    // ...........
    // ...........
    // ...........
    @Test
    public void rightLengthOfPathWithObstacles() {
        graph.addObstacle(1, 2);
        graph.addObstacle(2, 2);
        graph.addObstacle(0, 2);
        jps.shortestPath();
        assertEquals(5.828, jps.getRouteDistance(), 0.001);
    }

    @Test
    public void getRightNodeDistance() {
        JPSNode node = new JPSNode(2, 2);
        node.setParent(1, 1);
        assertEquals(1.414, jps.getNodeCost(node), 0.001);
    }

    @Test
    public void diagonalRouteIsDrawnRight() {
        Graph graph1 = new Graph(1, 1, 4, 4, 10);
        JPS jps1 = new JPS(graph1);
        jps1.shortestPath();
        jps1.drawRoute();
        assertEquals('?', graph1.getNode(2, 2));
    }

    @Test
    public void verticalRouteIsDrawnRight() {
        Graph graph1 = new Graph(1, 1, 1, 4, 10);
        JPS jps1 = new JPS(graph1);
        jps1.shortestPath();
        jps1.drawRoute();
        assertEquals('?', graph1.getNode(1, 2));
    }

    @Test
    public void horizontalRouteIsDrawnRight() {
        Graph graph1 = new Graph(1, 1, 4, 1, 10);
        JPS jps1 = new JPS(graph1);
        jps1.shortestPath();
        jps1.drawRoute();
        assertEquals('?', graph1.getNode(2, 1));
    }

    @Test
    public void horizontalNegativeRouteIsDrawnRight() {
        Graph graph1 = new Graph(4, 1, 1, 1, 10);
        JPS jps1 = new JPS(graph1);
        jps1.shortestPath();
        jps1.drawRoute();
        assertEquals('?', graph1.getNode(2, 1));
    }

    @Test
    public void diagonalNegativeRouteIsDrawnRight() {
        Graph graph1 = new Graph(4, 4, 1, 1, 10);
        JPS jps1 = new JPS(graph1);
        jps1.shortestPath();
        jps1.drawRoute();
        assertEquals('?', graph1.getNode(2, 2));
    }
}
