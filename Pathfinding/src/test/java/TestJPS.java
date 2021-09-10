
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
    Graph graph = new Graph(1,1,2,5,10);
    JPS jps = new JPS(graph);
    
    @Test
    public void estimatesRightDistanceToEnd() {
        JPSNode node = new JPSNode(1,1);
        double distance = jps.estimateDistanceToEnd(node);
        Assert.assertEquals(distance, 4.414, 0.001);
    }
    
    @Test
    public void rightLengthOfShortestPath() {
        jps.shortestPath();
        assertEquals(jps.route.size(), 3);
    }
}
