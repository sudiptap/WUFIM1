/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uncertainweightedfim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sudipta
 */
public class UncertainWeightedMethodFIM {
    
    class uncertaintyTransactionPair{
        public int transactionId;//this is transaction identification number
        public double uncertainty;//this is probability associated with the item for correspoding transaction
    }
    class itemDetails{
        public ArrayList<Integer> itemId;//this is the identification number of an item
        //public ArrayList<Double> uncertainty;//this is probability associated with the item for correspoding transaction
        public double weight;
        //public ArrayList<Integer> transactionList ;
        public ArrayList<uncertaintyTransactionPair> uncertaintyTransactionPairList;
        public double expectedSupport = 0.0;        
    };
    
    public ArrayList<itemDetails> vLayOut = new ArrayList<>();
    
    public ArrayList<ArrayList<Integer>> freqItemsetList = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> freqItemsetTransactionList = new ArrayList<>();
    
    public Map<Integer,Double> itemWeightMap=new HashMap<>();
    
    public int noOfOneFrequentItemsets=0;//will be updated once frequent item sets are found
    
    public void initialization(){
        for(int i=0; i< Constants.noOfItems+1; i++){
            itemDetails id = new itemDetails();
            id.itemId=new ArrayList<>();
            //id.uncertainty = new ArrayList<>();
            id.weight = -1.1;
            //id.transactionList = new ArrayList<>();
            uncertaintyTransactionPair utp = new uncertaintyTransactionPair();
            id.uncertaintyTransactionPairList = new ArrayList<>();
            vLayOut.add(id);
        }
    }
    
    public void scanData(){
        int itemId = -1;                
        ArrayList<String> dataList = HelperClass.readFile(Constants.transFileInp);
        for(int tranIdx=0; tranIdx< dataList.size(); tranIdx++){
            String[] transaction = dataList.get(tranIdx).split(" ");            
            for(int recIdx=0; recIdx< transaction.length; ){
                itemId = Integer.parseInt(transaction[recIdx]);
                //System.out.println(itemId);
                if(vLayOut.get(itemId).itemId.isEmpty()){
                    vLayOut.get(itemId).itemId.add(Integer.parseInt(transaction[recIdx]));
                }
                recIdx++;
                //vLayOut.get(itemId).uncertainty.add(Double.parseDouble(transaction[recIdx]));recIdx++;
                double uncertainty = Double.parseDouble(transaction[recIdx]);
                vLayOut.get(itemId).expectedSupport+=uncertainty;recIdx++;
                vLayOut.get(itemId).weight = Double.parseDouble(transaction[recIdx]);recIdx++;
                
                //vLayOut.get(itemId).transactionList.add(tranIdx);                 
                uncertaintyTransactionPair utp = new uncertaintyTransactionPair();
                utp.transactionId = tranIdx;
                utp.uncertainty = uncertainty;
                vLayOut.get(itemId).uncertaintyTransactionPairList.add(utp);
                itemWeightMap.put(itemId, vLayOut.get(itemId).weight);
            }
        }        
    }
    
    
    
    public void removeInfrequentOneItemsets(){
        for(int itemIdx=0; itemIdx< vLayOut.size(); itemIdx++){
            if(!isFrequentOneFrequentItemset(vLayOut.get(itemIdx))){
                vLayOut.remove(itemIdx);
            }else{
                itemWeightMap.put(vLayOut.get(itemIdx).itemId.get(0), vLayOut.get(itemIdx).weight);
            }
        }
        //vLayOut.remove(0);        
        noOfOneFrequentItemsets = vLayOut.size();
    }
    
