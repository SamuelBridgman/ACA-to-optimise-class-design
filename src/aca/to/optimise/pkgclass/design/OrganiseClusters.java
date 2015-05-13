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

import java.util.ArrayList;

/**
 * Organises the documents n the grid into clusters
 * @author Samuel
 */
public class OrganiseClusters {

    /**
     * organise clusters on the grid
     * 
     * @param grid
     * @param config
     * @return 
     */
    public ArrayList<ArrayList<Integer>> cluster(Grid grid, Configuration config) {
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
        ArrayList<Integer> inCluster = new ArrayList<>();
        ArrayList<Integer> openList = new ArrayList<>();
        int xSize = config.getXLength();
        int ySize = config.getYLength();
        Documents currentDoc = new Documents();

        // LOOP through grid
        for (int i = 0; i <= config.getXLength() - 1; i++) {
            for (int j = 0; j <= config.getYLength() - 1; j++) {
                alreadyInCluster:

                // check grid cell
                if (grid.getOccupied(i, j) != -1) {

                    //check if already in cluster
                    for (int k = 0; k < clusters.size(); k++) {
                        if (clusters.get(k).contains(grid.getOccupied(i, j))) {
                            break alreadyInCluster;
                        }
                    }

                    //else cluster the fots
                    openList.add(grid.getOccupied(i, j));

                    while (!openList.isEmpty()) {
                        currentDoc = grid.returnDocument(openList.get(0));
                        // Make neighbour hood grid +1/+1 tp -1/-1
                        int xHigh = currentDoc.getXAxis() + 1;
                        int xLow = currentDoc.getXAxis() - 1;
                        int yHigh = currentDoc.getYAxis() + 1;
                        int yLow = currentDoc.getYAxis() - 1;

                        int ih, jh, sum = 0;

                        // Loop through neighbourhood
                        for (int x = xLow; x < xHigh + 1; x++) {
                            for (int y = yLow; y < yHigh + 1; y++) {

                                ih = x;
                                jh = y;
                                
                                if (x < 0) {    // This is making the loop jump out
                                    ih = config.getXLength() - 1;
                                }
                                if (y < 0) {    
                                    jh = config.getYLength() - 1;
                                }

                                // Implimenting torus on edges
                                if (jh < 0) {
                                    jh = xSize + jh % xSize;
                                }
                                if (jh >= xSize) {
                                    jh = jh % xSize;
                                }
                                if (ih < 0) {
                                    ih = ySize + ih % ySize;
                                }
                                if (ih >= ySize) {
                                    ih = ih % ySize;
                                }

                                if (grid.getOccupied(ih, jh) != -1 &&
                                        grid.getOccupied(ih, jh) != currentDoc.getId() &&
                                        !inCluster.contains(grid.getOccupied(ih, jh)) &&
                                        !openList.contains(grid.getOccupied(ih, jh))) {
                                    openList.add(grid.getOccupied(ih, jh));
                                }

                            }
                        }
                        inCluster.add(openList.get(0)); // Add to inCluster
                        openList.remove(openList.get(0));    // Remove from open list
                    }
                    clusters.add(inCluster);
                    inCluster = new ArrayList<>();
                    openList.clear();
                }

            }
        }

        return clusters;
    }

    /**
     * Organises objects on grid into clusters
     *
     * @param grid
     * @param config
     * @return
     */
    public ArrayList<ArrayList<Integer>> oldCluster(Grid grid, Configuration config) {

        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
        int gridSize = config.getXLength() * config.getYLength();

        int xSize = config.getXLength();
        int ySize = config.getYLength();

        int gridIndex = 0;
        boolean added = false;

        // LOOP through grid
        for (int i = 0; i <= config.getXLength() - 1; i++) {
            for (int j = 0; j <= config.getYLength() - 1; j++) {

                if (grid.getOccupied(i, j) != -1) {
                    int xHigh = i + 1;
                    int xLow = i - 1;
                    int yHigh = j + 1;
                    int yLow = j - 1;

                    int ih, jh, sum = 0;
                    added = false;

                    // Loop through neighbourhood
                    neighbourhoodLoop:
                    for (int x = xLow; x < xHigh; x++) {
                        for (int y = yLow; y < yHigh; y++) {

                            if (x < 0) {
                                x = config.getXLength() - 1;
                            }
                            if (y < 0) {
                                y = config.getYLength() - 1;
                            }

                            ih = x;
                            jh = y;

                            // Implimenting torus on edges
                            if (jh < 0) {
                                jh = xSize + jh % xSize;
                            }
                            if (jh >= xSize) {
                                jh = jh % xSize;
                            }
                            if (ih < 0) {
                                ih = ySize + ih % ySize;
                            }
                            if (ih >= ySize) {
                                ih = ih % ySize;
                            }

                            // How do I add neighbours to an array?
                            // see if neighbour empty
                            if (grid.getOccupied(ih, jh) != -1) {

                                // Loop each cluster
                                for (int k = 0; k < clusters.size(); k++) {

                                    // If item's not in cluster
                                    if (clusters.get(k).contains(grid.getOccupied(ih, jh))) {
                                        clusters.get(k).add(grid.getOccupied(i, j));
                                        added = true;
                                        break neighbourhoodLoop;
                                    }
                                }
                            }
                        }
                    }
                    if (added == false) {
                        ArrayList<Integer> add = new ArrayList<>();
                        add.add(grid.getOccupied(i, j));
                        clusters.add(add);
                    }
                    gridIndex++;
                }
            }

        }
        return clusters;
    }
}
