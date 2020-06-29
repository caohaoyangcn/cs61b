import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        int pathSum = 0;
        int n = 1;
        while (n <= N) {
            pathSum += (int) Math.floor(Math.log10(n) / Math.log10(2));
            n++;
        }
        return pathSum;
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        int n = 1;
        double depthSum = 0;
        while (n <= N) {
            depthSum += Math.floor(Math.log10(n) / Math.log10(2));
            n++;
        }
        return depthSum / N;
    }

    public static void randomDelete(BST<Double> tree) {
        tree.deleteTakingRandom(tree.getRandomKey());
    }

    public static void takeSuccessorDelete(BST<Double> tree) {
        tree.deleteTakingSuccessor(tree.getRandomKey());
    }

    public static void randomInsert(BST<Double> tree, double min, double max) {
        tree.add(StdRandom.uniform(min, max));
    }
}
