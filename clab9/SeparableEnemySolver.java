import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class SeparableEnemySolver {

    Graph g;
    boolean SOLVED = false;
    boolean res;
    HashMap<String, Boolean> marked;
    HashMap<String, Integer> indices;
    WeightedQuickUnionUF groups;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are bidirectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {
        // TODO: Fix me
        if (SOLVED) {
            return res;
        }
        res = true;
        marked = new HashMap<>();
        indices = new HashMap<>();
        int nums = g.labels().size();
        groups = new WeightedQuickUnionUF(nums + 2);
        int index = 2;
        for (String label: g.labels()) {
            indices.put(label, index++);
            marked.put(label, false);
        }
        for (String label: g.labels()) {
            if (!marked.get(label)) {
                dfs(label, 0);
            }
        }
        for (String label: g.labels()) {
            for (String neighbor: g.neighbors(label)) {
                if (groups.connected(indices.get(label), indices.get(neighbor))) {
                    res = false;
                    SOLVED = true;
                    return res;
                }
            }
        }
        SOLVED = true;
        return res;
    }

    private void dfs(String label, int groupID) {
        marked.put(label, true);
        groups.union(groupID, indices.get(label));
        for (String neighbor: g.neighbors(label)) {
            if (!marked.get(neighbor)) {
                dfs(neighbor, (groupID + 1) % 2);
            }
        }
    }


    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
