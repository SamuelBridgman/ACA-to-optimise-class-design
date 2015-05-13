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
 *  Holds the matrix of where the documents are
 * 
 * @author Samuel
 */
public class Grid {

    private Configuration config;
    private ArrayList<Documents> documents;
    private int[][] documentOccupied;
    private UsesMatrix usesMatrix;
    private Documents doc;

    /**
     * Empty Constructor
     */
    public Grid() {
    }

    /**
     * Constructor
     * 
     * @param config
     * @param matrix
     * @param usesM 
     */
    public Grid(Configuration config, int[][] matrix, UsesMatrix usesM) {
        this.usesMatrix = usesM;
        this.config = config;
        Documents doc = new Documents();
        ArrayList<Documents> docs = doc.initaliseDocuments(matrix);

        this.documents = docs;
        documentOccupied = new int[config.getXLength()][config.getXLength()];

        // Initalise all grid spaces to be -1 empty
        for (int i = 0; i < config.getXLength(); i++) {
            for (int j = 0; j < config.getXLength(); j++) {
                documentOccupied[i][j] = -1;
            } //End yaxis for
        } // End xaxis for

        scatterDocuments(this.documents);
    }

    /**
     * Initialises grid using configuration file and calls scatterDocuments() on the
     * documents passed to it. All grid spaces initialised to -1 empty
     *
     * @param config
     * @param docs
     * @param usesM
     */
    public Grid(Configuration config, ArrayList<Documents> docs, UsesMatrix usesM) {
        this.usesMatrix = usesM;
        this.config = config;
        this.documents = docs;
        documentOccupied = new int[config.getXLength()][config.getXLength()];

        // Initalise all grid spaces to be -1 empty
        for (int i = 0; i < config.getXLength(); i++) {
            for (int j = 0; j < config.getXLength(); j++) {
                documentOccupied[i][j] = -1;
            } //End yaxis for
        } // End xaxis for

        scatterDocuments(this.documents);
    }

    /**
     * Scatters the documents on the map.
     *
     * @param docs
     */
    private void scatterDocuments(ArrayList<Documents> docs) {
        Random randomGenerator = new Random();
        int randomX;// = randomGenerator.nextInt(config.getXLength()-1);
        int randomY;// = randomGenerator.nextInt(config.getYLength()-1);   

        for (int i = 0; i < docs.size(); i++) {

            while (true) {

                randomX = randomGenerator.nextInt(config.getXLength());
                randomY = randomGenerator.nextInt(config.getYLength());

                if (this.getOccupied(randomX, randomY) == -1) {

                    if (debug == true) {
                        System.out.println("Document " + docs.get(i).getId() + " Payload " + docs.get(i).getPayLoad() + " Set at X " + randomX + " random Y " + randomY);
                    }

                    // Set space occupied
                    // Id set in similarityDistanceMatrix
                    documentOccupied[randomX][randomY] = i;

                    // Set position for the doc
                    docs.get(i).setPosition(randomX, randomY);
                    // what document is there?
                    break;
                }

                // Work out document matrix
            }
        }
    }

    /**
     * Note: This was copied from J. Handle's work with ACA
     *
     * @param load
     * @param xAxis
     * @param yAxis
     * @param speed
     * @return
     */
    public double computeDesityFunction(int load, int xAxis, int yAxis, int speed) {
        int xSize = config.getXLength();
        int ySize = config.getYLength();

        // Work out neighbourhood
        int xHigh = xAxis + this.config.getSigma();
        int xLow = xAxis - this.config.getSigma();
        int yHigh = yAxis + this.config.getSigma();
        int yLow = yAxis - this.config.getSigma();

        int ih, jh, count = 0;
        double sum = 0;

        // Loop through neighbourhood
        for (int i = xLow; i < xHigh + 1; i++) {
            for (int j = yLow; j < yHigh + 1; j++) {

                ih = i;
                jh = j;

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

                // Check all documents in area(not equal -1 so not empty)
                // And not the load currently being considerd for droping
                if ((documentOccupied[ih][jh] != -1) && (documentOccupied[ih][jh] != load)) {

                    // NOTE: Adition for dynamic ants
                    // make selection speed-dependent 
                    // double div = 1 + 1*((speed - 1)/config.getSpeed());
                    // Compute density similarity, 
                    if (usesMatrix.get(load, documentOccupied[ih][jh]) > 0) {
                        sum += 1 * 0.125;
                    }//else{
                    //sum += 1 * 0.125; // Would - 0.125 for disimilar item    
                    //}

                }
            }
        }
        return sum;
    }

