import org.junit.Test;
import static org.junit.Assert.*;


public class ArrayDequeTest {

    @Test
    public void TestSuite1() {
        ArrayDeque<Integer> arrDeque1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> arrDeque2;
        assertTrue(arrDeque1.isEmpty());
        arrDeque1.addFirst(1);
        arrDeque1.addLast(3);
        arrDeque1.addLast(5);
        arrDeque1.printDeque();
        for (int i = 7; i <= 37; ) {
            arrDeque1.addFirst(i);
            i += 2;
        }

        /* test copy constructor */
        arrDeque2 = new ArrayDeque<Integer>(arrDeque1);
        int currSize = arrDeque1.size();
        for (int i = 0; i < currSize; i++) {
            int val = arrDeque1.removeFirst();
            System.out.println(Integer.toString(val) + " was popped out from the front");
        }
        assertEquals(19, arrDeque2.size());
        System.out.print("arrDeque2: ");
        arrDeque2.printDeque();

        /* fill in arrDeque1 again */
        assertTrue(arrDeque1.isEmpty());
        for (int i = 7; i <= 37; ) {
            arrDeque1.addFirst(i);
            i += 2;
        }
        assertEquals(16, arrDeque1.size());
        assertEquals(29, arrDeque1.get(4).intValue());
        assertEquals(37, arrDeque1.removeFirst().intValue());
        assertEquals(7, arrDeque1.removeLast().intValue());
        arrDeque1.printDeque();
        assertEquals(14, arrDeque1.size());
    }
}
