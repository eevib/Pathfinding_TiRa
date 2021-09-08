package IO;

import Pathfinding.Graph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

/**
 *
 * @author eebe
 */
public class MapReader {

    public Graph graph;
    int size;

    public MapReader() {
        
    }

    public void countRows(File file) throws FileNotFoundException, IOException {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            int lineNumber = 0;
            while (reader.readLine() != null) {
                lineNumber = reader.getLineNumber();

            }
            reader.close();
            size = lineNumber;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public Graph createGraph(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(file));
        this.graph = new Graph(size+1);
        while (scanner.hasNextLine()) {
            for (int i = 0; i < size; i++) {
                char[] nextLine = scanner.nextLine().toCharArray();
                for (int j = 0; j < size; j++) {
                    graph.putPoint(j, i, nextLine[j]);
                }
            }
        }
        return this.graph;
    }
}
