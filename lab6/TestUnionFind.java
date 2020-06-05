import org.junit.Test;
import static org.junit.Assert.*;


public class TestUnionFind {

    @Test
    public void test1() {
        UnionFind uf = new UnionFind(16);
        assertEquals(16, uf.size);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(6, 4);
        uf.union(0, 1);
        uf.union(2, 3);
        assertEquals(5, uf.find(6));
        uf.union(7, 1);
        assertTrue(uf.connected(0, 6));
        assertFalse(uf.connected(2, 1)); // end of phase 1

        assertEquals(6, uf.sizeOf(0));
        uf.union(8, 9);
        uf.union(2, 8);
        uf.union(1, 9);
        assertEquals(10, uf.sizeOf(6));
        assertTrue(uf.connected(3, 4));
    }
}
