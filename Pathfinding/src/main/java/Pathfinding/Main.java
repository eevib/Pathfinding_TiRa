/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import IO.MapReader;
import IO.ScenReader;
import PathfindingMachine.RandomPathfinder;
import java.io.File;
import java.io.IOException;
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
    public static void main(String[] args) throws IOException {

        //1. i: 410, dij: 163.5512985522206, jps: 167.2081528017131, startx, y: 420, 64, endX,y: 423, 162
        // 92	Berlin_0_256.map	256	256	22	6	253	255	371.62950897 
        //  92	Berlin_0_256.map	256	256	8	10	242	245	369.41630554 
        // 87	Berlin_0_256.map	256	256	251	224	6	177	348.61731567
        //  87	Berlin_0_256.map	256	256	4	173	241	233	348.83051910
        MapReader map = new MapReader();
//        map.countRows(new File("Berlin_0_256.map"));
//        Graph BerlinGraph = map.createGraph(new File("Berlin_0_256.map"));
//        ScenReader scen = new ScenReader(new File("Berlin_0_256.map.scen"));
        map.countRows(new File("Berlin_1_512.map"));
//        RandomPathfinder paths = new RandomPathfinder(scen.getScens(), BerlinGraph);

        Graph Berlin_1_512 = map.createGraph(new File("Berlin_1_512.map"));
        ScenReader scen512 = new ScenReader(new File("Berlin_1_512.map.scen"));
//        Berlin_1_512.putStartPoint(420, 64);
//        Berlin_1_512.putEndPoint(423, 162);
        RandomPathfinder paths512 = new RandomPathfinder(scen512.getScens(), Berlin_1_512);
        paths512.findRandomPaths(100);

//        BerlinGraph.putStartPoint(251, 224);
//        BerlinGraph.putEndPoint(6, 177);
        int mapSize = 10;
        Graph graph = new Graph(0, 0, 5, 10, 11);
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
        JPSgraph.addObstacle(4, 7);
        //      JPSgraph.addObstacle(5, 7);
        JPSgraph.addObstacle(6, 7);
        JPSgraph.addObstacle(7, 7);
        JPSgraph.addObstacle(8, 7);
        JPSgraph.addObstacle(9, 7);
        JPSgraph.addObstacle(10, 7);
        JPSgraph.addObstacle(5, 5);
        JPSgraph.addObstacle(4, 4);
        JPSgraph.addObstacle(6, 4);
        JPSgraph.addObstacle(5, 8);
        JPSgraph.addObstacle(6, 8);
        JPSgraph.addObstacle(5, 6);
        JPSgraph.addObstacle(7, 6);

//        Dijkstra dijkstra = new Dijkstra(Berlin_1_512);
//        dijkstra.findRoute();
//        System.out.println("Dijkstra");
//        dijkstra.route.forEach(node -> System.out.println(node));
//        //  System.out.println(graph);
//        System.out.println("");
//
//        System.out.println("JPS");
//        JPS jps = new JPS(Berlin_1_512);
//        //     System.out.println(JPSgraph);
//        jps.shortestPath();
//        System.out.println("");
//        System.out.println("");
//        jps.drawRoute();
//        dijkstra.drawRoute();
//        System.out.println(Berlin_1_512);
//        jps.route.forEach(n -> System.out.println(n));

    }

}