    private boolean isFrequentOneFrequentItemset(itemDetails itemDetailsObject){
        boolean isFrequent = false;
        boolean trueCondition1 = true;
        boolean trueCondition2 = true;
        if((itemDetailsObject.expectedSupport<Constants.minExpectedSupp1)&&(itemDetailsObject.weight<Constants.minWeight)){
            //condition 1 fails
            trueCondition1 = false;
        }
        if((itemDetailsObject.weight*itemDetailsObject.expectedSupport)<Constants.minSupp2){
            //condition 2 fails
            trueCondition2 = false;
        }
        isFrequent = trueCondition1 || trueCondition2;
        return isFrequent;
    }
    
    private ArrayList<uncertaintyTransactionPair> getIntersectionOfPairLists(ArrayList<uncertaintyTransactionPair> list1, ArrayList<uncertaintyTransactionPair> list2){
        ArrayList<uncertaintyTransactionPair> retList = new ArrayList<>();
        try{        
        int i=0, j=0;
        while(i < list1.size() && j< list2.size()){
            if(list1.get(i).transactionId==(list2.get(j).transactionId)){
                uncertaintyTransactionPair utpObj = new uncertaintyTransactionPair();
                utpObj.transactionId = list1.get(i).transactionId;
                utpObj.uncertainty = list1.get(i).uncertainty*list2.get(j).uncertainty;
                retList.add(utpObj);
                //System.out.println(a_one.get(i));
                i++;
                j++;
            }else if(list1.get(i).transactionId < list2.get(j).transactionId){
                i++;
            }else if(list1.get(i).transactionId > list2.get(j).transactionId){
                j++;
            }
            //System.out.println("i="+i);
            //System.out.println("j="+j);
        }
        }catch(Exception e){
            System.out.println();
        }
        return retList;
    }
    
    public void testIntesection(){
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        list1.add(1);list1.add(3);list1.add(5);
        list2.add(3);
        //list2.add(4);list2.add(5);
        ArrayList<Integer> list = HelperClass.getIntersection(list1, list2);
        System.out.println(list);
    }
    
    public double getNewWeight(ArrayList<Integer> itemList){
        double newWeight = 0.0;
        for(int itemIdx=0; itemIdx< itemList.size(); itemIdx++){
            newWeight+=itemWeightMap.get(itemList.get(itemIdx));            
        }
        newWeight/=itemList.size();
        return newWeight;
    }
    
    public itemDetails getNewItemsetDetails(itemDetails itemDetailsObject1, itemDetails itemDetailsObject2){
        itemDetails newItemDetails = new itemDetails();        
        ArrayList<Integer> newItemId = new ArrayList<>();
        newItemId.addAll(itemDetailsObject1.itemId);
        newItemId.addAll(itemDetailsObject2.itemId);
        newItemDetails.itemId = newItemId;              
        newItemDetails.uncertaintyTransactionPairList = getIntersectionOfPairLists(itemDetailsObject1.uncertaintyTransactionPairList, 
                itemDetailsObject2.uncertaintyTransactionPairList);
        if(newItemDetails.uncertaintyTransactionPairList.isEmpty()){
            int kk=0;
        }
        newItemDetails.expectedSupport = getExpectedSupport(newItemDetails.uncertaintyTransactionPairList);
        newItemDetails.weight = getNewWeight(newItemDetails.itemId);  
        return newItemDetails;
               
    }
    
    private double getExpectedSupport(ArrayList<uncertaintyTransactionPair> utp){
        double eSupp = 0.0;
        for(int i=0; i< utp.size(); i++){
            eSupp+=utp.get(i).uncertainty;
        }
        return eSupp;
    }
    