    /**
     * Returns id of a space if its occupied by a document. TODO: will return -1
     * if space is not occupied, where should this be dealt with?
     *
     * @param xaxis
     * @param yaxis
     * @return
     */
    public int getOccupied(int xAxis, int yAxis) {
        return documentOccupied[xAxis][yAxis];
    }

    /**
     *
     * @param xAxis
     * @param yAxis
     * @return boolean if the space is free
     */
    public boolean isFree(int xAxis, int yAxis) {

        if (getOccupied(xAxis, yAxis) == -1) {
            return true;
        };
        return false;
    }

    /**
     * Sets a spaces occupied by placing the doc number into the grid
     *
     * @param x
     * @param y
     */
    public void setOccupied(int x, int y, int DocNum) {
        this.documentOccupied[x][y] = DocNum;
    }

    /**
     * Set a grid cell free taking in X and Y coordinates
     * @param x
     * @param y
     */
    public void setFree(int x, int y) {
        this.documentOccupied[x][y] = -1;
    }

    /**
     * return document using index
     * @param i
     * @return
     */
    public Documents returnDocument(int i) {
        return this.documents.get(i);
    }

    /**
     *get document using x and y coordinates
     * @param x
     * @param y
     * @return
     */
    public int getDocument(int x, int y) {
        return this.documentOccupied[x][y];
    }


    /**
     * returns nextFree space
     *
     * @param xAxis
     * @param yAxis
     * @return
     */
    public int[] nextFree(int xAxis, int yAxis) {
        int[] returnCoOrds = new int[2];
        int newX = 0, newY = 0, step = 1;

        int xSize = this.config.getXLength();
        int ySize = this.config.getYLength();

        while (true) {

            for (int i = 0; i < 10; i++) {

                // Copied from J.Handle
                // make a step with current radius
                int xpart = (int) Math.round(step * Math.random());
                int ypart = step - xpart;

                if (Math.random() < 0.5) {
                    xpart = -xpart;
                }
                if (Math.random() < 0.5) {
                    ypart = -ypart;
                }

                int x = xAxis + xpart;
                int y = yAxis + ypart;

                if (x < 0) {
                    x = xSize + x % xSize;
                }
                if (x >= xSize) {
                    x = x % xSize;
                }
                if (y < 0) {
                    y = ySize + y % ySize;
                }
                if (y >= ySize) {
                    y = y % ySize;
                }

                // Check if new coords are free if so return
                if (this.isFree(x, y)) {
                    returnCoOrds[0] = x;
                    returnCoOrds[1] = y;
                    return returnCoOrds;
                }
//                newX = xAxis;
//                newY = yAxis;
            }

            step++;
        }
    }

    /**
     * Creates random new coordinates for the ant to move to
     * @param xAxis
     * @param yAxis
     * @param speed
     * @return 
     */
    public int[] move(int xAxis, int yAxis, int speed) {

        int xSize = this.config.getXLength();
        int ySize = this.config.getYLength();

        int xPart = (int) Math.round(1 * Math.random());
        int yPart = speed - xPart;

        // Copied fom J. Handle
        // take shortest way: ants are walking on a torus!
        if (Math.random() < 0.5) {
            xPart = -xPart;
        }
        if (Math.random() < 0.5) {
            yPart = -yPart;
        }

        // compute new position
        int x = xAxis + xPart;
        int y = yAxis + yPart;

        // keep on torus
        if (x < 0) {
            x = xSize + x % xSize;
        }
        if (x >= xSize) {
            x = x % xSize;
        }
        if (y < 0) {
            y = ySize + y % ySize;
        }
        if (y >= ySize) {
            y = y % ySize;
        }

        int[] newCoords = new int[2];
        newCoords[0] = x;
        newCoords[1] = y;

        return newCoords;
    }

    /**
     * Outputs a string value of the grid
     * @return 
     */
    public String toString() {
        String retString = "";
        for (int i = 0; i < documentOccupied.length; i++) {
            for (int j = 0; j < this.documentOccupied[i].length; j++) {
                if (this.documentOccupied[i][j] != - 1) {
                    retString = retString + " ";
                }
                retString = retString + this.documentOccupied[i][j] + ",";
            }
            retString = retString + "\n";
        }
        return retString;
    }
}
