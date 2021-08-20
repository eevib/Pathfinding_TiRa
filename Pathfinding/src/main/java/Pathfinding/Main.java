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
        Graph graph = new Graph(1, 1, 10, 10, mapSize + 1);
       // graph.addObstacle(3, 2);
       // graph.addObstacle(4, 4);
       // graph.addObstacle(3, 4);
       // graph.addObstacle(3, 5);
        System.out.println(graph);

//        Dijkstra dijkstra = new Dijkstra(graph);
//        dijkstra.findRoute();
//        dijkstra.route.forEach(node -> System.out.println(node));
//        System.out.println("");
      //  Graph graphJPS = new Graph(2, 2, 5, 10, mapSize + 1);
        JPS jps = new JPS(graph);
        JPSNode node = new JPSNode(2, 6);
        node.setParent(2, 5);
        List<JPSNode> successors = new ArrayList<>();
        successors = jps.identifySuccessors(node);
        successors.stream().forEach(n -> System.out.println(n));
        jps.shortestPath();
        System.out.println("");
        jps.printDistanceGraph();
        System.out.println("");
   //     System.out.println(graphJPS);
        //   jps.route.forEach(node -> System.out.println(node));
        
    }

}
