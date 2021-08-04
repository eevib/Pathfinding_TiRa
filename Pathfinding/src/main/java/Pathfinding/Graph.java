package Pathfinding;

/**
 *
 * @author eebe
 */
public class Graph {

    char[][] graph;
    int n;

    public Graph(int startX, int startY, int endX, int endY, int n) {
        graph = new char[n+1][n+1];
        this.n = n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[j][i] = '.';
            }
        }
        graph[startX][startY] = 'S';
        graph[endX][endY] = 'F';
    }

    public void addObstacle(int x, int y) {
        graph[x][y] = '@';
    }
    public void addRoutePoint(int x, int y) {
        if(graph[x][y]=='S') {
            return;
        }
        graph[x][y] = '+';
    }
    
    @Override
    public String toString() {
        String printedGraph = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                printedGraph += "" + graph[j][i];
            }
            printedGraph += "\n"; 
        }
        return printedGraph;
    }
}
