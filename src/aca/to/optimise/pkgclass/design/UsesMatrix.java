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
 * Creates the matrix of what elements use other elements and methods to 
 * access the matrix
 * 
 * @author Samuel
 */
public class UsesMatrix {

    private int totalDocs;
    private int[][] usesMatrix;

    /**
     * Empty constructor
     */
    public UsesMatrix() {
    }

    /**
     * Holds the matric of relationships between documents
     *
     * @param usesMatrix
     * @return
     */
    public UsesMatrix(int[][] matrix) {
        //Should already be testeted that is an N*N grid, always should be semetric in size    
        totalDocs = matrix.length;
        usesMatrix = new int[this.totalDocs][this.totalDocs];
        this.usesMatrix = matrix;

    }

    /**
     * Returns -1 if no relationship and 1 if relationship Had to edit it to not
     * return 0 as in desityDependenceyFunction it does 1 - and for 0 that would
     * give a positive
     *
     * @param a
     * @param b
     * @return
     */
    public int get(int a, int b) {
        if (b != -1) {
            if (usesMatrix[a][b] != 0) {
                return usesMatrix[a][b];
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public int getSize() {
        return this.usesMatrix.length;
    }

    public int getSizeSecondDimension() {
        return this.usesMatrix[0].length;
    }
}
