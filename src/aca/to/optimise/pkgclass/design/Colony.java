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
import java.util.Random;

/**
 * Holds a collection of ant objects and some of the methods to manipulate them.
 * 
 * @author Samuel
 */
public class Colony {


    private Ant[] ants;
    private Configuration config;
    private int[][] colonyOccupied;
    private Grid grid;

    /**
     * Empty Constructor
     */
    public Colony() {
    }

    /**
     * Constructs Colony using configuration settings from the configuration file
     *
     * @param config
     * @param grid
     */
    public Colony(Configuration config, Grid grid) {
        this.config = config;
        this.grid = grid;
        this.ants = new Ant[config.getAmmountOfAnts()];
        colonyOccupied = new int[config.getXLength()][config.getXLength()];

        for (int i = 0; i < config.getXLength(); i++) {
            for (int j = 0; j < config.getXLength(); j++) {
                colonyOccupied[i][j] = -1;
            } //End yaxis for
        } // End xaxis for

        scatterAnts(this.ants);
    }

    /**
     * Scatters the ants across the topic map
     *
     * @param ants
     */
    private void scatterAnts(Ant[] ants) {
        Random randomGenerator = new Random();
        int randomX;
        int randomY;

        for (int i = 0; i < ants.length; i++) {
            ants[i] = new Ant(this.grid, this.config);
        }

        for (int i = 0; i < ants.length; i++) {
            randomX = randomGenerator.nextInt(config.getXLength() - 1);
            randomY = randomGenerator.nextInt(config.getYLength() - 1);
            if (this.getOccupied(randomX, randomY) == -1) {

                if (debug == true) {
                    System.out.println("Ant " + i + " Set at X " + randomX + " random Y " + randomY);
                }

                this.setOccupied(randomX, randomY);
                ants[i].setPosition(randomX, randomY);
                // what document is there?
            } else {
                do {    // Program better way to chose next space

                    if (debug == true) {
                        System.out.println("Occupied X " + randomX + " Y " + randomY);
                    }

                    if (randomX == this.config.getXLength() - 1) {
                        randomY = randomY + 1;
                    } else {
                        randomX = randomX + 1;
                    }

                } while (this.getOccupied(randomX, randomY) != -1);
                colonyOccupied[randomX][randomY] = 1;
                ants[i].setPosition(randomX, randomY);

                if (debug == true) {
                    System.out.println("Ant " + i + " Set at X " + randomX + " random Y " + randomY);
                }
            }
        }
    }

    /**
     * Executes the ants moving across the map and picking up and dropping the
     * items.
     */
    public void sortAlgorithm() {

        for (int i = 0; i < config.getItterations(); i++) {
            Random randomGenerator = new Random();
            int chosen = Math.max(randomGenerator.nextInt(config.getAmmountOfAnts() - 1), 0);

            if (ants[chosen].getDocNum() != -1) {
                if (ants[chosen].drop() == true) {
                    // Addition: set memory
                    // System.out.println("Drop antNu: " + ants[chosen].getDocNum() + " ,Chosen " + chosen);
                }
            } else {
                ants[chosen].pick();
                //System.out.println("Pick" + ants[chosen].getDocNum() + " ,Chosen " + chosen);
            }

            int[] newCoords;
            newCoords = ants[chosen].move();
            //System.out.println("Move (SetCoords): X " + newCoords[0] + " ,Y "  + newCoords[1]);
            ants[chosen].setPosition(newCoords[0], newCoords[1]);
        }

        for (int i = 0; i < ants.length; i++) {
            if (ants[i].getDocNum() != -1) {
                ants[i].finalDrop(); // New drop fucntcion finalDrop
            }
        }
    }

    /**
     * Same as the sortAlgorithm but outputs extra information every X iterations
     * @param uMatrix 
     */
    public void sortAlgorithmOutput(UsesMatrix uMatrix) {

        ArrayList<ArrayList<Integer>> clusters = null;
        OrganiseClusters OClusters = new OrganiseClusters();

        for (int i = 0; i < config.getItterations(); i++) {
            Random randomGenerator = new Random();
            int chosen = Math.max(randomGenerator.nextInt(config.getAmmountOfAnts() - 1), 0);

            if (ants[chosen].getDocNum() != -1) {
                if (ants[chosen].drop() == true) {
                    // Addition: set memory
                    // System.out.println("Drop antNu: " + ants[chosen].getDocNum() + " ,Chosen " + chosen);
                }
            } else {
                ants[chosen].pick();
                //System.out.println("Pick" + ants[chosen].getDocNum() + " ,Chosen " + chosen);
            }

            int[] newCoords;
            newCoords = ants[chosen].move();
            //System.out.println("Move (SetCoords): X " + newCoords[0] + " ,Y "  + newCoords[1]);
            ants[chosen].setPosition(newCoords[0], newCoords[1]);

            // Want to prin number of clusters and coupling metric
            if (i % 1000000 == 0) {
                clusters = OClusters.cluster(grid, config);

                int ammountOfClusters;
                ammountOfClusters = clusters.size();

                FormatOutput.appendTo("ammountOfClusters", ammountOfClusters);

                double couplingMetric = 0;
                couplingMetric = MetricEvaluation.coupling(clusters, uMatrix);
                System.out.println("Coupling: " + couplingMetric + " , Number of clusters: " + ammountOfClusters);

                FormatOutput.appendTo("couplingMetric", couplingMetric);
            }
        }

        for (int i = 0; i < ants.length; i++) {
            if (ants[i].getDocNum() != -1) {
                ants[i].finalDrop(); // New drop fucntcion finalDrop
            }
        }

    }

    /**
     * Gets if a space is occupied by an Ant
     *
     * @param xaxis
     * @param yaxis
     * @return
     */
    public int getOccupied(int xaxis, int yaxis) {
        return colonyOccupied[xaxis][yaxis];
    }

    /**
     * Sets a space occupied by an ant.
     *
     * @param x
     * @param y
     */
    public void setOccupied(int x, int y) {
        this.colonyOccupied[x][y] = 1;
    }
}
