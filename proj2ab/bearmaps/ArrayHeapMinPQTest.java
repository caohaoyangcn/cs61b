package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.SortedMap;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testContains() {
        ArrayHeapMinPQ<String> arrHeapPQ = new ArrayHeapMinPQ();
        assertTrue(arrHeapPQ.isEmpty());
        arrHeapPQ.add("foo", 1);
        assertFalse(arrHeapPQ.isEmpty());
        arrHeapPQ.add("var", 3);
        arrHeapPQ.add("bar", 2);
        assertTrue(arrHeapPQ.contains("foo"));
        assertEquals("foo", arrHeapPQ.removeSmallest());
        assertFalse(arrHeapPQ.contains("foo"));
        arrHeapPQ.changePriority("var", 0);
        assertEquals("var", arrHeapPQ.removeSmallest());
        assertEquals("bar", arrHeapPQ.removeSmallest());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> arrHeapPQ = new ArrayHeapMinPQ();
        arrHeapPQ.add("foo", 1);
        arrHeapPQ.add("var", 3);
        arrHeapPQ.add("bar", 2);
        arrHeapPQ.changePriority("bar", 0);
        assertEquals("bar", arrHeapPQ.removeSmallest());
    }

    @Test
    public void randomInsertTest() {
        int testScale = 50000;
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<String> arrHeapPQ = new ArrayHeapMinPQ();
        String base = "var";
        for (int i = 0; i < testScale; i++) {
            arrHeapPQ.add(base + i, Math.random());
        }
        for (int i = 0; i < testScale; i++) {
            assertTrue(arrHeapPQ.contains(base + i));
        }
        arrHeapPQ.changePriority("var10", -1);
        assertEquals("var10", arrHeapPQ.removeSmallest());
        arrHeapPQ.changePriority("var20", -.5);
        assertEquals("var20", arrHeapPQ.removeSmallest());
        for (int i = 0; i < testScale - 2; i++) {
            arrHeapPQ.removeSmallest();
//            System.out.print(arrHeapPQ.removeSmallest() + " ");
//            if (i % 10 == 0) {
//                System.out.println();
//            }
        }
        for (int i = 0; i < testScale; i++) {
            assertFalse(arrHeapPQ.contains(base + i));
        }
        System.out.println("Time cost: " + sw.elapsedTime() + "s");

        Stopwatch sw1 = new Stopwatch();
        NaiveMinPQ<String> naiveMinPQ = new NaiveMinPQ<>();
        for (int i = 0; i < testScale; i++) {
            naiveMinPQ.add(base + i, Math.random());
        }
        for (int i = 0; i < testScale; i++) {
            assertTrue(naiveMinPQ.contains(base + i));
        }
        naiveMinPQ.changePriority("var10", -1);
        assertEquals("var10", naiveMinPQ.removeSmallest());
        naiveMinPQ.changePriority("var20", -.5);
        assertEquals("var20", naiveMinPQ.removeSmallest());
        for (int i = 0; i < testScale - 2; i++) {
            naiveMinPQ.removeSmallest();
//            System.out.print(naiveMinPQ.removeSmallest() + " ");
//            if (i % 10 == 0) {
//                System.out.println();
//            }
        }
        for (int i = 0; i < testScale; i++) {
            assertFalse(naiveMinPQ.contains(base + i));
        }
        System.out.println("Time cost: " + sw1.elapsedTime() + "s");

    }

}
