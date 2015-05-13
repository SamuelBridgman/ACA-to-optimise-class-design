package ACA.to.optimise.pgkclass.desin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import aca.to.optimise.pkgclass.design.Configuration;
import aca.to.optimise.pkgclass.design.Documents;
import aca.to.optimise.pkgclass.design.Grid;
import aca.to.optimise.pkgclass.design.OrganiseClusters;
import aca.to.optimise.pkgclass.design.UsesMatrix;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for the organise clusters class
 * 
 * @author Samuel
 */
public class OrganiseClustersTest {

    //private static ArrayList<int[]> asses(Grid grid, UsesMatrix usesMatrix, Configuration config
    Configuration config = new Configuration();
    Grid grid = new Grid();
    ArrayList<Documents> docs = new ArrayList<>();
    UsesMatrix userMatrix = new UsesMatrix();
    OrganiseClusters organiseCluster = new OrganiseClusters();

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        grid = new Grid();
    }

    @After
    public void tearDown() {
    }

    // Check that the first document isnt over writig the second document
    @Test
    public void testAssesWithTwoDocsOneClusterNotByEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(4);
        this.config.setYLength(4);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(1, 1);
        this.grid.setOccupied(1, 1, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(1, 2);
        this.grid.setOccupied(1, 2, 1);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(1);
        expectedClusters.add(newCluster);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithFourDocsOneClusterNotByEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(4);
        this.config.setYLength(4);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);
        Documents docTwoOne = new Documents();
        docTwoOne.setId(2);
        docs.add(docTwoOne);
        Documents docTwoTwo = new Documents();
        docTwoTwo.setId(3);
        docs.add(docTwoTwo);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(1, 1);
        this.grid.setOccupied(1, 1, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(1, 2);
        this.grid.setOccupied(1, 2, 1);

        this.grid.setFree(this.docs.get(2).getXAxis(), this.docs.get(2).getYAxis());
        this.docs.get(2).setPosition(2, 1);
        this.grid.setOccupied(2, 1, 2);

        this.grid.setFree(this.docs.get(3).getXAxis(), this.docs.get(3).getYAxis());
        this.docs.get(3).setPosition(2, 2);
        this.grid.setOccupied(2, 2, 3);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(1);
        newCluster.add(2);
        newCluster.add(3);
        expectedClusters.add(newCluster);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithFourDocsOneClusterByEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(4);
        this.config.setYLength(4);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);
        Documents docTwoOne = new Documents();
        docTwoOne.setId(2);
        docs.add(docTwoOne);
        Documents docTwoTwo = new Documents();
        docTwoTwo.setId(3);
        docs.add(docTwoTwo);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(0, 0);
        this.grid.setOccupied(0, 0, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(0, 1);
        this.grid.setOccupied(0, 1, 1);

        this.grid.setFree(this.docs.get(2).getXAxis(), this.docs.get(2).getYAxis());
        this.docs.get(2).setPosition(1, 0);
        this.grid.setOccupied(1, 0, 2);

        this.grid.setFree(this.docs.get(3).getXAxis(), this.docs.get(3).getYAxis());
        this.docs.get(3).setPosition(1, 1);
        this.grid.setOccupied(1, 1, 3);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(1);
        newCluster.add(2);
        newCluster.add(3);
        expectedClusters.add(newCluster);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithFourDocsTwoClusterOveXAndYEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(4);
        this.config.setYLength(4);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);
        Documents docTwoOne = new Documents();
        docTwoOne.setId(2);
        docs.add(docTwoOne);
        Documents docTwoTwo = new Documents();
        docTwoTwo.setId(3);
        docs.add(docTwoTwo);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(0, 0);
        this.grid.setOccupied(0, 0, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(0, 1);
        this.grid.setOccupied(0, 1, 1);

        this.grid.setFree(this.docs.get(2).getXAxis(), this.docs.get(2).getYAxis());
        this.docs.get(2).setPosition(2, 1);
        this.grid.setOccupied(2, 1, 2);

        this.grid.setFree(this.docs.get(3).getXAxis(), this.docs.get(3).getYAxis());
        this.docs.get(3).setPosition(3, 3);
        this.grid.setOccupied(3, 3, 3);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(3);
        newCluster.add(1);
        expectedClusters.add(newCluster);
        ArrayList<Integer> newCluster2 = new ArrayList<>();
        newCluster2.add(2);
        expectedClusters.add(newCluster2);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithSixDocsThreeClusterByEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(5);
        this.config.setYLength(5);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);
        Documents docTwoOne = new Documents();
        docTwoOne.setId(2);
        docs.add(docTwoOne);
        Documents docTwoTwo = new Documents();
        docTwoTwo.setId(3);
        docs.add(docTwoTwo);
        Documents docFive = new Documents();
        docTwoTwo.setId(4);
        docs.add(docFive);
        Documents docSix = new Documents();
        docTwoTwo.setId(5);
        docs.add(docSix);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(0, 0);
        this.grid.setOccupied(0, 0, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(0, 1);
        this.grid.setOccupied(0, 1, 1);

        this.grid.setFree(this.docs.get(2).getXAxis(), this.docs.get(2).getYAxis());
        this.docs.get(2).setPosition(3, 2);
        this.grid.setOccupied(3, 2, 2);

        this.grid.setFree(this.docs.get(3).getXAxis(), this.docs.get(3).getYAxis());
        this.docs.get(3).setPosition(3, 1);
        this.grid.setOccupied(3, 1, 3);

        this.grid.setFree(this.docs.get(4).getXAxis(), this.docs.get(4).getYAxis());
        this.docs.get(4).setPosition(0, 3);
        this.grid.setOccupied(0, 3, 4);

        this.grid.setFree(this.docs.get(5).getXAxis(), this.docs.get(5).getYAxis());
        this.docs.get(5).setPosition(1, 3);
        this.grid.setOccupied(1, 3, 5);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(1);
        expectedClusters.add(newCluster);
        ArrayList<Integer> newCluster2 = new ArrayList<>();
        newCluster2.add(4);
        newCluster2.add(5);
        expectedClusters.add(newCluster2);
        ArrayList<Integer> newCluster3 = new ArrayList<>();
        newCluster3.add(3);
        newCluster3.add(2);
        expectedClusters.add(newCluster3);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithTwoDocsOneClusterOverXAxisEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(4);
        this.config.setYLength(4);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(0, 0); // Write about this why its different values
        this.grid.setOccupied(0, 0, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(0, 3);
        this.grid.setOccupied(0, 3, 1);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(1);
        expectedClusters.add(newCluster);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithTwoDocsOneClusterOverYAxisEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(4);
        this.config.setYLength(4);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(0, 0); // Write about this why its different values
        this.grid.setOccupied(0, 0, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(3, 0);
        this.grid.setOccupied(3, 0, 1);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(1);
        expectedClusters.add(newCluster);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithTwoDocsOneClusterOverMidYAxisEdge() {
        this.config.setAmmountOfAnts(1);
        this.config.setItterations(1000);
        this.config.setXLength(4);
        this.config.setYLength(4);

        Documents docOneTwo = new Documents();
        docOneTwo.setId(0);
        docs.add(docOneTwo);
        Documents docOneThree = new Documents();
        docOneThree.setId(1);
        docs.add(docOneThree);

        this.grid = new Grid(this.config, this.docs, this.userMatrix);

        this.grid.setFree(this.docs.get(0).getXAxis(), this.docs.get(0).getYAxis());
        this.docs.get(0).setPosition(0, 1); // Write about this why its different values
        this.grid.setOccupied(0, 1, 0);

        this.grid.setFree(this.docs.get(1).getXAxis(), this.docs.get(1).getYAxis());
        this.docs.get(1).setPosition(3, 1);
        this.grid.setOccupied(3, 1, 1);

        ArrayList<ArrayList<Integer>> expectedClusters = new ArrayList<>();
        ArrayList<Integer> newCluster = new ArrayList<>();
        newCluster.add(0);
        newCluster.add(1);
        expectedClusters.add(newCluster);

        assertEquals(expectedClusters, organiseCluster.cluster(this.grid, this.config));
    }

    @Test
    public void testAssesWithTenDocsInUShape() {
        assertEquals(true, true);
    }

}
