import java.util.ArrayDeque;

public class UnionFind {

    int[] vertices;
    int size;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        vertices = new int[n];
        size = n;
        for (int i = 0; i < size; i++) {
            vertices[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= size) {
            throw new IllegalArgumentException("Not a valid index: " + vertex);
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        int returnSize = 0;
        int root = find(v1);
        for (int i = 0; i < size; i++) {
            if (find(i) == root) {
                returnSize += 1;
            }
        }
        return returnSize;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return vertices[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // No extra validating, since sizeOf already does this for us
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            vertices[v1] = v2;
            return;
        }
        int s1 = sizeOf(v1);
        int s2 = sizeOf(v2);

        // connecting v2's root (root2) to root1
        if (s1 > s2) {
            vertices[root2] = root1;
            vertices[root1] = -(s1 + s2);
        }
        // connecting v1's root (roo1) to root2
        else {
            vertices[root1] = root2;
            vertices[root2] = -(s1 + s2);
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        int currNode = vertex;
        ArrayDeque<Integer> path = new ArrayDeque<>();
        while (vertices[currNode] > 0) {
            path.add(currNode);
            currNode = parent(currNode);
        }
        for (Integer i: path) {
            vertices[i] = currNode;
        }
        return currNode;
    }

}