    public boolean isFrequentItemset(itemDetails newItemDetails){
        boolean isFrequent = false;
        boolean trueCondition1 = true;
        boolean trueCondition2 = true;
        //itemDetails newItemDetails = getNewItemsetDetails(itemDetailsObject1, itemDetailsObject2);
        if((newItemDetails.expectedSupport<Constants.minExpectedSupp1)&&(newItemDetails.weight<Constants.minWeight)){
            //condition 1 fails
            trueCondition1 = false;
        }
        if((newItemDetails.weight*newItemDetails.expectedSupport)<Constants.minSupp2){
            //condition 2 fails
            trueCondition2 = false;
        }
        //isFrequent = trueCondition1 || trueCondition2;  
        if(trueCondition1==true){
            if(trueCondition2==true){
                isFrequent=true;
            }
        }
        return isFrequent;
    }
    
//    public boolean isFrequentItemset(itemDetails newItemDetails){
//        boolean isFrequent = false;
//        boolean trueCondition1 = false;
//        boolean trueCondition2 = false;
//        //itemDetails newItemDetails = getNewItemsetDetails(itemDetailsObject1, itemDetailsObject2);
//        if((newItemDetails.expectedSupport>=Constants.minExpectedSupp1)&&(newItemDetails.weight>=Constants.minWeight)){
//            //condition 1 fails
//            trueCondition1 = true;
//        }
//        if((newItemDetails.weight*newItemDetails.expectedSupport)>=Constants.minSupp2){
//            //condition 2 fails
//            trueCondition2 = true;
//        }
//        isFrequent = trueCondition1 && trueCondition2;       
//        return isFrequent;
//    }
        
    public void generateFrequentKItemSets(){  
        int kFreqStartIdx=0;
        int kFreqEndIdx=noOfOneFrequentItemsets;
        int kFreqIdx=kFreqStartIdx;
        int noOfKFrequentItemsets=0;
        while(true){
            noOfKFrequentItemsets=0;
            for(kFreqIdx=kFreqStartIdx; kFreqIdx< kFreqEndIdx; kFreqIdx++){
                itemDetails itemDetailsObject1 = vLayOut.get(kFreqIdx);
                int sizeOfItemset = itemDetailsObject1.itemId.size();
                if(sizeOfItemset==0)
                    continue;
                int largestItem = itemDetailsObject1.itemId.get(sizeOfItemset-1);
                for(int oneFreqIdx=0; oneFreqIdx< noOfOneFrequentItemsets; oneFreqIdx++){
                    itemDetails itemDetailsObject2 = vLayOut.get(oneFreqIdx);
                    //if itemDetailsObject2 has an itemid > the largest itemid of itemDetailsObject1 we should merge them else not
                    //int oneItemset=-2;
                    //try{
                    if(itemDetailsObject2.itemId.isEmpty())
                        continue;
                    int oneItemset = itemDetailsObject2.itemId.get(0);//there is only one item in this itemset
                    //}catch(Exception e){
                        //int ii=0;
                    //}
                    if(oneItemset>largestItem){
                        //check whether the combined itemset is a frequent item
                        itemDetails newItemDetails = getNewItemsetDetails(itemDetailsObject1, itemDetailsObject2);
                        //test for existence of the itemset in the database
                        if(!newItemDetails.uncertaintyTransactionPairList.isEmpty()){
                            if(isFrequentItemset(newItemDetails)){
                                //System.out.println(newItemDetails.itemId);
                                vLayOut.add(newItemDetails); 
                                noOfKFrequentItemsets++;
                            }
                        }
                    }
                }
            }
            //update the start and end idex now
            if(noOfKFrequentItemsets==0){
                break;
            }
            kFreqStartIdx=kFreqEndIdx;
            kFreqEndIdx=kFreqStartIdx+noOfKFrequentItemsets;
        }
        
//        for(int vLayOutIdx=0; vLayOutIdx< vLayOut.size(); vLayOutIdx++){
//            if(vLayOut.get(vLayOutIdx).itemId.contains(15)){
//                System.out.println(vLayOut.get(vLayOutIdx).itemId + " : " + vLayOut.get(vLayOutIdx).expectedSupport);
//            }
//            if(isFrequentItemset(vLayOut.get(vLayOutIdx)))
//                System.out.println(vLayOut.get(vLayOutIdx).itemId + " : " + vLayOut.get(vLayOutIdx).expectedSupport);
//        }
    }
    
}
