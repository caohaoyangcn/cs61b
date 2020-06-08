package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int countOpened;
    // once the whole grid percolates, ufForPercolates is assigned to null.
    // 2 extra virtual sites: bottom and top
    private WeightedQuickUnionUF ufForPercolates;
    // 1 extra virtual site: top
    private WeightedQuickUnionUF ufForIsFull;
    private final int maxIndex;
    private boolean percolated;

    public Percolation(int N) {
        percolated = false;
        maxIndex = N;
        countOpened = 0;
        // special virtual site in ufForIsFull:
        // index 0: virtual top site
        ufForIsFull = new WeightedQuickUnionUF(N * N + 1);
        // special virtual site in ufForPercolates:
        // 1. index N * N + 1: virtual bottom site
        // 2. index 0: virtual top site
        ufForPercolates = new WeightedQuickUnionUF(N * N + 1 + 1);
        grid = new boolean[N][N];
        for (boolean[] row: grid) {
            for (boolean slot: row) {
                slot = false;
            }
        }
    }

    public void open(int row, int col) {
        // If any site in the bottom row is opened, connect it with virtual bottom site
        if (!percolated && row == maxIndex - 1) {
            ufForPercolates.union(maxIndex * maxIndex + 1, arrayIndexToUnionIndex(row, col));
        }
        grid[row][col] = true;
        countOpened += 1;
        openHelper(row, col);
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        return ufForIsFull.connected(0, arrayIndexToUnionIndex(row, col));
    }

    /**
     * A helper function that transform the {row, col} coordinate of a site
     * into the corresponding index in our UnionFind data structure.
     */
    private int arrayIndexToUnionIndex(int row, int col) {
        return 1 + row * maxIndex + col;
    }

    public int numberOfOpenSites() {
        return countOpened;
    }

    public boolean percolates() {
        if (percolated) {
            return true;
        }
        if (ufForPercolates.connected(maxIndex * maxIndex + 1, 0)){
            // once the whole grid percolates, ufForPercolates is assigned to null.
            percolated = true;
            ufForPercolates = null;
            return true;
        }
        return false;
    }

    private int[] upNeighbor(int row, int col) {
        return new int[]{row - 1, col};
    }
    private int[] downNeighbor(int row, int col) {
        return new int[]{row + 1, col};
    }
    private int[] leftNeighbor(int row, int col) {
        return new int[]{row, col - 1};
    }
    private int[] rightNeighbor(int row, int col) {
        return new int[]{row, col + 1};
    }
    private int[][] allNeighbors(int[] coordinate) {
        return new int[][] {upNeighbor(coordinate[0], coordinate[1]),
                            downNeighbor(coordinate[0], coordinate[1]),
                            leftNeighbor(coordinate[0], coordinate[1]),
                            rightNeighbor(coordinate[0], coordinate[1])};
    }

    /**
     * Handle connection updates resulted from opening a new site of coordinate {row, col}
     * If any neighbors of this new site are already opened, link them together in a disjoint set.
     * @param coordinate the location of newly opened site
     */
    private void updateConnection(int[] coordinate) {
        int[][] neighbors = allNeighbors(coordinate);
        for (int[] neighbor: neighbors) {
            try {
                    if (isOpen(neighbor[0], neighbor[1])) {
                        if (!percolated) {
                            ufForPercolates.union(arrayIndexToUnionIndex(coordinate[0], coordinate[1]),
                                    arrayIndexToUnionIndex(neighbor[0], neighbor[1]));
                        }
                        ufForIsFull.union(arrayIndexToUnionIndex(coordinate[0], coordinate[1]),
                                arrayIndexToUnionIndex(neighbor[0], neighbor[1]));
                    }
            }
            catch (IndexOutOfBoundsException ignored) {
            }
        }
    }

    /**
     * Connect opened sites next to each other in disjoint sets (via a helper function call)
     * If any site in the top row is opened, change it to a full site by connecting it with node 0
     */
    private void openHelper(int row, int col) {
        int[] coordinate = new int[] {row, col};
        if (row == 0) {
            if (!isFull(row, col)) {
                if (!percolated) {
                    ufForPercolates.union(arrayIndexToUnionIndex(row, col), 0);
                }
                ufForIsFull.union(arrayIndexToUnionIndex(row, col), 0);
            }
        }
        updateConnection(coordinate);
    }
}
