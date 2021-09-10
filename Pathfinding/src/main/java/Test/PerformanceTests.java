package Test;

import Pathfinding.Dijkstra;
import Pathfinding.Graph;
import Pathfinding.JPS;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTests {

    Random random = new Random();
    ArrayList<String[]> allLines;
    ArrayList<String[]> lines;
    Graph graph;
    Double averageDistance;

    public PerformanceTests(ArrayList<String[]> lines, Graph graph) {
        this.allLines = lines;
        this.graph = graph;
        this.lines = new ArrayList<String[]>();

    }

    public void run(int howMany) {
        getRandomLines(howMany);
        performanceTest(howMany);
        speedTest(howMany);
    }

    /**
     * Goes through list of random lines selecetd, compares Dijkstras distamce
     * to .scen distance and JPS distance to Dijkstra, if there is a error,
     * error % is calculated.
     *
     * @param howMany number of random lines selected.
     */
    public void performanceTest(int howMany) {
        Graph graph1 = graph;
        double totalDistance = 0;
        double errorDistance = 0;
        int nr = 0;
        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i);
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
                //   System.out.println(nr + ". i: " + i + ", dij: " + dijkstralength + ", jps: " + jps.getRouteDistance() + ", startx, y: " + startX + ", " + startY + ", endX,y: " + endX + ", " + endY);
                nr++;
                errorDistance = Math.abs(jps.getRouteDistance() - dijkstra.getRouteDistance());
                totalDistance += dijkstra.getRouteDistance();
            }
            if (!compareToScen(dijkstralength, scenDistance)) {
                System.out.println("The route with startPoint (" + startX + ", " + startY + ") and endPoint (" + endX + ", " + endY + ") are not correct ");
            }
//            jps.route.forEach(n -> System.out.println(n));
//            dijkstra.route.forEach(node -> System.out.println(node));
        }
        System.out.println("How many: " + howMany);
        System.out.println("Map: Berlin_2_1024");
        System.out.println("Average distance of routes: " + averageDistance);
        System.out.println("Error % in distance " + errorDistance / totalDistance * 100 + " %");
        System.out.println("Errors in total: " + nr);
        System.out.println("Error % in nuber of routes: " + (double) nr / howMany * 100 + " %");
        System.out.println();

    }

    /**
     * Compares the route distances found by Dijkstra and JPS.
     *
     * @param jps double distance of Jps route.
     * @param dijkstra doublde distance of Dijkstra route.
     * @return
     */
    public boolean compareDistances(Double jps, Double dijkstra) {
        if (Math.abs(jps - dijkstra) < 0.000001) {
            return true;
        }
        return false;
    }

    /**
     * Compares Dijkstras route distance to the one on .scen file, returns true
     * if Dijkstra is better or as good as .scen.
     *
     * @param dijkstra doublde distance of Dijkstra route.
     * @param scen doublde distance of .scen route.
     * @return
     */
    public boolean compareToScen(Double dijkstra, Double scen) {
        if (Math.abs(scen - dijkstra) < 0.000001 || dijkstra < scen) {
            return true;
        }
        return false;
    }

    /**
     * Checks the speed for JPS and Dijkstra separetly.
     *
     * @param howMany the number of random lines for the speed test.
     */
    public void speedTest(int howMany) {
        Graph graph1 = graph;

        long dijkstraTime = 0;
        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i);
            int startX = Integer.parseInt(line[4]);
            int startY = Integer.parseInt(line[5]);
            int endX = Integer.parseInt(line[6]);
            int endY = Integer.parseInt(line[7]);
            graph1.putStartPoint(startX, startY);
            graph1.putEndPoint(endX, endY);
            long startDij = System.currentTimeMillis();
            Dijkstra dijkstra = new Dijkstra(graph1);
            dijkstra.findRoute();
            long end = System.currentTimeMillis();
            dijkstraTime += (end - startDij);
        }
        System.out.println("Dijkstra time (milliseconds): " + dijkstraTime);
        long jpsTime = 0;
        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i);
            int startX = Integer.parseInt(line[4]);
            int startY = Integer.parseInt(line[5]);
            int endX = Integer.parseInt(line[6]);
            int endY = Integer.parseInt(line[7]);
            graph1.putStartPoint(startX, startY);
            graph1.putEndPoint(endX, endY);
            long jpsStart = System.currentTimeMillis();
            JPS jps = new JPS(graph1);
            jps.shortestPath();
            long jpsEnd = System.currentTimeMillis();
            jpsTime += (jpsEnd - jpsStart);
        }
        System.out.println("JPS time (milliseconds): " + jpsTime);

    }

    /**
     * Randomly selects wanted number of lines from the allLines list and puts
     * them to other list.
     *
     * @param howMany the number of random lines picked.
     */
    public void getRandomLines(int howMany) {
        double distance = 0;
        for (int i = 0; i < howMany; i++) {
            int index = (int) (Math.random() * (allLines.size() - 0)) + 0;
            //   line.add(allLines.get(index));
            lines.add(allLines.get(index));
            distance += Double.parseDouble(allLines.get(index)[8]);
        }
        this.averageDistance = distance / howMany;
    }
}
