/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import IO.MapReader;
import IO.ScenReader;
import Test.PerformanceTests;
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

        MapReader map = new MapReader();
        Graph Berlin_2_1024 = map.createGraph(new File("Denver_2_1024.map"));
        ScenReader Berlin_2_1024_scen = new ScenReader(new File("Denver_2_1024.map.scen"));
        PerformanceTests paths_Berlin_2_1024 = new PerformanceTests(Berlin_2_1024_scen.getScens(), Berlin_2_1024);
        paths_Berlin_2_1024.run(10);
        
        Graph Berlin_1_512 = map.createGraph(new File("Berlin_1_512.map"));
        ScenReader Berlin_1_512_scen = new ScenReader(new File("Berlin_1_512.map.scen"));
        Berlin_1_512.putStartPoint(0, 0);
        Berlin_1_512.putEndPoint(500, 500);
        Dijkstra dij = new Dijkstra(Berlin_1_512);
        JPS jps = new JPS(Berlin_1_512);
        dij.findRoute();
        jps.shortestPath();
        dij.drawRoute();
        System.out.println(dij.routeDistance);
        jps.drawRoute();
        System.out.println(Berlin_1_512);
        
        

    }

}
