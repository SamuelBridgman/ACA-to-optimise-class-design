package ACA.to.optimise.pgkclass.desin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import aca.to.optimise.pkgclass.design.Configuration;
import aca.to.optimise.pkgclass.design.Documents;
import aca.to.optimise.pkgclass.design.Grid;
import aca.to.optimise.pkgclass.design.UsesMatrix;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.text.Document;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * unit tests for the grid class
 * 
 * @author Samuel
 */
public class GridTests {

    Configuration config = new Configuration();
    ArrayList<Documents> docs = new ArrayList<Documents>();
    UsesMatrix userMatrix = new UsesMatrix();
    Grid grid;

    public GridTests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public void callConstructor() {
        // Tried setting these so if they changed in config the test will still work
        // thrws error on two tests if i set them instead of them being 10 in config
//        this.config.setXLength(10);
//        this.config.setYLength(10);
        this.config.setAmmountOfAnts(10);
        this.config.setItterations(1000);
        this.grid = new Grid(this.config, this.docs, this.userMatrix);
    }

    public void addXDocuments(int x) {
        for (int i = 0; i < x; i++) {
            Documents doc = new Documents();
            this.docs.add(doc);
        }
    }

    @Test
    public void testGridConstructorNoDocs() {
        callConstructor();
        assertEquals(-1, grid.getOccupied(1, 1));
        assertEquals(-1, grid.getOccupied(config.getXLength() - 1, config.getYLength() - 1));
    }

    /**
     * Requires config to be set to x = 10 and y = 10. Should this happen in
     * here to set up a test config?
     */
    @Test
    public void testScatterDocsViaGridConstructorWith100Docs() {
        addXDocuments(100);
        callConstructor();

        assertThat(grid.getOccupied(1, 1), not(-1));
        assertThat(grid.getOccupied(config.getXLength() - 1, config.getYLength() - 1), not(-1));
    }

    // Also tests getOccupied, not good practce as test should only test 1 bit of functionality
    // but IsFree uses getOccupied other than testing getOccuped by itself I dont know how to isolate
    // IsFree.
    @Test
    public void testIsFree() {
        addXDocuments(100);
        callConstructor();
        assertEquals(false, grid.isFree(0, 0));
    }

    @Test
    public void testReturnDocument() {
        Documents newDoc = new Documents();
        newDoc.setPayLoad("TestDocument");
        docs.add(newDoc);
        callConstructor();
        assertEquals("TestDocument", this.grid.returnDocument(0).getPayLoad());
    }

    @Test
    public void testNextFreeNoProblems() {

        setUp3By3GridWithDocsOnlyLeavingOneOneFree();

        int[] coords = new int[2];
        coords[0] = 0;
        coords[1] = 0;
        Assert.assertArrayEquals(coords, this.grid.nextFree(1, 1));
    }

    @Test
    public void testNextFreeNextToXAxis() {

        setUp3By3GridWithDocsOnlyLeavingOneOneFree();

        int[] coords = new int[2];
        coords[0] = 0;
        coords[1] = 0;
        Assert.assertArrayEquals(coords, this.grid.nextFree(0, 1));
    }

    @Test
    public void testNextFreeNextToYAxis() {

        setUp3By3GridWithDocsOnlyLeavingOneOneFree();

        int[] coords = new int[2];
        coords[0] = 0;
        coords[1] = 0;
        Assert.assertArrayEquals(coords, this.grid.nextFree(1, 0));
    }

    @Test
    public void testNextFreeNextToXAndYAxis() {

        setUp3By3GridWithDocsOnlyLeavingOneOneFree();

        int[] coords = new int[2];
        coords[0] = 0;
        coords[1] = 0;
        Assert.assertArrayEquals(coords, this.grid.nextFree(2, 2));
    }

