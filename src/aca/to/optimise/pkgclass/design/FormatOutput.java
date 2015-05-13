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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Writes the output to fie and formats the output to UML format.
 * 
 * @author Samuel
 */
class FormatOutput {

    /**
     * Writes classes to file in UML format
     *
     * @param clusters
     * @param methods
     * @param attributes
     * @return
     * @throws IOException
     */
    static String createClasses(ArrayList<ArrayList<Integer>> clusters, int methods, int attributes) throws IOException {

        ArrayList<uml> classes = new ArrayList();
        uml newClass = new uml();
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = 0; j < clusters.get(i).size(); j++) {
                if (clusters.get(i).get(j) < methods) {
                    newClass.addMethod(clusters.get(i).get(j));
                } else {
                    newClass.addAttribute(clusters.get(i).get(j));
                }
            }
            classes.add(newClass);
            newClass = new uml();
        }
        ToFile toFile = new ToFile();

        return toFile.StringToFile(classes);
    }

    /**
     * Write number of clusters to file, for batch functionality
     *
     * @param name
     * @param metric
     * @param ammountOfClusters
     */
    static void coupling(String name, double metric, int ammountOfClusters) {
        BufferedWriter writer = null;

        try {
            //create a temporary file
            String nameFileOne = "Coupling.txt";
            File logFile = new File(nameFileOne);

            writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(name + ", Coupling: " + metric + " ,Clusters: " + ammountOfClusters);
            writer.newLine();

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

    /**
     * Used for extended output
     *
     * @param fileName
     * @param data
     */
    static void appendTo(String fileName, int data) {
        BufferedWriter writer = null;

        try {
            //create a temporary file
            File logFile = new File(fileName + ".txt");

            // This will output the full path where the file will be written to...
            //System.out.println(logFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(logFile, true));
            String data1 = Integer.toString(data);
            writer.write(data1);
            writer.newLine();

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

    /**
     * used for extended output
     *
     * @param fileName
     * @param data
     */
    static void appendTo(String fileName, double data) {
        BufferedWriter writer = null;

        try {
            //create a temporary file
            File logFile = new File(fileName + ".txt");

            // This will output the full path where the file will be written to...
            //System.out.println(logFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(logFile, true));
            String data1 = Double.toString(data);
            writer.write(data1);
            writer.newLine();

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

class ToFile {

    /**
     * Used to format output
     * 
     * @param classes
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String StringToFile(ArrayList<uml> classes) throws FileNotFoundException, IOException {
        String time = "";
        time = this.getTime();
        String nameFileOne = "Uml" + time + ".txt";

        try (FileOutputStream scfos = new FileOutputStream(nameFileOne);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(scfos))) {
            for (int i = 0; i < classes.size(); i++) {
                writer.write("class " + i);
                writer.newLine();
                writer.write("------");
                writer.newLine();
                ArrayList<Integer> methods = classes.get(i).getMethods();
                for (int j = 0; j < methods.size(); j++) {
                    writer.write(methods.get(j).toString());
                    writer.newLine();
                }
                writer.write("------");
                writer.newLine();
                ArrayList<Integer> attributes = classes.get(i).getAttrbutes();
                for (int j = 0; j < attributes.size(); j++) {
                    writer.write(attributes.get(j).toString());
                    writer.newLine();
                }
                writer.write("------");
                writer.newLine();

            }
        }
        return nameFileOne;
    }

    /**
     * Not currently used
     * 
     * @param classes 
     */
    public void writeUmlToFile(ArrayList<uml> classes) {
        //BufferedWriter writer = null;
        String time = "";
        time = this.getTime();

        try {
            //create a temporary file
            String nameFileOne = "Uml" + time + ".txt";
            File logFile = new File(nameFileOne);

            // This will output the full path where the file will be written to...
            //System.out.println(logFile.getCanonicalPath());
            //writer = new BufferedWriter(new FileWriter(logFile, true));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile), "UTF_8"));
            for (int i = 0; i < classes.size(); i++) {
                writer.write("class " + i);
                writer.newLine();
                writer.write("------");
                writer.newLine();
                ArrayList<Integer> methods = classes.get(i).getMethods();
                for (int j = 0; j < methods.size(); j++) {
                    writer.write(methods.get(j));
                    writer.newLine();
                }
                writer.write("------");
                writer.newLine();
                ArrayList<Integer> attributes = classes.get(i).getAttrbutes();
                for (int j = 0; j < attributes.size(); j++) {
                    writer.write(attributes.get(j));
                    writer.newLine();
                }
                writer.write("------");
                writer.newLine();

            }
            writer.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                //    writer.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Used to create unique file name
     *
     * @return
     */
    private String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(cal.getTime());
    }
}

/**
 * Used to create UML output
 *
 * @author Samuel
 */
class uml {

    private ArrayList<Integer> methods = new ArrayList<>();
    private ArrayList<Integer> attributes = new ArrayList<>();

    public ArrayList<Integer> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<Integer> methods) {
        this.methods = methods;
    }

    public ArrayList<Integer> getAttrbutes() {
        return attributes;
    }

    public void setAttrbutes(ArrayList<Integer> attrbutes) {
        this.attributes = attrbutes;
    }

    public void addAttribute(Integer attribute) {
        this.attributes.add(attribute);
    }

    public void addMethod(Integer method) {
        this.methods.add(method);
    }
}
