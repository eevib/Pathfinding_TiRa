/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author eebe
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int mapSize = 10;
        Graph graph = new Graph(2, 2, 5, 10, mapSize + 1);
        graph.addObstacle(3, 1);
        graph.addObstacle(3, 2);
        graph.addObstacle(3, 2);
        graph.addObstacle(3, 4);
        graph.addObstacle(4, 6);
        graph.addObstacle(2, 6);
        graph.addObstacle(3, 6);
        graph.addObstacle(4, 5);
        graph.addObstacle(5, 5);
        graph.addObstacle(5, 7);
        graph.addObstacle(6, 7);
        graph.addObstacle(7, 7);
        graph.addObstacle(8, 7);
        graph.addObstacle(4, 7);
        System.out.println(graph);
//
//        Dijkstra dijkstra = new Dijkstra(graph);
//        dijkstra.findRoute();
//        dijkstra.route.forEach(node -> System.out.println(node));
//        System.out.println(graph);
//        System.out.println("");
        //  Graph graphJPS = new Graph(2, 2, 5, 10, mapSize + 1);
        JPS jps = new JPS(graph);
        jps.shortestPath();
        System.out.println("");
        jps.printDistanceGraph();
        System.out.println("");
        System.out.println(graph);
        jps.route.forEach(n -> System.out.println(n));

    }

}
