import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hug.
 */

public class Experiments {

    public static void experiment1() {
        BST<Double> tree = new BST();
        List<Integer> numbers = new ArrayList<>();
        List<Double> avgDepths = new ArrayList<>();
        List<Double> optimalAvgDepths = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            tree.add(StdRandom.uniform());
            numbers.add(i);
            avgDepths.add(tree.avgTreeDepth());
            optimalAvgDepths.add(ExperimentHelper.optimalAverageDepth(i));
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("average depth of random BST", numbers, avgDepths);
        chart.addSeries("average depth of optimal BST", numbers, optimalAvgDepths);

        new SwingWrapper(chart).displayChart();

    }

    public static void experiment2() {
        BST<Double> tree = new BST<>();
        List<Integer> Ms = new ArrayList<>();
        List<Double> avgDepths = new ArrayList<>();
        List<Double> avgRecursive = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ExperimentHelper.randomInsert(tree, -10, 10);
        }

        for (int i = 1; i <= 10000; i++) {
            ExperimentHelper.takeSuccessorDelete(tree);
            Ms.add(i);
            ExperimentHelper.randomInsert(tree, -10, 10);
            double d = tree.avgTreeDepth();
            avgDepths.add(d);
            avgRecursive.add(tree.avgTreeDepthRecursive());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("average depth of BST", Ms, avgDepths);
        chart.addSeries("average depth of BST Recursive", Ms, avgRecursive);
        new SwingWrapper(chart).displayChart();

    }

    public static void experiment3() {
        BST<Double> tree = new BST<>();
        List<Integer> Ms = new ArrayList<>();
        List<Double> avgDepths = new ArrayList<>();
        List<Double> avgRecursive = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ExperimentHelper.randomInsert(tree, -10, 10);
        }

        for (int i = 1; i <= 10000; i++) {
            ExperimentHelper.randomDelete(tree);
            Ms.add(i);
            ExperimentHelper.randomInsert(tree, -10, 10);
            double d = tree.avgTreeDepth();
            avgDepths.add(d);
            avgRecursive.add(tree.avgTreeDepthRecursive());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("average depth of BST", Ms, avgDepths);
        chart.addSeries("average depth of BST Recursive", Ms, avgRecursive);
        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
//        experiment1();
        experiment2();
        experiment3();
    }
}
