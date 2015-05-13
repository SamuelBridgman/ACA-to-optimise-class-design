package ACA.to.optimise.pgkclass.desin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import aca.to.optimise.pkgclass.design.ReadInFile;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit tests for the read in file class
 * 
 * @author Samuel
 */
public class ReadInFileTest {

    ReadInFile read = new ReadInFile();

    private List<String> initaliseList() {
        List<String> strList;
        strList = new ArrayList<>();
        strList.add("00000000001");
        strList.add("00000000001");
        strList.add("00000000000");
        return strList;
    }

    private int[][] initaliseIntArray() {
        int[][] array = new int[3][11];
        array[0][0] = 0;
        array[0][1] = 0;
        array[0][2] = 0;
        array[0][3] = 0;
        array[0][4] = 0;
        array[0][5] = 0;
        array[0][6] = 0;
        array[0][7] = 0;
        array[0][8] = 0;
        array[0][9] = 0;
        array[0][10] = 1;
        array[1][0] = 0;
        array[1][1] = 0;
        array[1][2] = 0;
        array[1][3] = 0;
        array[1][4] = 0;
        array[1][5] = 0;
        array[1][6] = 0;
        array[1][7] = 0;
        array[1][8] = 0;
        array[1][9] = 0;
        array[1][10] = 1;
        array[2][0] = 0;
        array[2][1] = 0;
        array[2][3] = 0;
        array[2][4] = 0;
        array[2][5] = 0;
        array[2][6] = 0;
        array[2][7] = 0;
        array[2][8] = 0;
        array[2][9] = 0;
        array[2][10] = 0;
        return array;
    }

    @Test
    public void testReadInText() {

        List<String> list = initaliseList();
        int[][] testMatrix;
        testMatrix = read.stripToIntArrayOfArray(list);
        int[][] matrix;
        matrix = initaliseIntArray();

        Assert.assertArrayEquals(matrix, testMatrix);
    }
}
