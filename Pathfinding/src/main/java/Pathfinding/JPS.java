package Pathfinding;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author eebe
 */
public class JPS {
    int n;
    JPSNode[][] nodes;
    PriorityQueue<JPSNode> closedList = new PriorityQueue<>();
    ArrayList<JPSNode> openList = new ArrayList<>();
    JPSNode startNode;
    JPSNode endNode;
    ArrayList<Node> route = new ArrayList<>();
    Graph graph;

    
   public JPS(int startX, int startY, int endX, int endY, int mapSize, Graph graph) {
       this.n = mapSize + 1;
       this.startNode = new JPSNode(startX, startY);
       this.endNode = new JPSNode(endX, endY);
       this.graph = graph;
       
   }
   
   public void shortestPath() {
       JPSNode start = new JPSNode(startNode.nodeX, startNode.nodeY);
       int distanceToEnd = estimateDistanceToEnd(start);
       start.updateGHFParent(0, distanceToEnd, null);
       nodes[startNode.nodeX][startNode.nodeY] = start;  
       
   }
   
   public int estimateDistanceToEnd(JPSNode node) {
       int x = Math.abs(node.nodeX - endNode.nodeX);
       int y = Math.abs(node.nodeY - endNode.nodeY);
       return x + y;
   }
}
