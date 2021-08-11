package Pathfinding;

/**
 *
 * @author eebe
 */
public class Node implements Comparable<Node> {
    int nodeX;
    int nodeY;
    double distance;
    
    public Node(int nodeX, int nodeY, double distance) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.distance = distance;
    }
    @Override
    public int compareTo(Node otherNode) {
        if(this.distance < otherNode.distance) return -1;
        if(this.distance > otherNode.distance) return 1;
        return 0;
    }
    
    @Override
    public String toString() {
        return "(" + nodeX + nodeY + "), distance " + distance;
    }

    public void setNodeX(int nodeX) {
        this.nodeX = nodeX;
    }

    public void setNodeY(int nodeY) {
        this.nodeY = nodeY;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
