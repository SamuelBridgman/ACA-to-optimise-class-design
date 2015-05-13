package ACA.to.optimise.pgkclass.desin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import aca.to.optimise.pkgclass.design.UsesMatrix;
import aca.to.optimise.pkgclass.design.MetricEvaluation;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit tests for the metric evaluation 
 * 
 * @author Samuel
 */
public class MetricEvaluationTests {

    @Test
    public void testCoulingFunctionalitySixDocsTwoClustersEightInClassUsesNoOutOfClass() {
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
        
        ArrayList<Integer> clusterOne = new ArrayList<>();
        clusterOne.add(0);
        clusterOne.add(1);
        clusterOne.add(2);
        clusters.add(clusterOne);
        
        ArrayList<Integer> clusterTwo = new ArrayList<>();
        clusterTwo.add(3);
        clusterTwo.add(4);
        clusterTwo.add(5);
        clusters.add(clusterTwo);
        
        
        int[][] matrix = 
       {{0, 1, 0, 0, 0, 0},
        {1, 0, 1, 0, 0, 0},
        {0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1},
        {0, 0, 0, 1, 0, 0},
        {0, 0, 0, 1, 0, 0}};

        UsesMatrix usesMatrix = new UsesMatrix(matrix);
        
        double couplingMetric = MetricEvaluation.coupling(clusters, usesMatrix);
        assertEquals(8,couplingMetric, 0);
    }
    
    @Test
    public void testCoulingFunctionalitySixDocsTwoClustersEightInClassUsesTwoOut() {
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
        
        ArrayList<Integer> clusterOne = new ArrayList<>();
        clusterOne.add(0);
        clusterOne.add(1);
        clusterOne.add(2);
        clusters.add(clusterOne);
        
        ArrayList<Integer> clusterTwo = new ArrayList<>();
        clusterTwo.add(3);
        clusterTwo.add(4);
        clusterTwo.add(5);
        clusters.add(clusterTwo);
        
        
        int[][] matrix = 
       {{0, 1, 0, 0, 1, 1},
        {1, 0, 1, 0, 0, 0},
        {0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1},
        {1, 0, 0, 1, 0, 0},
        {1, 0, 0, 1, 0, 0}};

        UsesMatrix usesMatrix = new UsesMatrix(matrix);
        
        double couplingMetric = MetricEvaluation.coupling(clusters, usesMatrix);
        assertEquals(0.33,couplingMetric, 0.01);
    }
}
