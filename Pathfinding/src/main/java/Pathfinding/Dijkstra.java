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
    ArrayList<Node> route = new ArrayList<>();

/**
 * Creates the data structures needed. 
 * @param startX Start point X-axis.
 * @param startY Start point Y-axis.
 * @param endX End point X-axis.
 * @param endY End point Y-axis.
 * @param mapSize Integer with map height. The map is as wide as high.
 */
    public Dijkstra(int startX, int startY, int endX, int endY, int mapSize) {
        startNode = new Node(startX, startY, 0);
        endNode = new Node(endX, endY, Integer.MAX_VALUE);
        n = mapSize + 1;
        distanceGraph = new double[n][n];
        nodesVisited = new double[n][n];
    }

    private void createGraph() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
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

}
