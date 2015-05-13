package ACA.to.optimise.pgkclass.desin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import aca.to.optimise.pkgclass.design.Configuration;
import aca.to.optimise.pkgclass.design.Documents;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * unit tests for the document class
 * 
 * @author Samuel
 */
public class DocumentTest {
    
    Configuration config = new Configuration();

    @Test
    public void testXDistance() {
        Documents docOne = new Documents();
        Documents docTwo = new Documents();
        
        docOne.setPosition(0, 0);
        docTwo.setPosition(5, 0);
        
        this.config.setNumOfDocs(2);
        
        assertEquals(5, (int)docOne.distance(docTwo));
    }

    @Test
    public void testYDistance() {
        Documents docOne = new Documents();
        Documents docTwo = new Documents();
        docOne.setPosition(0, 0);
        docTwo.setPosition(0, 5);
        int i = (int)docOne.distance(docTwo);
        assertEquals(5, i);
    }

    @Test
    public void testSameXAndYDistance() {
        Documents docOne = new Documents();
        Documents docTwo = new Documents();
        docOne.setPosition(0, 0);
        docTwo.setPosition(4, 4);
        int i = (int)docOne.distance(docTwo);
        assertEquals(4, i);
    }
    
    @Test
    public void testMixXAndYDistance() {
        Documents docOne = new Documents();
        Documents docTwo = new Documents();
        docOne.setPosition(0, 0);
        docTwo.setPosition(6, 2);
        int i = (int)docOne.distance(docTwo);
        assertEquals(6, i);
    }
    
    @Test
    public void initaliseDocuments(){
        
        Documents docs = new Documents();

        String[] names = new String[5]; 
        names[0] = "Kettle";
        names[1] = "Water";
        names[2] = "Toaster";
        names[3] = "Bread";
        names[4] = "Power";
        
        int[][] m = new int[5][5];       
        int[] kettle = {0,1,0,0,1}; 
        m[0] = kettle;
        int[] water = {1,0,0,0,0}; 
        m[1] = water;
        int[] toaster = {0,0,0,1,1};
        m[2] = toaster;
        int[] bread = {0,0,1,0,0};
        m[3] = bread;
        int[] power = {1,0,1,0,0};
        m[4] = power;

        ArrayList<Documents> listOfDocs;
        listOfDocs = docs.initaliseDocuments(m, names);

        assertEquals(listOfDocs.get(0).getId(), 0);
        assertEquals(listOfDocs.get(0).getPayLoad(), "Kettle");
        
        assertEquals(listOfDocs.get(4).getId(), 4);
        assertEquals(listOfDocs.get(4).getPayLoad(), "Power");
    }
}
