/*  
    Copyright (C) 2004 Samuel Bridgman - 10014913
    Email: samuel.bridgman@hotmail.co.uk

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package aca.to.optimise.pkgclass.design;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to read in a file and format 
 * 
 * @author Samuel
 */
public class ReadInFile {
    
    /**
     * Used to make matrix from read in List 
     * @param lines
     * @return 
     */
    public int[][] stripToIntArrayOfArray(List<String> lines) {

        int column = 0, countTwo = 0, row = 0, size = 10;
        String[] list = new String[3];

        List<String> returnList = new ArrayList<>();

        if (!lines.isEmpty()) {                 // get number of columns
            for (String lineToken : lines) {
               if (lineToken.length() > size) { // Used to strip the optimum from example files
                    column++;
                }
            }
        }

        String[] a = new String[column];

        if (!lines.isEmpty()) {                 // Strip all empty line and extra numbers
            for (String lineToken : lines) {
                if (lineToken.length() > size) {
                    returnList.add(lineToken);
                    a[countTwo] = lineToken;        // convert to string array
                    countTwo++;
                }else{
                    for (int i = 0; i < lineToken.length(); i++) {
                        list = lineToken.split(" ");
                        Main.methods = parseInt(list[0]); // could have passed this as last in index of array then stripped off on other side?
                        Main.attributes = parseInt(list[1]);
                    }
                }
            }
        }

        row = a[0].length();
        char[][] charMatrix = new char[column][row];

        for (int i = 0; i < a.length; i++) {        // char array of array
            for (int j = 0; j < row; j++) {
                charMatrix[i][j] = a[i].charAt(j);
            }
        }

        int[][] intMatrix = new int[column][row];             // convert to int array of array

        for (int i = 0; i < charMatrix.length; i++) {
            for (int j = 0; j < charMatrix[i].length; j++) {
                // Ascii 48 - 0, 49 - 1
                intMatrix[i][j] = charMatrix[i][j] - 48;         // PROBLEM charMatrix return ascii, prog better way
            }
        }
        return intMatrix;
    }

    /**
     * Reads in text file
     * @param aFileName
     * @return 
     */
    public List<String> readTextFile(String aFileName) {
        List<String> lines = null;
        try {
            Path path = Paths.get(aFileName);
            lines = Files.readAllLines(path, UTF_8);
        } catch (IOException e) {
            System.out.println(e);
        }
        return lines;
    }
}
