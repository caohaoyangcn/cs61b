package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Haoyang Cao
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    HashMap<Point, Node> point2NodeMapping;
    KDTree kdt;
    MyTrieSet trie;
    HashMap<String, LinkedList<Node>> name2NodesMapping;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        List<Point> pts = new ArrayList<>();
        point2NodeMapping = new HashMap<>();
        name2NodesMapping = new HashMap<>();
        trie = new MyTrieSet();
        for (Node node: nodes) {
            if (node.name() != null) {
                String name = cleanString(node.name());
                trie.add(name);
                if (name2NodesMapping.containsKey(name)) {
                    name2NodesMapping.get(name).add(node);
                }
                else {
                    LinkedList<Node> xs = new LinkedList<>();
                    xs.add(node);
                    name2NodesMapping.put(name, xs);
                }
            }
            if (neighbors(node.id()).isEmpty()) {
                continue;
            }
            Point fromNode = node2point(node);
            pts.add(fromNode);
            point2NodeMapping.put(fromNode, node);
        }
        kdt = new KDTree(pts);
    }

    private Point node2point(Node toConvert) {
        return new Point(toConvert.lon(), toConvert.lat());
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return point2NodeMapping.get(kdt.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return trie.keysWithPrefix(cleanString(prefix));
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        LinkedList<Node> xs = name2NodesMapping.get(cleanString(locationName));
        ArrayList<Map<String, Object>> toReturn = new ArrayList<>();
        for (Node node: xs) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("lat", node.lat());
            map.put("lon", node.lon());
            map.put("name", node.name());
            map.put("id", node.id());
            toReturn.add(map);
        }
        return toReturn;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
