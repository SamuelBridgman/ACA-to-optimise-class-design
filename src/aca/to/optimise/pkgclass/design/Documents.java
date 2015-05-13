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
 * Holds the details of the document and its position on the grid.
 * 
 * @author Samuel
 */
public class Documents {

    private int xAxis;
    private int yAxis;
    private String payLoad;
    private int id;


    private Configuration config;

    /**
     * Initialise document to -1, -1. Once scattered they should be positive X
     * and Y values.
     */
    public Documents() {
        this.xAxis = -1;
        this.yAxis = -1;
    }
    
    /**
     * Initialise document with a payload
     * @param pay 
     */
    public Documents(String pay) {
        this.xAxis = -1;
        this.yAxis = -1;
        this.payLoad = pay;
    }

    /**
     * Initialises documents Id from position in the array and PayLoad from user
     * input. Taking in userMatrix so that I can create a look u on each
     * document of the relationship of that to others if needed.
     *
     * @param matrix
     * @param names
     * @return ArrayList<Documents> 
     */
    public ArrayList<Documents> initaliseDocuments(int[][] matrix, String[] names) {
        ArrayList<Documents> documents = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            Documents doc = new Documents();
            doc.setId(i);
            doc.setPayLoad(names[i]);
            documents.add(doc);
        }

        return documents;
    }

    /**
     * Initialises documents Id from position in the array and PayLoad from user
     * input. Taking in userMatrix so that I can create a look u on each
     * document of the relationship of that to others if needed.
     *
     * @param matrix
     * @return ArrayList<Documents> 
     */
    public ArrayList<Documents> initaliseDocuments(int[][] matrix) {
        ArrayList<Documents> documents = new ArrayList<>();
        int count = 0;

        //Should already be testeted that is an N*N grid, always should be semetric in size
        for (int i = 0; i < matrix.length; i++) {
            Documents doc = new Documents();
            doc.setId(count);
            doc.setPayLoad("Element" + i);
            documents.add(doc);
            count ++;
        }

        return documents;
    }
    
    public void setPosition(int x, int y) {
        this.xAxis = x;
        this.yAxis = y;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public int getXAxis() {
        return xAxis;
    }

    public int getYAxis() {
        return yAxis;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }

    /**
     * Implementation taking diagonal as 1
     *
     * @param doc
     * @return
     */
    public float distance(Documents doc) {

        int x = (Math.abs(this.xAxis) - Math.abs(doc.getXAxis()));
        int y = (Math.abs(this.yAxis) - Math.abs(doc.getYAxis()));
        x = Math.abs(x);
        y = Math.abs(y);
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }

}
