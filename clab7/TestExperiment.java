import org.junit.Test;
import static org.junit.Assert.*;

public class TestExperiment {
    @Test
    public void test() {
//        double x = ExperimentHelper.optimalAverageDepth(5000);
//        assertEquals(2.5, ExperimentHelper.optimalAverageDepth(10), 0.01);
//        assertEquals(2.5, ExperimentHelper.optimalIPL(10), 0.01);

        BST<Integer> tree = new BST<>();
        tree.add(4);
        tree.add(2);
        tree.add(6);
        tree.add(3);
        tree.add(5);
        tree.add(1);
        tree.add(7);
        tree.add(8);
        assertEquals((double) 13/ 8, tree.avgTreeDepthRecursive(), 0.01);
        assertEquals((double) 13/ 8, tree.avgTreeDepth(), 0.01);
        tree.deleteTakingRandom(8);
        assertEquals((double) 10/ 7, tree.avgTreeDepthRecursive(), 0.01);
        assertEquals((double) 10/ 7, tree.avgTreeDepth(), 0.01);
        tree.deleteTakingRandom(4);
        for (int i = 10; i < 20; i++) {
            tree.add(i);
            tree.deleteTakingRandom(tree.getRandomKey());
            double x = tree.avgTreeDepth();
            double y = tree.avgTreeDepthRecursive();
            assertEquals(y, x, 0.01);
        }
    }
}
