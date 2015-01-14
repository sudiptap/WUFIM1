/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uncertainweightedfim;

import java.util.HashMap;

/**
 *
 * @author Sudipta
 */
public class Constants {
    public static String transFileInp = "DataF10kS.txt";
    
    public static int noOfItems = 34348;
    
    public static int noOfTransactions = 9982;
    
    public static double minSup1 = 0.015;
    
    public static double minWeight = 0.02;
    
    
    
    public static double minExpectedSupp1 = minSup1*(double)noOfTransactions;//(maximumthreshold)
    public static double minSupp2 = minExpectedSupp1/4;//(minimumthresshold and divided by 3)
    
    
//    public static String transFileInp = "smallData.txt";
//    public static int noOfItems = 6;
//    public static int noOfTransactions = 8;
//    public static double minSup1 = 0.02;
//    public static double minWeight = 0.11;
//    public static double minExpectedSupp1 = minSup1*(double)noOfTransactions;//(maximumthreshold)
//    public static double minSupp2 = 0.04;//(minimumthresshold and divided by 3)
}
