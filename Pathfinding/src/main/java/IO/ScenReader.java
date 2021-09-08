package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author eebe
 */
public class ScenReader {
    File file;
    ArrayList<String[]> lines;
    
    public ScenReader(File file) throws FileNotFoundException {
        this.file = file;
        this.lines = new ArrayList<>();
        readScen();
    }
    public void readScen() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(file));
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line.split("\\s+"));
            // StartX = 4, StartY = 5, endX = 6, endY = 7, distance = 8
        }
    }
    public ArrayList<String[]> getScens() {
        return this.lines;
    }
}
