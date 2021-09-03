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
        Graph JPSgraph = new Graph(2, 2, 5, 10, mapSize + 1);

            graph.addObstacle(3, 2);
        graph.addObstacle(3, 3);
        graph.addObstacle(3, 4);
        graph.addObstacle(4, 6);
        graph.addObstacle(1, 7);
        graph.addObstacle(3, 7);
        graph.addObstacle(2, 7);
              graph.addObstacle(4, 7);

     //        JPSgraph.addObstacle(3, 2);
   //    JPSgraph.addObstacle(4, 3);
        JPSgraph.addObstacle(6, 3);
        JPSgraph.addObstacle(3, 3);
        JPSgraph.addObstacle(3, 4);
        JPSgraph.addObstacle(4, 6);  //???
        JPSgraph.addObstacle(4, 5);
    //      JPSgraph.addObstacle(4, 1);
      JPSgraph.addObstacle(1, 7);
        JPSgraph.addObstacle(2, 7);
        JPSgraph.addObstacle(3, 7);
        JPSgraph.addObstacle(4,7);
  //      JPSgraph.addObstacle(5, 7);
        JPSgraph.addObstacle(6,7);
        JPSgraph.addObstacle(7,7);
        JPSgraph.addObstacle(8,7);
        JPSgraph.addObstacle(9, 7);
        JPSgraph.addObstacle(10, 7);
        JPSgraph.addObstacle(5, 5);
        JPSgraph.addObstacle(4, 4);
        JPSgraph.addObstacle(6, 4);
        JPSgraph.addObstacle(5, 8);
        JPSgraph.addObstacle(6, 8);
        JPSgraph.addObstacle(5, 6);
        JPSgraph.addObstacle(7, 6);

//
        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.findRoute();
        System.out.println("Dijkstra");
        dijkstra.route.forEach(node -> System.out.println(node));
        System.out.println(graph);
        System.out.println("");

        System.out.println("JPS");
        JPS jps = new JPS(JPSgraph);
        System.out.println(JPSgraph);
        jps.shortestPath();
        System.out.println("");
        System.out.println("");
        System.out.println(JPSgraph);
        jps.route.forEach(n -> System.out.println(n));

    }

}
