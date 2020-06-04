package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        assertEquals(10, arb.capacity());
        assertTrue(arb.isEmpty());
        arb.enqueue(10);
        arb.enqueue(12);
        arb.enqueue(14);
        assertEquals(3, arb.fillCount());
        int val = arb.dequeue();
        assertEquals(10, val);
        assertEquals(2, arb.fillCount());
        assertEquals(12, arb.peek().intValue());
        for (int i = 0; i < 8; i++) {
            arb.enqueue(i);
        }
        assertEquals(10, arb.fillCount());
//      arb.enqueue(1);
//        for (Integer i: arb) {
//            System.out.println(i.intValue());
//        }
    }

    @Test
    public void  testEquals() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        for (int i = 0; i < 8; i++) {
            arb.enqueue(i);
        }

        ArrayRingBuffer<Integer> arb1 = new ArrayRingBuffer(10);
        for (int i = 0; i < 8; i++) {
            arb1.enqueue(i);
        }

        assertEquals(arb1, arb);
        assertEquals(0, arb.peek().intValue());
        arb1.dequeue();
        assertEquals(1, arb1.peek().intValue());

        for (Integer i: arb) {
            for (Integer j: arb) {
                System.out.println("i: " + i + ", j: " + j);
            }
        }

    }
}