    private void setUp3By3GridWithDocsOnlyLeavingOneOneFree() {
        this.config.setXLength(3);
        this.config.setYLength(3);

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
        Documents docTwoThree = new Documents();
        docTwoThree.setId(4);
        docs.add(docTwoThree);
        Documents docThreeOne = new Documents();
        docThreeOne.setId(5);
        docs.add(docThreeOne);
        Documents docThreeTwo = new Documents();
        docThreeTwo.setId(6);
        docs.add(docThreeTwo);
        Documents docThreeThree = new Documents();
        docThreeThree.setId(7);
        docs.add(docThreeThree);

        callConstructor();

        this.docs.get(0).setPosition(1, 2);
        this.grid.setOccupied(0, 1, 0);
        this.docs.get(1).setPosition(1, 3);
        this.grid.setOccupied(0, 2, 1);
        this.docs.get(2).setPosition(2, 1);
        this.grid.setOccupied(1, 0, 2);
        this.docs.get(3).setPosition(2, 2);
        this.grid.setOccupied(1, 1, 3);
        this.docs.get(4).setPosition(2, 3);
        this.grid.setOccupied(1, 2, 4);
        this.docs.get(5).setPosition(3, 1);
        this.grid.setOccupied(2, 0, 5);
        this.docs.get(6).setPosition(3, 2);
        this.grid.setOccupied(2, 1, 6);
        this.docs.get(7).setPosition(3, 3);
        this.grid.setOccupied(2, 2, 7);
        this.grid.setFree(0, 0);
    }

    @Test
    public void testMoveXAndYAxis() {
        callConstructor();
        boolean result = true;
        boolean desiredsResult = true;
        int[] newCoords;
        newCoords = this.grid.move(1, 1, 0);
        
        if(newCoords[0] != 1 || newCoords[1] != 1){
            result = true;
        }else{
            result = false;
        }

        assertTrue("Error testing random move functionality, retry.", result);
    }

    // TEST computeDensityFunction
    @Test
    public void testcomputeDensityFunctionAllItemUsesEightPopulated() {
        this.config.setXLength(3);
        this.config.setYLength(3);

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
        Documents docTwoThree = new Documents();
        docTwoThree.setId(4);
        docs.add(docTwoThree);
        Documents docThreeOne = new Documents();
        docThreeOne.setId(5);
        docs.add(docThreeOne);
        Documents docThreeTwo = new Documents();
        docThreeTwo.setId(6);
        docs.add(docThreeTwo);
        Documents docThreeThree = new Documents();
        docThreeThree.setId(7);
        docs.add(docThreeThree);
        Documents docOneOne = new Documents();
        docOneOne.setId(8);
        docs.add(docOneOne);

        int[][] oneMatrix = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1}};

        UsesMatrix mx = new UsesMatrix(oneMatrix);

        this.userMatrix = mx;

        callConstructor();

        this.docs.get(0).setPosition(1, 2);
        this.grid.setOccupied(0, 1, 0);
        this.docs.get(1).setPosition(1, 3);
        this.grid.setOccupied(0, 2, 1);
        this.docs.get(2).setPosition(2, 1);
        this.grid.setOccupied(1, 0, 2);
        this.docs.get(3).setPosition(2, 2);
        this.grid.setOccupied(1, 1, 3);
        this.docs.get(4).setPosition(2, 3);
        this.grid.setOccupied(1, 2, 4);
        this.docs.get(5).setPosition(3, 1);
        this.grid.setOccupied(2, 0, 5);
        this.docs.get(6).setPosition(3, 2);
        this.grid.setOccupied(2, 1, 6);
        this.docs.get(7).setPosition(3, 3);
        this.grid.setOccupied(2, 2, 7);
        this.docs.get(8).setPosition(1, 1);
        this.grid.setOccupied(0, 0, 8);

        double value = 0;
        
        value = this.grid.computeDesityFunction(3, 2, 2, 0);

        //  1 - 0 = 1, 1 - 1 = 0
        //sum += (1 - usesMatrix.get(load, documentOccupied[ih][jh]));
        assertEquals(1, value, 0);
    }
    
        // TEST computeDensityFunction
    @Test
    public void testcomputeDensityFunctionOneItemUsesZero() {
        this.config.setXLength(3);
        this.config.setYLength(3);


        Documents docTwoTwo = new Documents();
        docTwoTwo.setId(3);
        docs.add(docTwoTwo);


        int[][] oneMatrix = {{0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}};

        UsesMatrix mx = new UsesMatrix(oneMatrix);

        this.userMatrix = mx;

        callConstructor();

        this.docs.get(0).setPosition(2, 2);
        this.grid.setOccupied(1, 1, 0);

        double value = 0;

        value = this.grid.computeDesityFunction(3, 2, 2, 0);

        assertEquals(0, value, 0);
    }
    
        @Test
    public void testTorusFunctionality() {
        
    }
    
        @Test
    public void testAllItemsRemainOnGridAfterRunningOfTheAlgorithm() {
        
    
    }

    // TODO; test set free
    // TEST setOccupied
    // TEST getDocment
    // TEST isfree
    // TEST move
}
