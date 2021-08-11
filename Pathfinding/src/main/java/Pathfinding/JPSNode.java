/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

/**
 *
 * @author eebe
 */
public class JPSNode {
    int nodeX;
    int nodeY;
    Node parent;
    double g, h, f;
    
    public JPSNode(int x, int y) {
        this.nodeX = x;
        this.nodeY = y;
    }
    
    public void updateGHFParent(double g, double h, Node parent) {
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.parent = parent;
    }

    
}
