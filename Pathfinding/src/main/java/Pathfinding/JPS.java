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
    Boolean[][] onOpenList;
    PriorityQueue<JPSNode> openList = new PriorityQueue<>();
    JPSNode startNode;
    JPSNode endNode;
    public ArrayList<JPSNode> route = new ArrayList<>();
    Graph graph;
    Boolean routeFound;
    Double routeDistance;

    /**
     *
     * @param graph map where starting node is marked S and goal node is marked
     * F. Obstacles are marked @.
     */
    public JPS(Graph graph) {
        this.routeDistance = Double.POSITIVE_INFINITY;
        this.routeFound = false;
        this.n = graph.getMapSize() + 1;
        this.graph = graph;
        this.startNode = new JPSNode(graph.startX, graph.startY);
        this.endNode = new JPSNode(graph.endX, graph.endY);
        nodes = new JPSNode[n][n];
        onOpenList = new Boolean[n][n];
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                JPSNode node = new JPSNode(i, j);
                nodes[i][j] = node;
                onOpenList[i][j] = false;
            }
        }

    }

    /**
     * Finds the shortest route from startpoint to endpoint using JPS.
     */
    public void shortestPath() {
        //start point added to openList and to nodes. 
        JPSNode start = new JPSNode(startNode.nodeX, startNode.nodeY);
        double distanceToEnd = estimateDistanceToEnd(start);
        start.updateGHF(0, distanceToEnd);
        nodes[startNode.nodeX][startNode.nodeY] = start;
        openList.add(start);
        onOpenList[start.nodeX][start.nodeY] = true;
        // Picks the node with smallest f value. 
        while (!openList.isEmpty()) {
            JPSNode current = openList.poll();
            onOpenList[current.nodeX][current.nodeY] = false;

            // End node is found
            if (current.nodeX == endNode.nodeX && current.nodeY == endNode.nodeY) {
                endNode = current;
                routeFound = true;
                routeDistance = current.g;
                findPath(nodes[endNode.nodeX][endNode.nodeY]);
                break;
            }
            List<JPSNode> successorNodes = identifySuccessors(current);

            // gets successors and adds them to openList.
            if (!successorNodes.isEmpty()) {
                for (int i = 0; i < successorNodes.size(); i++) {
                    JPSNode successor = successorNodes.get(i);
                    if (successor.g < nodes[successor.nodeX][successor.nodeY].g) {
                        nodes[successor.nodeX][successor.nodeY] = successor;
                        if (!onOpenList[successor.nodeX][successor.nodeY]) {
                            openList.add(successor);
                            onOpenList[successor.nodeX][successor.nodeY] = true;
                        }

                    }

                }
            }
        }
        // If the openList gets empty without reaching end point, no route is found between start and end point. 
        if (!routeFound) {
            System.out.println("No route found");
        }

    }

    /**
     * Identifies the successor nodes.
     *
     * @param node to which successors are searched.
     * @return A arrayList of successor nodes. If no successors are found,
     * returns an empty list.
     */
    public List<JPSNode> identifySuccessors(JPSNode node) {
        List<JPSNode> successors = new ArrayList<>();
        int[][] neighbours = pruneNeighbours(node); // Pruned neighbours. 

        // Goes through all neighbours
        for (int i = 0; i < neighbours.length; i++) {
            int nx = neighbours[i][0]; // neighbour x
            int ny = neighbours[i][1]; // neighbour y
            // If neighour is null, it is marked -1 and we can continue.
            if (nx == -1) {
                continue;
            }
            // Jumps from node to the direction of neighbour
            JPSNode jump = jump(node, nx - node.nodeX, ny - node.nodeY);
            if (jump != null) {
                jump.setParent(node.nodeX, node.nodeY);
                double nodeCost = getJumpCost(jump);
                jump.updateGHF(node.g + nodeCost, estimateDistanceToEnd(jump));
                successors.add(jump);
                //  graph.addJumpPoint(jump.nodeX, jump.nodeY);
            }
        }
        return successors;
    }

    /**
     * Jumps and returns node jumped to, if found.
     *
     * @param node the node from where the jump is done.
     * @param dx x direction
     * @param dy y direction
     * @return JPS node if forced neighbours or end node is found, else jumps
     * again from neighbour node to same direction.
     */
    public JPSNode jump(JPSNode node, int dx, int dy) {
        int x = node.nodeX;
        int y = node.nodeY;
        if (!graph.passable(x + dx, y + dy)) {
            return null;
        }
        JPSNode n = new JPSNode(x + dx, y + dy);
        n.setParent(node.nodeX, node.nodeY);
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
        if (dx != 0 && dy != 0) {
            if (jump(n, dx, 0) != null) {
                return n;
            }
            if (jump(n, 0, dy) != null) {
                return n;
            }
        }
        return jump(n, dx, dy);
    }

    // A node n in neighbours(x) is forced if:
    // 1. n is not a natural neighbour of x
    // 2. len((p(x),x,n))< len((p(x),...,x,n)\x)
    // https://lucho1.github.io/JumpPointSearch/#before-starting pictures used to understand rules
    /**
     * Returns true if a node has a forced neighbour, moving in direction dx, dy.
     * @param x
     * @param y
     * @param dx
     * @param dy
     * @return 
     */
    public boolean forcedNeighbours(int x, int y, int dx, int dy) {
        // diagonal direction
        if (dx != 0 && dy != 0) {

            if (!graph.passable(x, y - dy)) {
                if (graph.passable(x + dx, y - dy)) {
                    return true;
                }
            }
            if (!graph.passable(x - dx, y)) {
                if (graph.passable(x - dx, y + dy)) {
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

                //      vertical direction, dx = 0;
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
     * Calculates the total distance from parent node.
     *
     * @param node the one the distance is calculated to.
     * @return double value with the distance.
     */
    public double getJumpCost(JPSNode node) {
        int dx = (node.nodeX - node.parentX) / Math.max(Math.abs(node.nodeX - node.parentX), 1);
        int dy = (node.nodeY - node.parentY) / Math.max(node.nodeY - node.parentY, 1);
        int jumpDistanceX = Math.abs(node.parentX - node.nodeX);
        int jumpDistanceY = Math.abs(node.parentY - node.nodeY);
        if (dx != 0 && dy != 0) {
            return sqrt(2) * jumpDistanceX;
        } else {
            if (dx != 0) {
                return jumpDistanceX;
            } else {
                return jumpDistanceY;
            }
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
        // When no parents, return all neighbours.
        if (px == -1) {
            return graph.getNeighbours(x, y);
        }

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
            } else {
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

    /**
     * Finds the path from the endNode to start node, by moving from one node to
     * its parent.
     *
     * @param endNode
     */
    public void findPath(JPSNode endNode) {
        JPSNode nextNode = nodes[endNode.nodeX][endNode.nodeY];
        route.add(endNode);
        while (true) {
            nextNode = nodes[nextNode.parentX][nextNode.parentY];
            if (nextNode.nodeX == startNode.nodeX && nextNode.nodeY == startNode.nodeY) {
                route.add(nextNode);
                Collections.reverse(route);
                break;
            }
            route.add(nextNode);

        }

    }

    /**
     * Draws the route starting from end node, by calculating the distance and
     * direction between jump points.
     */
    public void drawRoute() {
        Collections.reverse(route);
        for (JPSNode n : route) {
            if (n.parentX == -1) {
                Collections.reverse(route);
                return;
            }
            int distanceX = n.nodeX - n.parentX;
            int distanceY = n.nodeY - n.parentY;
            int x = n.nodeX;
            int y = n.nodeY;
            //  System.out.println("x " + x + ", y: " +y);

            // moving diagonal
            if (distanceX != 0 && distanceY != 0) {
                if (distanceX > 0 && distanceY > 0) {
                    for (int i = distanceX; i > 0; i--) {
                        graph.addJPSRoutePoint(x, y);
                        x--;
                        y--;
                    }

                } else if (distanceX < 0 && distanceY < 0) {
                    distanceY = -distanceY;
                    for (int i = distanceY; i > 0; i--) {
                        graph.addJPSRoutePoint(x, y);
                        x++;
                        y++;
                    }
                } else if (distanceY < 0) {
                    for (int i = 0; i < distanceX; i++) {
                        graph.addJPSRoutePoint(x, y);
                        x--;
                        y++;
                    }

                } else if (distanceX < 0) {
                    for (int i = 0; i < distanceY; i++) {
                        graph.addJPSRoutePoint(x, y);
                        x++;
                        y--;
                    }
                    //distanceX and distanceY < 0
                }

                // moving horizontal
            } else if (distanceX != 0) {
                if (distanceX > 0) {
                    for (int i = distanceX; i > 0; i--) {
                        graph.addJPSRoutePoint(x, y);
                        x--;
                    }

                } else { //distanceX < 0
                    distanceX = Math.abs(distanceX);
                    for (int i = distanceX; i > 0; i--) {
                        graph.addJPSRoutePoint(x, y);
                        x++;
                    }
                }
                // Moving vertical
            } else {
                if (distanceY > 0) {
                    for (int i = distanceY; i > 0; i--) {
                        graph.addJPSRoutePoint(x, y);
                        y--;
                    }
                } else {
                    distanceY = Math.abs(distanceY);
                    for (int i = distanceY; i > 0; i--) {
                        graph.addJPSRoutePoint(x, y);
                        y++;
                    }
                }

            }
        }
    }

    /**
     * Estimates the heuristic distance to end node.
     *
     * @param node The node for which the estimation is made.
     * @return Double heuristic distance to end node.
     */
    public double estimateDistanceToEnd(JPSNode node) {
        int x = Math.abs(node.nodeX - endNode.nodeX);
        int y = Math.abs(node.nodeY - endNode.nodeY);
        double distance = Math.min(x, y) * sqrt(2) + Math.abs(y - x);
        return distance;
    }
    
    
    public double getRouteDistance() {
        return this.routeDistance;
    }

    /**
     * Just to help debuggig.
     */
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
