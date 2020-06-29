package bearmaps.hw4;
import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private
    SolverOutcome outcome;
    ArrayHeapMinPQ<Vertex> vertices;
    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, Vertex> edgeTo;
    ArrayList<Vertex> solution;
    double solutionWeight;
    int numStatesExplored;
    double explorationTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        solutionWeight = 0;
        solution = new ArrayList<>();
        distTo = new HashMap<>();
        distTo.put(start, (double) 0);
        vertices = new ArrayHeapMinPQ<>();
        vertices.add(start, 0);
        numStatesExplored = 0;
        edgeTo = new HashMap<>();
        Stopwatch sw = new Stopwatch();
        while (vertices.size() > 0 && !vertices.getSmallest().equals(end)) {
            if (sw.elapsedTime() / 1000 > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                explorationTime = timeout;
                return;
            }
            Vertex p = vertices.removeSmallest();
            numStatesExplored++;
            for (WeightedEdge<Vertex> e: input.neighbors(p)) {
                Vertex v = e.to();
                if (!distTo.containsKey(v)) {
                    distTo.put(v, distTo.get(e.from()) + e.weight());
                    edgeTo.put(v, p);
                    vertices.add(v, distTo.get(v) + input.estimatedDistanceToGoal(v, end));
                }
                else {
                    double routeLen = distTo.get(e.from()) + e.weight();
                    if (distTo.get(v) > routeLen) {
                        distTo.put(v, routeLen);
                        edgeTo.put(v, p);
                        if (vertices.contains(v)) {
                            vertices.changePriority(v, distTo.get(v) + input.estimatedDistanceToGoal(v, end));
                        }
                        else {
                            vertices.add(v, distTo.get(v) + input.estimatedDistanceToGoal(v, end));
                        }
                    }
                }
            }
        }
        if (!vertices.isEmpty() &&vertices.getSmallest().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            Vertex v = end;
            solution.add(end);
            while (!v.equals(start)) {
                Vertex prev = edgeTo.get(v);
                solution.add(prev);
                v = prev;
            }
            Collections.reverse(solution);
            solutionWeight = distTo.get(end);
        }
        else {
            outcome = SolverOutcome.UNSOLVABLE;
        }
        explorationTime = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
