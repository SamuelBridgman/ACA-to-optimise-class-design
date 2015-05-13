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

import static aca.to.optimise.pkgclass.design.Main.debug;
import java.util.ArrayList;

/**
 * Class to hold the evaluation of any metrics
 * @author Samuel
 */
public class MetricEvaluation {

    /**
     * Facade method to hold all the metric evaluation 
     * @param clusters
     * @param usesMatrix 
     */
    public void MetricEvaluation(ArrayList<ArrayList<Integer>> clusters, UsesMatrix usesMatrix) {
        double coupling;

        coupling = coupling(clusters, usesMatrix);

    }

    /**
     * Evaluates coupling
     * @param clusters
     * @param usesMatrix
     * @return coupling metric
     */
    public static double coupling(ArrayList<ArrayList<Integer>> clusters, UsesMatrix usesMatrix) {

        int inClassUses = 0, outOfClassUses = 0;
        ArrayList<Integer> currentCluster = new ArrayList<>();

        // Loop each item in matrix
        for (int i = 0; i < usesMatrix.getSize(); i++) {

            // Identfy what cluster the item being looked at is in 
            // Loop clusters
            for (int j = 0; j < clusters.size(); j++) {
                // Loop items in cluster
                for (int k = 0; k < clusters.get(j).size(); k++) {
                    // If item in cluster is the same as the item being assesed 
                    if (clusters.get(j).get(k) == i) // Add cluster as one containing item being checked
                    {
                        currentCluster = clusters.get(j);
                    }
                }
            }

            // Loop each object, as syymetricl matrix
            for (int j = 0; j < usesMatrix.getSizeSecondDimension(); j++) {
                // Is the uses matrix true
                if (usesMatrix.get(i, j) == 1) {
                    // If in same cluster add to in class 
                    if (currentCluster.contains(j)) {
                        inClassUses++;
                    } else {
                        // Else out of class
                        outOfClassUses++;
                    }
                }

            }

        }

        if (outOfClassUses == 0) {
            if (debug == true) {
                System.out.println("In: " + inClassUses + " ,Out: " + outOfClassUses);
            }

            return inClassUses;
        }
        double div = inClassUses + outOfClassUses;
        double retVal = outOfClassUses / div;
        if (debug == true) {
            System.out.println("In: " + inClassUses + " ,Out: " + outOfClassUses + " ,retVal: " + retVal);
        }
        return retVal;
    }
}
