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
import java.lang.reflect.Array;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade to pull all the elements together and define batch functionality or
 * special output.
 * @author Samuel
 */
public class Main {

    // Number of methods in input file
    public static int methods;
    // Number of attributes in inut file 
    public static int attributes;
    // Print's all debug infomration when true
    public static boolean debug = true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        // Initalise files
        Configuration config = new Configuration();
        OrganiseClusters OClusters = new OrganiseClusters();
        ReadInFile read = new ReadInFile();
        List<String> lines = null;
        ArrayList<ArrayList<Integer>> clusters = null;

        // Prameters for this run
        // Number of runs of the algorithm for batch functionality
        int itteration = 1;

        // Read in file
        lines = read.readTextFile("CBSUseMatrix.txt"); 

        // Formatt input
        int[][] intMatrix = read.stripToIntArrayOfArray(lines);

        // Set up uses table
        UsesMatrix uMatrix = new UsesMatrix(intMatrix);

        // LOOP
        int x = 0;
        while (x < itteration) {

            // Iitalise grid
            Grid grid = new Grid(config, intMatrix, uMatrix);

            // Initalise colony
            Colony colony = new Colony(config, grid);

            System.out.println("-------------------------- BEFORE -------------------------- ");
            System.out.println(grid.toString());
            
            // Run the normal algorithm
            colony.sortAlgorithm();
            
            // Run the algorithm with extra output every X itterations.
            //colony.sortAlgorithmOutput(uMatrix);

            System.out.println("-------------------------- AFTER -------------------------- ");
            System.out.println(grid.toString());

            // Cluster objects
            clusters = OClusters.cluster(grid, config);

            if (debug == true) {
                for (int i = 0; i < clusters.size(); i++) {
                    System.out.println(clusters.get(i));

                }
            }

            // Compute coupling metric
            double couplingMetric = 0;
            couplingMetric = MetricEvaluation.coupling(clusters, uMatrix);
            
            // Compute ammount of clusters
            int ammountOfClusters;
            ammountOfClusters = clusters.size();

            if (debug == true) {
                System.out.println("Coupling: " + couplingMetric);
            }
                        
            //  Write to file
            String name = FormatOutput.createClasses(clusters, methods, attributes);
            FormatOutput.coupling(name, couplingMetric, ammountOfClusters);

            x++;
        }//END LOOP

    }
}
