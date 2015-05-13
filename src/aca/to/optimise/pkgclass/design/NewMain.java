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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class built to flip input matrix
 * 
 * @author Samuel
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> l = readTextFile("GDPUseMatrix.txt");
        int q = 0, total = l.size();
        //System.out.println(l.get(0).length());
        for (int i = ((l.get(0).length() + 1) / 2); i > 0; i--) {
            for (int j = 0; j < total; j++) {
                //System.out.println(i*2-2);
                //System.out.println(l.get(j).charAt(i * 2 - 2));
                appendTo("new", l.get(j).charAt(i * 2 - 2)+ " ");
            }
            //System.out.println(i + "Before \n");
            appendTo("new", "\n");
        }
    }

    public static List<String> readTextFile(String aFileName) {
        List<String> lines = null;
        try {
            Path path = Paths.get(aFileName);
            lines = Files.readAllLines(path, UTF_8);
        } catch (IOException e) {
            System.out.println(e);
        }
        return lines;
    }

    static void appendTo(String fileName, String data) {
        BufferedWriter writer = null;

        try {
            //create a temporary file
            File logFile = new File(fileName + ".txt");

            // This will output the full path where the file will be written to...
            //System.out.println(logFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(data);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();

            } catch (Exception e) {
            }
        }
    }
}
