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
public class JPSNode implements Comparable<JPSNode> {

    int nodeX;
    int nodeY;
    JPSNode parent;
    double g, h, f;
    int parentX;
    int parentY;

    /**
     * Creates a node and initializes g,h and f to infinity.
     * @param x Node x.
     * @param y Node y.
     */
    public JPSNode(int x, int y) {
        this.nodeX = x;
        this.nodeY = y;
        this.g = Double.POSITIVE_INFINITY;
        this.h = Double.POSITIVE_INFINITY;
        updateGHF(this.g, this.h);
        this.parentX = -1;
        this.parentY = -1;
    }

    /**
     * Updates the g, h and f values of the node.
     * @param g movement cost from start node.
     * @param h heuristic evaluation of distance to goal node.
     */
    public void updateGHF(double g, double h) {
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    /**
     * Updates parent as x and y value.
     * @param x
     * @param y 
     */
    public void setParent(int x, int y) {
        this.parentX = x;
        this.parentY = y;
    }

    @Override
    public int compareTo(JPSNode t) {
        if (this.f < t.f) {
            return -1;
        }
        if (this.f > t.f) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return "(" + this.nodeX + ", " + this.nodeY + "), parent: " + this.parentX + ", " + this.parentY + ".g: " + String.format("%.2f", this.g) + ", h: " + String.format("%.2f", this.h) + ", f: " + String.format("%.2f", this.f);

    }

}
