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
/**
 * Dijkstra shortest path algorithm
 */
public class Dijkstra {

    int n;
    double[][] distanceGraph;
    double[][] nodesVisited;
    PriorityQueue<Node> heap = new PriorityQueue<>();
    Node startNode;
    Node endNode;
    public ArrayList<Node> route = new ArrayList<>();
    Graph graph;
    Double routeDistance;

    /**
     * Creates data structures needed.
     *
     * @param graph map where starting node is marked S and goal node is marked
     * F. Obstacles are marked @.
     */
    public Dijkstra(Graph graph) {
        this.graph = graph;
        startNode = new Node(graph.startX, graph.startY, 0);
        endNode = new Node(graph.endX, graph.endY, Integer.MAX_VALUE);
        n = graph.getMapSize();
        distanceGraph = new double[n][n];
        nodesVisited = new double[n][n];
        this.routeDistance = Double.POSITIVE_INFINITY;
    }

    public void createGraph() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char node = graph.getNode(i, j);
                distanceGraph[j][i] = Integer.MAX_VALUE;

            }
        }
    }

    /**
     * Goes trough graph and saves distances from start node to end node.
     */
    public void shortestPath() {
        createGraph();
        heap.add(new Node(startNode.nodeX, startNode.nodeY, 0));
        distanceGraph[startNode.nodeX][startNode.nodeY] = 0;
        while (heap.size() > 0) {
            Node currentNode = heap.poll();
            if (currentNode.nodeX == endNode.nodeX && currentNode.nodeY == endNode.nodeY) {
                break;
            }
            if (nodesVisited[currentNode.nodeX][currentNode.nodeY] == -1) {
                continue;
            }
            nodesVisited[currentNode.nodeX][currentNode.nodeY] = -1;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (currentNode.nodeX + j < 0 || currentNode.nodeX + j >= n || currentNode.nodeY + i < 0 || currentNode.nodeY + i >= n) {
                        continue;
                    } else {
                        double oldDistance = distanceGraph[currentNode.nodeX + j][currentNode.nodeY + i];
                        double newDistance;
                        // if there is a obstacle, the distance is put to infinity.
                        if (!graph.passable(currentNode.nodeX + j, currentNode.nodeY + i)) {
                            newDistance = Double.POSITIVE_INFINITY;
                            distanceGraph[currentNode.nodeX + j][currentNode.nodeY + i] = newDistance;
                            continue;
                        }
                        if (i == 0 || j == 0) {
                            newDistance = currentNode.distance + 1;
                        } else {
                            newDistance = currentNode.distance + sqrt(2);
                        }
                        if (newDistance < oldDistance) {
                            distanceGraph[currentNode.nodeX + j][currentNode.nodeY + i] = newDistance;
                            heap.add(new Node(currentNode.nodeX + j, currentNode.nodeY + i, newDistance));

                        }

                    }
                }

            }
        }
    }

    /**
     * Finds the nodes in the route and saves them to the List route.
     */
    public void findRoute() {
        shortestPath();
        Node currentNode = new Node(endNode.nodeX, endNode.nodeY, distanceGraph[endNode.nodeX][endNode.nodeY]);
        route.add(currentNode);
        this.routeDistance = currentNode.distance;
        Node smallestNode = new Node(endNode.nodeX, endNode.nodeY, distanceGraph[endNode.nodeX][endNode.nodeY]);
        while (true) {
            for (int i = -1; i <= 1; i++) {
                if (currentNode.nodeY + i < 0 || currentNode.nodeY + i >= n) {
                    continue;
                } else {
                    for (int j = -1; j <= 1; j++) {
                        if (currentNode.nodeX + j < 0 || currentNode.nodeX + j >= n) {
                            continue;
                        } else {
                            double newNodeDistance = distanceGraph[(currentNode.nodeX + j)][(currentNode.nodeY + i)];
                            Node newNode = new Node((currentNode.nodeX + j), (currentNode.nodeY + i), newNodeDistance);
                            if (newNode.distance < smallestNode.distance) {
                                smallestNode = newNode;
                            }
                        }
                    }
                }

            }
            if (currentNode.nodeX == startNode.nodeX && currentNode.nodeY == startNode.nodeY) {
                break;
            }
            route.add(smallestNode);
            currentNode = smallestNode;
        }
        Collections.reverse(route);
    }

    public void drawRoute() {
        Collections.reverse(route);
        for (Node n : route) {
            graph.addRoutePoint(n.nodeX, n.nodeY);
        }
    }

    public void printGraphs() {
        System.out.println("DistanceGraph");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(" " + distanceGraph[j][i]);
            }
            System.out.println("");
        }
        System.out.println("VisitedGraph");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(" " + nodesVisited[j][i]);
            }
            System.out.println("");
        }
    }

    public Graph getGraph() {
        return this.graph;
    }

    public List getRouteList() {
        return route;
    }

    public double getRouteDistance() {
        return this.routeDistance;
    }

}
