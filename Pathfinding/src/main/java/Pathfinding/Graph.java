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
                graph[i][j] = '.';
            }
        }
        graph[startX][startY] = 'S';
        graph[endX][endY] = 'F';
    }

    public Graph(int n) {
        this.n = n;
        graph = new char[n][n];
    }

    public boolean putStartPoint(int startX, int startY) {
        if (this.passable(startX, startY)) {
            graph[startX][startY] = 'S';
            this.startX = startX;
            this.startY = startY;
            return true;
        }
        return false;
    }

    public boolean putEndPoint(int endX, int endY) {
        if (this.passable(endX, endY)) {
            graph[endX][endY] = 'F';
            this.endX = endX;
            this.endY = endY;
            return true;
        }
        return false;
    }

    public void addObstacle(int x, int y) {
        graph[x][y] = '@';
    }

    public void addRoutePoint(int x, int y) {
        if (graph[x][y] == 'S') {
            return;
        }
        if (graph[x][y] == 'F') {
            return;
        }
        graph[x][y] = '+';
    }

    public void addJPSRoutePoint(int x, int y) {
        if (graph[x][y] == 'S') {
            return;
        }
        if (graph[x][y] == 'F') {
            return;
        }
        graph[x][y] = '?';
    }

    public char getNode(int x, int y) {
        return graph[x][y];
    }

    public void putPoint(int x, int y, char mark) {
        graph[x][y] = mark;
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
        return graph.length;
    }

    /**
     * Returns true if x,y is not under 0, outside of map or an obstacle
     * @param x
     * @param y
     * @return 
     */
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

    /**
     * Returns a list with neigbours. In column 0 neigbhbour x value, in column
     * 1 neighbour y value. If neihgbour is non passable it is marked -1,-1.
     *
     * @param x
     * @param y
     * @return a list of neighbours.
     */
    public int[][] getNeighbours(int x, int y) {
        int[][] neighbours = new int[8][2];

        if (passable(x - 1, y)) {
            neighbours[0][0] = x - 1;
            neighbours[0][1] = y;
        } else {
            neighbours[0][0] = -1;
            neighbours[0][1] = -1;
        }

        if (passable(x + 1, y)) {
            neighbours[1][0] = x + 1;
            neighbours[1][1] = y;

        } else {
            neighbours[1][0] = -1;
            neighbours[1][1] = -1;
        }

        if (passable(x, y - 1)) {
            neighbours[2][0] = x;
            neighbours[2][1] = y - 1;
        } else {
            neighbours[2][0] = -1;
            neighbours[2][1] = -1;
        }

        if (passable(x, y + 1)) {
            neighbours[3][0] = x;
            neighbours[3][1] = y + 1;

        } else {
            neighbours[3][0] = -1;
            neighbours[3][1] = -1;
        }

        if (passable(x - 1, y - 1)) {
            neighbours[4][0] = x - 1;
            neighbours[4][1] = y - 1;
        } else {
            neighbours[4][0] = -1;
            neighbours[4][1] = -1;
        }

        if (passable(x - 1, y + 1)) {
            neighbours[5][0] = x - 1;
            neighbours[5][1] = y + 1;

        } else {
            neighbours[5][0] = -1;
            neighbours[5][1] = -1;
        }

        if (passable(x + 1, y - 1)) {
            neighbours[6][0] = x + 1;
            neighbours[6][1] = y - 1;

        } else {
            neighbours[6][0] = -1;
            neighbours[6][1] = -1;
        }
        if (passable(x + 1, y + 1)) {
            neighbours[7][0] = x + 1;
            neighbours[7][1] = y + 1;

        } else {
            neighbours[7][0] = -1;
            neighbours[7][1] = -1;
        }
        return neighbours;
    }

    /**
     * Adds a jump point to the graph.
     * @param x
     * @param y 
     */
    public void addJumpPoint(int x, int y) {
        if (graph[x][y] == 'S') {
            return;
        }
        if (graph[x][y] == 'F') {
            return;
        }
        graph[x][y] = '*';
    }
}
