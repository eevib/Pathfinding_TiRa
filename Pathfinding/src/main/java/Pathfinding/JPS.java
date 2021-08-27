package Pathfinding;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author eebe
 */
public class JPS {

    int n;
    JPSNode[][] nodes;
    JPSNode[][] possibleBetterNodes;
    Boolean[][] isAlreadyChecked;
    ArrayList<JPSNode> closedList = new ArrayList<>();
    PriorityQueue<JPSNode> openList = new PriorityQueue<>();
    JPSNode startNode;
    JPSNode endNode;
    public ArrayList<JPSNode> route = new ArrayList<>();
    Graph graph;

    /**
     *
     * @param graph map where starting node is marked S and goal node is marked
     * F. Obstacles are marked @.
     */
    public JPS(Graph graph) {
        this.n = graph.getMapSize() + 1;
        this.graph = graph;
        this.startNode = new JPSNode(graph.startX, graph.startY);
        this.endNode = new JPSNode(graph.endX, graph.endY);
        nodes = new JPSNode[n][n];
        possibleBetterNodes = new JPSNode[n][n];
        isAlreadyChecked = new Boolean[n][n];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                JPSNode node = new JPSNode(i, j);
                nodes[i][j] = node;
                isAlreadyChecked[i][j] = false;
                possibleBetterNodes[i][j] = node;
            }
        }

    }

    /**
     * Supposed to find the shortest route from startpoint to endpoint using
     * JPS, not yet working as it should.
     */
    public void shortestPath() {
        //start point added to openList and to nodes. 
        JPSNode start = new JPSNode(startNode.nodeX, startNode.nodeY);
        double distanceToEnd = estimateDistanceToEnd(start);
        start.updateGHF(0, distanceToEnd);
        nodes[startNode.nodeX][startNode.nodeY] = start;
        openList.add(start);
        isAlreadyChecked[start.nodeX][start.nodeY] = true;
        // Picks the node with smallest h value. 
        while (!openList.isEmpty()) {
            JPSNode current = openList.poll();
            System.out.println("current " + current);
            isAlreadyChecked[current.nodeX][current.nodeY] = true;

            // End node is found
            if (current.nodeX == endNode.nodeX && current.nodeY == endNode.nodeY) {
                endNode = current;
                System.out.println("end: " + endNode);
                findPath(nodes[endNode.nodeX][endNode.nodeY]);
                break;
            }
            List<JPSNode> successorNodes = identifySuccessors(current);

            // gets successors and adds them to openList.
            if (!successorNodes.isEmpty()) {
                for (int i = 0; i < successorNodes.size(); i++) {
                    JPSNode successor = successorNodes.get(i);
                    System.out.println("successor: " + successor);
                    if (isAlreadyChecked[successor.nodeX][successor.nodeY]) {
                        if (successor.g < nodes[successor.nodeX][successor.nodeY].g) {
                            nodes[successor.nodeX][successor.nodeY] = successor;
                        }
                    }
                    openList.add(successor);
                }
            }
            //   printDistanceGraph();
        }
    }

    public List<JPSNode> identifySuccessors(JPSNode node) {
        List<JPSNode> successors = new ArrayList<>();
        int[][] neighbours = pruneNeighbours(node); // All neighbours
        for (int i = 0; i < neighbours.length; i++) {
            int nx = neighbours[i][0];
            int ny = neighbours[i][1];
            JPSNode jump = jump(node, nx - node.nodeX, ny - node.nodeY);
            if (jump != null) {
                jump.setParent(node.nodeX, node.nodeY);
                successors.add(jump);
                graph.addJumpPoint(jump.nodeX, jump.nodeY);
                if (possibleBetterNodes[jump.nodeX][jump.nodeY].g < nodes[jump.nodeX][jump.nodeY].g) {
                    nodes[jump.nodeX][jump.nodeY] = jump;
                }
            }
        }
        return successors;
    }

    public JPSNode jump(JPSNode node, int dx, int dy) {
        int x = node.nodeX;
        int y = node.nodeY;
        if (!graph.passable(x + dx, y + dy)) {
            return null;
        }
        JPSNode n = new JPSNode(x + dx, y + dy);
        n.setParent(node.nodeX, node.nodeY);
        double nodeCost = getNodeCost(n);
        n.updateGHF(node.g + nodeCost, estimateDistanceToEnd(n));
        possibleBetterNodes[n.nodeX][n.nodeY] = n;
        if (n.nodeX == endNode.nodeX && n.nodeY == endNode.nodeY) {
            return n;
        }

        int dxn = (n.nodeX - node.nodeX) / Math.max(Math.abs(n.nodeX - node.nodeX), 1);
        int dyn = (n.nodeY - node.nodeY) / Math.max(Math.abs(n.nodeY - node.nodeY), 1);
        // When forced neighbours are found the node is returned
        if (forcedNeighbours(n.nodeX, n.nodeY, dxn, dyn)) {
            return n;
        }
        // diagonal direction
        if (dxn != 0 && dyn != 0) {
            if (jump(n, dxn, 0) != null) {
                return n;
            }
            if (jump(n, 0, dyn) != null) {
                return n;
            }
        }
        // Normalized direction
//        int dx1 = (n.nodeX - node.nodeX) / Math.max(Math.abs(n.nodeX - node.nodeX), 1);
//        int dy1 = (n.nodeY - node.nodeY) / Math.max(Math.abs(n.nodeY - node.nodeY), 1);
        //   System.out.println("return jump: " + n + ", dx + dy " + dx + dy);
        return jump(n, dxn, dyn);
    }

    // A node n in neighbours(x) is forced if:
    // 1. n is not a natural neighbour of x
    // 2. len((p(x),x,n))< len((p(x),...,x,n)\x)
    // https://lucho1.github.io/JumpPointSearch/#before-starting pictures used to understand rules
    public boolean forcedNeighbours(int x, int y, int dx, int dy) {
//        if (dx == 0 || dy == 0) {
//            if (dx != 0) {
//                if (graph.passable(x + dx, y + 1) && !graph.passable(x, y + 1)
//                        || graph.passable(x + dx, y - 1) && !graph.passable(x, y - 1)) {
//                    return true;
//                }
//            } else {
//                if (graph.passable(x + 1, y + dy) && !graph.passable(x + 1, y)
//                        || graph.passable(x - 1, y + dy) && !graph.passable(x - 1, y)) {
//                    return true;
//                }
//
//            }
//        } else if (dx != 0 && dy != 0) {
//            if (!graph.passable(x + dx, y + dy) && graph.passable(x + dx, y)
//                    && graph.passable(x, y + dy)) {
//                return true;
//            }


        // diagonal direction
        if (dx != 0 && dy != 0) {
            if (!graph.passable(x - dx, y)) {
                if (graph.passable(x - dx, y + dy)) {
                    return true;
                }
            }
            if (!graph.passable(x, y - dy)) {
                if (graph.passable(x + dx, y - dy)) {
                    return true;
                }
            }
            // horizontal direction
        } else {

            if (dx != 0) {
                if (!graph.passable(x, y - 1)) {
                    if (graph.passable(x + dx, y - 1)) {
                        return true;
                    }
                }
                if (!graph.passable(x, y + 1)) {
                    if (graph.passable(x + dx, y + 1)) {
                        return true;
                    }
                }
                // vertical direction, dx = 0;
            } else {
                if (!graph.passable(x - 1, y)) {
                    if (graph.passable(x - 1, y + dy)) {
                        return true;
                    }
                }
                if (!graph.passable(x + 1, y)) {
                    if (graph.passable(x + 1, y + dy)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the cost of travel from parent node, sqrt(2) for diagonal and 1
     * for straight.
     *
     * @param node
     * @return the distance from the neighbour node
     */
    public double getNodeCost(JPSNode node) {
        int dx = (node.nodeX - node.parentX) / Math.max(Math.abs(node.nodeX - node.parentX), 1);
        int dy = (node.nodeY - node.parentY) / Math.max(node.nodeY - node.parentY, 1);
        if (dx != 0 && dy != 0) {
            return sqrt(2);
        } else {
            return 1;

        }
    }

    /**
     * Goes through all neighbours and prunes neighbours not wanted/ needed.
     *
     * @param node
     * @return A list of neighbours where [][]=-1 indicates null;
     */
    public int[][] pruneNeighbours(JPSNode node) {
        int x = node.nodeX;
        int y = node.nodeY;
        int px = node.parentX;
        int py = node.parentY;
        int[][] pNeighbours = new int[5][2];

        // Normalized direction
        int dx = (x - px) / Math.max(Math.abs(x - px), 1);
        int dy = (y - py) / Math.max(Math.abs(y - py), 1);

        // Moving diagonal
        if (dx != 0 && dy != 0) {
            if (graph.passable(x, y + dy)) {
                pNeighbours[0][0] = x;
                pNeighbours[0][1] = y + dy;
            }
            if (graph.passable(x + dx, y)) {
                pNeighbours[1][0] = x + dx;
                pNeighbours[1][1] = y;
            }
            if (graph.passable(x, y + dy) || graph.passable(x + dx, y)) {
                pNeighbours[2][0] = x + dx;
                pNeighbours[2][1] = y + dy;
            }
            if (!graph.passable(x - dx, y) && graph.passable(x, y + dy)) {
                pNeighbours[3][0] = x - dx;
                pNeighbours[3][1] = y + dy;
            }
            if (!graph.passable(x, y - dy) && graph.passable(x + dx, y)) {
                pNeighbours[4][0] = x + dx;
                pNeighbours[4][1] = y - dy;
            }
        } else {
            // Moving vertical
            if (dx == 0) {
                if (graph.passable(x, y + dy)) {
                    pNeighbours[0][0] = x;
                    pNeighbours[0][1] = y + dy;
                }
                if (!graph.passable(x + 1, y)) {
                    pNeighbours[1][0] = x + 1;
                    pNeighbours[1][1] = y + dy;
                }
                if (!graph.passable(x - 1, y)) {
                    pNeighbours[2][0] = x - 1;
                    pNeighbours[2][1] = y + dy;
                }
                // Moving horizontal
            } else if (graph.passable(x + dx, y)) {
                if (graph.passable(x + dx, y)) {
                    pNeighbours[0][0] = x + dx;
                    pNeighbours[0][1] = y;
                }
                if (!graph.passable(x, y + 1)) {
                    pNeighbours[1][0] = x + dx;
                    pNeighbours[1][1] = y + 1;
                }
                if (!graph.passable(x, y - 1)) {
                    pNeighbours[2][0] = x + dx;
                    pNeighbours[2][1] = y - 1;
                }
            }
        }
        return pNeighbours;
    }

    public void findPath(JPSNode endNode) {
        JPSNode nextNode = nodes[endNode.nodeX][endNode.nodeY];
        route.add(endNode);
        while (true) {
            nextNode = nodes[nextNode.parentX][nextNode.parentY];
            route.add(nextNode);
            if (nextNode.nodeX == startNode.nodeX && nextNode.nodeY == startNode.nodeY) {
                Collections.reverse(route);
                break;
            }
            graph.addRoutePoint(nextNode.nodeX, nextNode.nodeY);
        }

    }

    /**
     * Estimates the shortest distance to end node.
     *
     * @param node The node for which the estimation is made.
     * @return Double distance to end node.
     */
    public double estimateDistanceToEnd(JPSNode node) {
        int x = Math.abs(node.nodeX - endNode.nodeX);
        int y = Math.abs(node.nodeY - endNode.nodeY);
        double distance = Math.min(x, y) * sqrt(2) + Math.abs(y - x);
        return distance;
    }

    public void printDistanceGraph() {
        System.out.println("Distance from start");
        for (int y = 1; y < n; y++) {
            for (int x = 1; x < n; x++) {
                String distance = String.format("%.2f", nodes[x][y].g);
                if (distance.equalsIgnoreCase("infinity")) {
                    distance = "inf ";
                }
                System.out.print(distance + "    ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("********************************");
        System.out.println("H");
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                String h = String.format("%.2f", nodes[j][i].h);
                if (h.equalsIgnoreCase("infinity")) {
                    h = "inf ";
                }
                System.out.print(h + "    ");
            }
            System.out.println("");
        }
//        System.out.println("");
//        System.out.println("********************************");
//        System.out.println("Just a graph to check that my graph x and y is right");
//        for (int i = 1; i < n; i++) {
//            for (int j = 1; j < n; j++) {
//                System.out.print(nodes[j][i].nodeX + "," + nodes[j][i].nodeY + "  ");
//            }
//            System.out.println("");
//        }

        System.out.println("");
        System.out.println("********************************");
        System.out.println("Parents");
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                String parents = "" + nodes[j][i].parentX + "," + nodes[j][i].parentY + "  ";
                System.out.print(parents);
            }
            System.out.println("");
        }
    }
}
