import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private int res;

    // use array sorting and 1 PQ, O(N * logN)
    public FlightSolver(ArrayList<Flight> flights) {
        res = 0;
        flights.sort(new flightComparatorStart());
        PriorityQueue<Flight> pqEndTime = new PriorityQueue<>(new flightComparatorEnd());
        int record = 0;
        for (int i = 0; i < flights.size(); i++) {
            pqEndTime.add(flights.get(i));
            while (pqEndTime.peek().endTime < flights.get(i).startTime) {
                record -= pqEndTime.poll().passengers;
            }
            record += flights.get(i).passengers;
            if (record > res) {
                res = record;
                }
        }
    }

    // use 2 PQ, O(N * logN)
//    public FlightSolver(ArrayList<Flight> flights) {
//        res = 0;
//        PriorityQueue<Flight> pqStartTime = new PriorityQueue<>(new flightComparatorStart());
//        PriorityQueue<Flight> pqEndTime = new PriorityQueue<>(new flightComparatorEnd());
//        for (Flight flt: flights) {
//            pqEndTime.add(flt);
//            pqStartTime.add(flt);
//        }
//        int record = 0;
//        while (!pqStartTime.isEmpty()) {
//            if (pqStartTime.peek().startTime <= pqEndTime.peek().endTime) {
//                record += pqStartTime.poll().passengers;
//            }
//            else {
//                record -= pqEndTime.poll().passengers;
//            }
//            if (record > res) {
//                res = record;
//            }
//        }
//    }

    private static class flightComparatorEnd implements Comparator<Flight> {
        public int compare(Flight a, Flight b) {
            if (a.endTime > b.endTime) {
                return 1;
            }
            else if (a.endTime < b.endTime) {
                return -1;
            }
            return 0;
        }
    }
    private static class flightComparatorStart implements Comparator<Flight> {
        public int compare(Flight a, Flight b) {
            if (a.startTime > b.startTime) {
                return 1;
            }
            else if (a.startTime < b.startTime) {
                return -1;
            }
            return 0;
        }
    }

    public int solve() {
        return res;
    }

}
