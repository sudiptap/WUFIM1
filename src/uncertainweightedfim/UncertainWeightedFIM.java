/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uncertainweightedfim;

import java.util.Date;

/**
 *
 * @author Sudipta
 */
public class UncertainWeightedFIM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        UncertainWeightedMethodFIM uwfim = new UncertainWeightedMethodFIM();
        //uwfim.testIntesection();
        uwfim.initialization();
        uwfim.scanData();
        long startTime = new Date().getTime();
        uwfim.removeInfrequentOneItemsets();
        uwfim.generateFrequentKItemSets();
        long endTime = new Date().getTime();
        //System.out.println("elapsed milliseconds: " + (endTime - startTime));
        System.out.println("elapsed seconds: " + ((endTime - startTime)));
    }
}
