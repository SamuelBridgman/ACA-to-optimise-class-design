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

/**
 * Ant agent that picks up and drops documents.
 * 
 * @author Samuel
 */
public class Ant {

    private int xAxis;
    private int yAxis;
    private int docNum;
    private int speed;
    private int failAttempts;

    private Grid grid;
    private Configuration config;

    private int fail = 0;

    /**
     * Constructor
     * 
     * @param grid
     * @param config 
     */
    public Ant(Grid grid, Configuration config) {
        this.config = config;
        this.grid = grid;
        this.xAxis = 0;
        this.yAxis = 0;
        this.docNum = -1;
        this.failAttempts = config.getAntFailAttempts();
    }

    /**
     * Sets position of an Ant using X and Y coordinates
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.xAxis = x;
        this.yAxis = y;
    }

    /**
     * Returns the load of the ant if its carrying one else -1
     *
     * @return
     */
    public int getDocNum() {
        return docNum;
    }

    /**
     * Compute pickup-probability given the density value
     *
     * @param f the local density and similarity f
     * @return the resulting probability Taken from J. Handle
     */
    private double pPick(double f) {
       // if (f != 0) {
            double p = (this.config.getkp() / (this.config.getkp() + f));
            return p * p;
       // } else {
       //     return 0;
       // }
    }

    /**
     * Compute drop-probability given the density value
     *
     * @param f the local density and similarity f
     * @return the resulting probability Taken from J. Handle
     */
    private double pDrop(double f) {
        if (f != 0) {
            double p = (f / (this.config.getkd() + f));
            return p * p;
        } else {
            return 0;
        }
    }

    /**
     * Tries to drop the item the at is holding
     * 
     * @return
     */
    public boolean drop() {
        double densityFunction = grid.computeDesityFunction(this.docNum, this.xAxis, this.yAxis, this.speed);

        double dPP = pDrop(densityFunction);

        if ((fail == failAttempts) || Math.random() < dPP) {             // Check if failed to drop more than 100 times and drop %
            if (this.grid.isFree(this.xAxis, this.yAxis)) {     // check space is free
                
                if (debug == true) {
                System.out.println("Doc " + this.docNum);
                }
                
                grid.setOccupied(this.xAxis, this.yAxis, this.docNum);                     // set the grid occupied by placing the 
                // doc number onto that squar of the grid
                grid.returnDocument(this.docNum).setPosition(this.xAxis, this.yAxis);      // set the document position
                this.docNum = -1;                                                          // update the ant documnets 
                this.fail = 0;                                                             // coords and failed counter
                // TODO: update memory 

                if (debug == true) {
                System.out.println("Drop: X " + this.xAxis + " ,Y" + this.yAxis + ",DocNu " + this.docNum); // DEBUG
                }
                
                return true;
            } else {
                int[] newCoord = grid.nextFree(this.xAxis, this.yAxis);         // Find next free spot
                grid.setOccupied(newCoord[0], newCoord[1], this.docNum);        // Set space occupied on the grid
                grid.returnDocument(this.docNum).setPosition(newCoord[0], newCoord[1]); //Set the ocument's coords
                this.docNum = -1;   // Set the ant to no document
                this.fail = 0;      // Reset the fail counter

                if (debug == true) {
                System.out.println("Drop (New): X " + newCoord[0] + " ,Y" + newCoord[1] + ",DocNu " + this.docNum); // DEBUG
                }
                
                return true;
            }
        }
        this.fail++;
        return false;
    }
    
    /**
     * Used to force the ants to drop an item if they are holding it.
     */
    public void finalDrop(){
                if (this.grid.isFree(this.xAxis, this.yAxis)) {     // check space is free
                
                if (debug == true) {
                System.out.println("Doc " + this.docNum);
                }
                
                grid.setOccupied(this.xAxis, this.yAxis, this.docNum);                     // set the grid occupied by placing the 
                // doc number onto that squar of the grid
                grid.returnDocument(this.docNum).setPosition(this.xAxis, this.yAxis);      // set the document position
                this.docNum = -1;                                                          // update the ant documnets 
                this.fail = 0;                                                             // coords and failed counter
                // TODO: update memory 

                if (debug == true) {
                System.out.println("Drop: X " + this.xAxis + " ,Y" + this.yAxis + ",DocNu " + this.docNum); // DEBUG
                }
                
            } else {
                int[] newCoord = grid.nextFree(this.xAxis, this.yAxis);         // Find next free spot
                grid.setOccupied(newCoord[0], newCoord[1], this.docNum);        // Set space occupied on the grid
                grid.returnDocument(this.docNum).setPosition(newCoord[0], newCoord[1]); //Set the ocument's coords
                this.docNum = -1;   // Set the ant to no document
                this.fail = 0;      // Reset the fail counter

                if (debug == true) {
                System.out.println("Drop (New): X " + newCoord[0] + " ,Y" + newCoord[1] + ",DocNu " + this.docNum); // DEBUG
                }
                

            }
    }

    /**
     * Tries to pick up a document if there is one in the same space as the ant
     * 
     * @return
     */
    public boolean pick() {

        if (!grid.isFree(xAxis, yAxis)) {                               // Check there is a document
            double f = grid.computeDesityFunction(grid.getDocument(xAxis, yAxis), xAxis, yAxis, speed); // Compute density
            if (Math.random() < pPick(f)) {                     // Random does ant pick up the doc
                this.docNum = grid.getDocument(xAxis, yAxis);   // Update ant has doc
                grid.setFree(xAxis, yAxis);                     // Update grid space 
                //grid.returnDocument(grid.getDocument(xAxis, yAxis)).setPosition(-1, -1); // TESTING
                // TODO: MEMORY
                return true;
            }
        }
        return false;
    }
    
    /**
     * Moves the ant
     * 
     * @return 
     */
    public int[] move() {
        //System.out.println("Move: X " + this.xAxis + " ,Y " + this.yAxis);
        return grid.move(this.xAxis, this.yAxis, this.speed);
    }
}
