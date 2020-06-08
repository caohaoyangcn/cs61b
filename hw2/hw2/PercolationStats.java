package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    private final int N;
    private final int T;
    private double[] thresholds;
    private double m;
    private double deviation;
    private double cfdLow;
    private double cfdHigh;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0) {
            throw new IllegalArgumentException("N must be positive, but got: " + N);
        }
        if (T < 0) {
            throw new IllegalArgumentException("T must be positive, but got: " + T);
        }
        this.N = N;
        this.T = T;
        cfdHigh = 0;
        cfdLow = 0;
        m = 0;
        deviation = 0;
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation newExperiment = pf.make(N);
            while (!newExperiment.percolates()) {
                int [] coordinate = randomXYGenerator();
                while (newExperiment.isOpen(coordinate[0], coordinate[1])) {
                    coordinate = randomXYGenerator();
                }
                newExperiment.open(coordinate[0], coordinate[1]);
            }
            thresholds[i] = (double) newExperiment.numberOfOpenSites() / (N * N);
        }
        m = StdStats.mean(thresholds);
        deviation = StdStats.stddev(thresholds);
    }

    private int[] randomXYGenerator() {
        int val = StdRandom.uniform(0, N * N - 1);
        int col = val % N;
        int row = val / N;
        return new int[] {row, col};
    }

    public double mean() {
        return m;
    }

    public double stddev() {
        return deviation;
    }

    public double confidenceLow() {
        if (cfdLow != 0) {
            return cfdLow;
        }
        cfdLow = m - 1.96 * deviation / Math.sqrt(T);
        return cfdLow;
    }

    public double confidenceHigh() {
        if (cfdHigh != 0) {
            return cfdHigh;
        }
        cfdHigh = m + 1.96 * deviation / Math.sqrt(T);
        return cfdHigh;
    }
}
