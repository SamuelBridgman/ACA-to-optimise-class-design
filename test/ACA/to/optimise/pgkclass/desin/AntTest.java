package ACA.to.optimise.pgkclass.desin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import aca.to.optimise.pkgclass.design.Colony;
import aca.to.optimise.pkgclass.design.Grid;
import aca.to.optimise.pkgclass.design.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for the ant class
 * @author Samuel
 */
public class AntTest {
    
    Configuration config = new Configuration();
    Grid grid = new Grid();
    Colony colony;

    
    
    public AntTest(){
        
    }
    
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
    
    public void callConstructor() {
        this.colony = new Colony(this.config, this.grid);
    }

    @Test
    public void testColonyConstructorNoDocs() {
        this.config.setAmmountOfAnts(0);
        callConstructor();
        assertEquals(-1, colony.getOccupied(1, 1));
        assertEquals(-1, colony.getOccupied(config.getXLength() - 1, config.getYLength() - 1));
    }
    
    @Test
    public void testColonyConstructorWith10Ants() {                
        this.config.setAmmountOfAnts(10);
        callConstructor();
    }
    
    @Test
    public void testAntDrop(){
        
    }
    
    @Test
    public void testAntMove(){
        
    }
    
    @Test
    public void testAntPickUp(){
        
    }
    
    @Test
    public void testAntFinalDrop(){
        
    }
    
}
