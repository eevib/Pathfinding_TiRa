/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import java.util.Collections;

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
        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.findRoute();
        dijkstra.route.forEach(node -> System.out.println(node));
        System.out.println("");

        Graph graphJPS = new Graph(2, 2, 5, 10, mapSize + 1);
        JPS jps = new JPS(graphJPS);
        jps.shortestPath();
        System.out.println(graphJPS);
        jps.route.forEach(node -> System.out.println(node));

    }

}
