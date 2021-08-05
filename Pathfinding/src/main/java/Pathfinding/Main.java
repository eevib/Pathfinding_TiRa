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
        Dijkstra dijkstra = new Dijkstra(10, 1, 5, 10, mapSize);
        dijkstra.findRoute();
        dijkstra.route.forEach(node -> System.out.println(node));
        System.out.println("");
    }

}
