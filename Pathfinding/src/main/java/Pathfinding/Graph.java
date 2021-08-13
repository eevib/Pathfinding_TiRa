package Pathfinding;

import static java.lang.Math.sqrt;

/**
 *
 * @author eebe
 */
public class Graph {

    char[][] graph;
    int n;
    int startX;
    int startY;
    int endX;
    int endY;

    public Graph(int startX, int startY, int endX, int endY, int n) {
        graph = new char[n][n];
        this.endX = endX;
        this.endY = endY;
        this.startX = startX;
        this.startY = startY;
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
        if (graph[x][y] == 'S') {
            return;
        }
        graph[x][y] = '+';
    }

    public char getNode(int x, int y) {
        return graph[x][y];
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

    public int getMapSize() {
        return graph.length - 1;
    }

    public boolean passable(int x, int y) {
        if (x < n && y < n && x >= 0 && y >= 0) {
            if (graph[x][y] != '@') {
                return true;
            }
        }
        return false;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public int[][] getNeighbours(int x, int y) {
        int[][] neighbours = new int[8][3];

        if (passable(x - 1, y)) {
            neighbours[0][0] = x - 1;
            neighbours[0][1] = y;
            neighbours[0][2] = 1;
        } else {
            neighbours[0][0] = -1;
            neighbours[0][1] = -1;
        }

        if (passable(x + 1, y)) {
            neighbours[1][0] = x + 1;
            neighbours[1][1] = y;
            neighbours[1][2] = 1;

        } else {
            neighbours[1][0] = -1;
            neighbours[1][1] = -1;
        }

        if (passable(x, y - 1)) {
            neighbours[2][0] = x;
            neighbours[2][1] = y - 1;
            neighbours[2][2] = 1;
        } else {
            neighbours[2][0] = -1;
            neighbours[2][1] = -1;
        }

        if (passable(x, y + 1)) {
            neighbours[3][0] = x;
            neighbours[3][1] = y + 1;
            neighbours[3][2] = 1;
        } else {
            neighbours[3][0] = -1;
            neighbours[3][1] = -1;
        }

        if (passable(x - 1, y - 1)) {
            neighbours[4][0] = x - 1;
            neighbours[4][1] = y - 1;
            neighbours[4][2] = 2;
        } else {
            neighbours[4][0] = -1;
            neighbours[4][1] = -1;
        }

        if (passable(x - 1, y + 1)) {
            neighbours[5][0] = x - 1;
            neighbours[5][1] = y + 1;
            neighbours[5][2] = 2;
        } else {
            neighbours[5][0] = -1;
            neighbours[5][1] = -1;
        }

        if (passable(x + 1, y - 1)) {
            neighbours[6][0] = x + 1;
            neighbours[6][1] = y - 1;
            neighbours[6][2] = 2;
        } else {
            neighbours[6][0] = -1;
            neighbours[6][1] = -1;
        }
        if (passable(x + 1, y + 1)) {
            neighbours[7][0] = x + 1;
            neighbours[7][1] = y + 1;
            neighbours[7][2] = 2;
        } else {
            neighbours[7][0] = -1;
            neighbours[7][1] = -1;
        }
        return neighbours;
    }
}
