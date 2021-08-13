package Pathfinding;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author eebe
 */
public class JPS {
    int n;
    JPSNode[][] nodes;
    ArrayList<JPSNode> closedList = new ArrayList<>();
    PriorityQueue<JPSNode> openList = new PriorityQueue<>();
    JPSNode startNode;
    JPSNode endNode;
    public ArrayList<JPSNode> route = new ArrayList<>();
    Graph graph;

    /**
     *
     * @param graph map where starting node is marked S and goal node is marked
     * F. Obstacles are marked @.
     */
    public JPS(Graph graph) {
        this.n = graph.getMapSize() + 1;
        this.graph = graph;
        this.startNode = new JPSNode(graph.startX, graph.startY);
        this.endNode = new JPSNode(graph.endX, graph.endY);
        nodes = new JPSNode[n][n];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                JPSNode node = new JPSNode(i, j);
                nodes[j][i] = node;
            }
        }

    }

    /**
     * Finds the shortest route from startpoint to endpoint using A* (will be upgraded to JPS) algorithm.
     */
    public void shortestPath() {
        JPSNode start = new JPSNode(startNode.nodeX, startNode.nodeY);
        double distanceToEnd = estimateDistanceToEnd(start);
        start.updateGHF(0, distanceToEnd);
        nodes[startNode.nodeX][startNode.nodeY] = start;
        openList.add(start);
        JPSNode previousNode = startNode;
        while (!openList.isEmpty()) {
            JPSNode current = openList.poll();
            closedList.add(current);
            if (current.nodeX == endNode.nodeX && current.nodeY == endNode.nodeY) {
                endNode.setParent(previousNode.nodeX, previousNode.nodeY);
                break;
            }
            int[][] neighbours = graph.getNeighbours(current.nodeX, current.nodeY);
            List<JPSNode> successorNodes = new ArrayList<>();
            double currentDistance = current.g;
            for (int i = 0; i < neighbours.length; i++) {
                int x = neighbours[i][0];
                int y = neighbours[i][1];
                if (x < 0) {
                    continue;
                }
                double distance;
                if (i < 4) {
                    distance = 1;
                } else {
                    distance = sqrt(2);
                }
                JPSNode successorNode = new JPSNode(x, y);
                successorNode.updateGHF(currentDistance + distance, estimateDistanceToEnd(successorNode));
                successorNode.setParent(current.nodeX, current.nodeY);
                successorNodes.add(successorNode);
            }

            for (int i = 0; i < successorNodes.size(); i++) {
                JPSNode cur = successorNodes.get(i);
                if (cur.f < nodes[cur.nodeX][cur.nodeY].f) {
                    nodes[cur.nodeX][cur.nodeY] = cur;
                    openList.add(cur);
                } else {
                    openList.add(cur);
                }
            }
            closedList.add(current);
            previousNode = current;
        }
        findPath(endNode);
    }

    public void findPath(JPSNode endNode) {
        JPSNode nextNode = nodes[endNode.nodeX][endNode.nodeY];
        route.add(endNode);
        while (true) {
            nextNode = nodes[nextNode.parentX][nextNode.parentY];
            route.add(nextNode);
            if (nextNode.nodeX == startNode.nodeX && nextNode.nodeY == startNode.nodeY) {
                Collections.reverse(route);
                break;
            }
            graph.addRoutePoint(nextNode.nodeX, nextNode.nodeY);
            
        }

    }

    public List<JPSNode> horizontalSearch(JPSNode position, int horizontalDirection, double distance) {
        ArrayList<JPSNode> jumpPoints = new ArrayList<>();
        int x0 = position.nodeX;
        int y0 = position.nodeY;
        while (true) {
            int x1 = x0 + horizontalDirection;
            if (x1 > this.n || x1 < 0) {
                return jumpPoints;
            }
            if (graph.getNode(x1, y0) == '@') {
                return jumpPoints;
            }
            if (endNode.nodeX == x1 && endNode.nodeY == y0) {
                endNode.updateGHF(position.g + 1, 0);
                jumpPoints.add(endNode);
                return jumpPoints;

            }
            int x2 = x1 + horizontalDirection;

            if (graph.getNode(x1, (y0 - 1)) == '@' && graph.getNode(x2, (y0 - 1)) != '@') {
                JPSNode node = new JPSNode(x1, y0);
                node.updateGHF(distance, estimateDistanceToEnd(node));
                jumpPoints.add(node);
            }

        }
    }

    /**
     * Estimates the shortest distance to end node.
     * @param node The node for which the estimation is made.
     * @return Double distance to end node.
     */
    public double estimateDistanceToEnd(JPSNode node) {
        int x = Math.abs(node.nodeX - endNode.nodeX);
        int y = Math.abs(node.nodeY - endNode.nodeY);
        double distance = Math.min(x, y) * sqrt(2) + Math.abs(y - x);
        return distance;
    }
}
