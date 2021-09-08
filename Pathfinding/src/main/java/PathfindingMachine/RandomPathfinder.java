package PathfindingMachine;

import Pathfinding.Dijkstra;
import Pathfinding.Graph;
import Pathfinding.JPS;
import java.util.ArrayList;
import java.util.Random;

public class RandomPathfinder {

    Random random = new Random();
    ArrayList<String[]> lines;
    Graph graph;

    public RandomPathfinder(ArrayList<String[]> lines, Graph graph) {
        this.lines = lines;
        this.graph = graph;
        
    }

    public void findRandomPaths(int howMany) {
        Graph graph1 = graph;
        System.out.println("lines " + lines.size());
        int nr = 1;
        for (int i = 0; i < howMany; i++) {
            int index = (int) (Math.random() * (lines.size() - 0)) + 0;
            String[] line = lines.get(index);
            int startX = Integer.parseInt(line[4]);
            int startY = Integer.parseInt(line[5]);
            int endX = Integer.parseInt(line[6]);
            int endY = Integer.parseInt(line[7]);
            double scenDistance = Double.parseDouble(line[8]);
            graph1.putStartPoint(startX, startY);
            graph1.putEndPoint(endX, endY);
            Dijkstra dijkstra = new Dijkstra(graph1);
            JPS jps = new JPS(graph1);
            jps.shortestPath();
            dijkstra.findRoute();
            double dijkstralength = dijkstra.getRouteDistance();
            
            if (compareDistances(jps.getRouteDistance(), dijkstralength)) {
                //   System.out.println("The route with startPoint (" + startX + ", " + startY + ") and endPoint (" + endX + ", " + endY + ") are of similar length of " + dijkstralength);
                
            } else {
                System.out.println(nr + ". i: " + index + ", dij: " + dijkstralength + ", jps: " + jps.getRouteDistance() + ", startx, y: " + startX + ", " + startY + ", endX,y: " + endX + ", " + endY);
                nr++;
            }
            if (!compareToScen(dijkstralength, scenDistance)) {
                   System.out.println("The route with startPoint (" + startX + ", " + startY + ") and endPoint (" + endX + ", " + endY + ") are not correct ");
            }
//            jps.route.forEach(n -> System.out.println(n));
//            dijkstra.route.forEach(node -> System.out.println(node));
        }
    }

    public boolean compareDistances(Double jps, Double dijkstra) {
        if (Math.abs(jps - dijkstra) < 0.000001) {
            return true;

        }
        return false;
    }

    public boolean compareToScen(Double dijkstra, Double scen) {
        if (Math.abs(scen - dijkstra) < 0.00001 || dijkstra < scen) {
            return true;
        }
        return false;
    }
}
