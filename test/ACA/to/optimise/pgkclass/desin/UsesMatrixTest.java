package ACA.to.optimise.pgkclass.desin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import aca.to.optimise.pkgclass.design.UsesMatrix;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit tests for the uses matric class
 * 
 * @author Samuel
 */
public class UsesMatrixTest {

    private UsesMatrix uM;

    @Test
    public void TestMatrix() {
        setupMatrix();

        assertEquals(uM.getSize(), 5);
    }

    /**
     * Returns 1 if there is a relationship, -1 if there is not a relationship.
     */
    @Test
    public void TestGet() {
        setupMatrix();

        assertEquals(-1, uM.get(0, 0));
        assertEquals(1, uM.get(2, 3));
        assertEquals(1, uM.get(3, 2));
        assertEquals(-1, uM.get(4, 4));
    }

    private void setupMatrix() {

        int[][] m = new int[5][5];

        int[] kettle = {0, 1, 0, 0, 1};
        m[0] = kettle;
        int[] water = {1, 0, 0, 0, 0};
        m[1] = water;
        int[] toaster = {0, 0, 0, 1, 1};
        m[2] = toaster;
        int[] bread = {0, 0, 1, 0, 0};
        m[3] = bread;
        int[] power = {1, 0, 1, 0, 0};
        m[4] = power;

        uM = new UsesMatrix(m);
    }

}
