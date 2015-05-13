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

import java.io.Serializable;

/**
 * Holds parameter details for the running of the algorithm
 * 
 * @author Samuel
 */
public class Configuration implements Serializable {

    /**
     * Grid parameters
     */
    private int xLength = 10;
    private int yLength = 10;
    private int itterations = 100000000;
    private int sigma = 1;
    private double kd = 0.15;
    private double kp = 0.15;
    

    /**
     * Ant parameters
     */
    private int ammountOfAnts = 30;
    private int speed = 50;
    private int failAttempts = 100;

    /**
     * Document parameters
     */
    private int numOfDocs = 0;

    /*
     Initalisation
     */
    public Configuration() {
    }

    public int getXLength() {
        return this.xLength;
    }

    public void setXLength(int xLength) {
        this.xLength = xLength;
    }

    public int getYLength() {
        return this.yLength;
    }

    public void setYLength(int yLength) {
        this.yLength = yLength;
    }

    public int getAmmountOfAnts() {
        return this.ammountOfAnts;
    }

    public void setAmmountOfAnts(int x) {
        this.ammountOfAnts = x;
    }

    public int getItterations() {
        return itterations;
    }

    public void setItterations(int itterations) {
        this.itterations = itterations;
    }

    public int getSigma() {
        return sigma;
    }

    public void setSigma(int sigma) {
        this.sigma = sigma;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getNumOfDocs() {
        return numOfDocs;
    }

    public void setNumOfDocs(int numOfDocs) {
        this.numOfDocs = numOfDocs;
    }

    double getkp() {
        return this.kp;
    }

    double getkd() {
        return this.kd;
    }

    int getAntFailAttempts() {
        return this.failAttempts;
    }
}
