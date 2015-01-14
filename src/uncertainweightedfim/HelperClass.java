/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uncertainweightedfim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;
/**
 *
 * @author Sudipta
 */
public class HelperClass {
    public static String newline = System.getProperty("line.separator");

    public static ArrayList<String> listFilesInDir(String dirName){
        ArrayList<String> results = new ArrayList<String>();
        File[] files = new File("/path/to/the/directory").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }
    
    
    public static void deleteFile(String fileLocation){
        try{
            File file = new File(fileLocation);
            boolean wasDeleted = file.delete();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void createDir(String directoryName){
        File theDir = new File(directoryName);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
          //System.out.println("creating directory: " + directoryName);
          boolean result = false;

          try{
              theDir.mkdir();
              result = true;
           } catch(SecurityException se){
              //handle it
           }   
        }
    }
    
    public static ArrayList<String> readFile(String fileLocation) {
            //System.out.println(fileLocation);
		ArrayList<String> list = new ArrayList();
		File file = new File(fileLocation);
                //System.out.println(file.exists() +"- " );
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				list.add(line.trim());
			}                        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
    
    public static ArrayList<String> readFileKeepSpace(String fileLocation) {
            //System.out.println(fileLocation);
		ArrayList<String> list = new ArrayList();
		File file = new File(fileLocation);
                //System.out.println(file.exists() +"- " );
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				list.add(line);
			}                        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
    
     public static ArrayList<String> readFileUTF16(String fileLocation) {
         ArrayList<String> list = new ArrayList();   
         try {
		File fileDir = new File(fileLocation); 
		BufferedReader in = new BufferedReader(
		   new InputStreamReader(
                      new FileInputStream(fileDir), "UTF16")); 
		String str; 
		while ((str = in.readLine()) != null) {
		    System.out.println(str);
                    list.add(str.trim());
		} 
                in.close();
	    } 
	    catch (UnsupportedEncodingException e) 
	    {
			System.out.println(e.getMessage());
	    } 
	    catch (IOException e) 
	    {
			System.out.println(e.getMessage());
	    }
	    catch (Exception e)
	    {
			System.out.println(e.getMessage());
	    }
         return list;
	}



    public static ArrayList<String> readFastaFile(String fileLocation) {
            //System.out.println(fileLocation);
		ArrayList<String> list = new ArrayList();
		File file = new File(fileLocation);
                //System.out.println(file.exists() +"- " );
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
                                if(!line.contains(">seq_"))
                                    list.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
    
    public static void writeToBinaryFile(ArrayList<Integer> list, String fileName, boolean append) {
        FileOutputStream fileos = null;
        ObjectOutputStream obos = null;
        DataOutputStream dos = null;
        try{
            fileos = new FileOutputStream(fileName,true);
            //obos = new ObjectOutputStream(fileos);
            dos = new DataOutputStream(fileos);
            for(int listIdx=0; listIdx< list.size(); listIdx++){
                dos.writeInt(list.get(listIdx));                
                //dos.writeChar('\n');
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        //fileos.close();
        //obos.close();       
    }
    
    public static void writeDBLToBinaryFile(ArrayList<Double> list, String fileName, boolean append) {
        FileOutputStream fileos = null;
        ObjectOutputStream obos = null;
        DataOutputStream dos = null;
        try{
            fileos = new FileOutputStream(fileName,true);
            //obos = new ObjectOutputStream(fileos);
            dos = new DataOutputStream(fileos);
            for(int listIdx=0; listIdx< list.size(); listIdx++){
                dos.writeDouble(list.get(listIdx));                
                //dos.writeChar('\n');
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        //fileos.close();
        //obos.close();       
    }

    public static void WriteToFile(ArrayList<String> list, String fileName,
			boolean append) {
		try {
			FileOutputStream fop = new FileOutputStream(fileName, append);

			for (int idx = 0; idx < list.size(); idx++) {
				String string = list.get(idx);
				fop.write(string == null ? "".getBytes() : string.getBytes());
				fop.write(newline.getBytes());
			}

			fop.flush();
			fop.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
    
        public static void WriteToFile(ArrayList<String> list, String fileName,
			boolean append, String type) {
		try {
                        Writer out = new BufferedWriter(new OutputStreamWriter(
                                        new FileOutputStream(fileName), "UTF-16"));
			FileOutputStream fop = new FileOutputStream(fileName, append);

			for (int idx = 0; idx < list.size(); idx++) {
				String string = list.get(idx);
				out.write(string);
				out.write("\n");
			}

			out.flush();
			out.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
        
        public static ArrayList<Double> readBinfileIntoDBLList(String fileLocation){
            ArrayList<Double> list = new ArrayList<>();
            try{
                DataInputStream ind = new DataInputStream(new FileInputStream("abc.bin"));
                while(ind.available()>0){
                    list.add(ind.readDouble());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return list;
        }
        
        public static ArrayList<String> readBinfileIntoStringList(String fileLocation){
            ArrayList<String> list = new ArrayList<>();
            try{
                DataInputStream ind = new DataInputStream(new FileInputStream(fileLocation));
                while(ind.available()>0){
                    int dEntry = ind.readInt();
                    //System.out.println(dEntry);
                    ArrayList<Character> charList = new ArrayList<>();
                    //int iEntry = Integer.parseInt(String.valueOf(dEntry));
                    int iEntry = (int)dEntry;
                    String binList = Integer.toBinaryString(iEntry);
                    if(binList.length()<31){
                        int getCurrLen = binList.length();
                        for(int i=0; i< 31-getCurrLen; i++){
                            binList = "0".concat(binList);
                        }
                    }
                    list.add(binList);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return list;
        }
    
        public static ArrayList<String> ReadFileLineByLine(String fileLocation){
           ArrayList<String> list = new ArrayList<>();
            try {
                
                BufferedReader br = new BufferedReader(new FileReader(fileLocation));
                String line;
                while ((line = br.readLine()) != null) {
                   // process the line.
                    list.add(line);
                    if(list.size()>1024)
                        break;
                }
                br.close();                 
             } catch (FileNotFoundException ex) {
			ex.printStackTrace();
                } catch (IOException ex) {
			ex.printStackTrace();
		}
            return list;
        }
    
        public static void WriteToFile(HashSet<String> hs, String fileName,
			boolean append) {
		try {
			FileOutputStream fop = new FileOutputStream(fileName, append);

			Iterator itr = hs.iterator();
                        while(itr.hasNext()){
                            String string = itr.next().toString();
                            fop.write(string == null ? "".getBytes() : string.getBytes());
                            fop.write(newline.getBytes());
                        }

			fop.flush();
			fop.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

    public static void WriteToFile(String word, String fileName,
			boolean append) {
		try {
			FileOutputStream fop = new FileOutputStream(fileName, append);

			//for (int idx = 0; idx < list.size(); idx++) {
				//String string = list.get(idx);
				fop.write(word == null ? "".getBytes() : word.getBytes());
				//fop.write(newline.getBytes());
			//}

			fop.flush();
			fop.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

    public static BufferedReader openFileForRead(String fileName)throws IOException{
        FileInputStream fstream = new FileInputStream(fileName);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return br;
    }

    public static void WriteHashMapToFile(HashMap map, String fileName, boolean append){
        try {
			FileOutputStream fop = new FileOutputStream(fileName, append);
                        Iterator it = map.entrySet().iterator();
                        while(it.hasNext()){
                            System.out.println(it.next());
                        }
			fop.flush();
			fop.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
    
    public static ArrayList<Integer> intersection(ArrayList<Integer> a, ArrayList<Integer> b)  
    {
        ArrayList<Integer> retList = new ArrayList();
        for(int aIdx=0; aIdx< a.size();){            
            for(int bIdx = 0; bIdx< b.size();){
                if(a.get(aIdx)< b.get(bIdx))
                    aIdx++;
                if(b.get(bIdx)< a.get(aIdx))
                    bIdx++;
                if(b.get(bIdx)==a.get(aIdx)){
                    retList.add(a.get(aIdx));
                    System.out.println(retList);
                    aIdx++;
                    bIdx++;
                }
            }
        }
        return retList;
    }
    
    public static ArrayList<Integer> intersection1(ArrayList<Integer> a, ArrayList<Integer> b)  
    {
        ArrayList<Integer> retList = new ArrayList();
        int aindex = 0;  
        int bindex = 0;  
        while (aindex < a.size() && bindex < b.size()) {  
          if (a.get(aindex) == b.get(bindex)) {  
            //System.out.println(a.get(aindex) + " is in both a and b."); 
            retList.add(a.get(aindex));
            aindex++;  
            bindex++;  
          }  
          else if (a.get(aindex) < b.get(bindex)) {  
            aindex++;  
          }  
          else {  
            bindex++;  
          }  
        } 
        System.out.println(retList.size());
        return retList;
    }
    
    public static ArrayList<Integer> intersection2(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> list = new ArrayList();

        for (Integer t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    public static ArrayList<Integer> getIntersection(ArrayList<Integer> a_one, ArrayList<Integer> a_two){
        ArrayList<Integer> a_list = new ArrayList();
        int i=0, j=0;
        while(i < a_one.size() && j< a_two.size()){
            //if(a_one.get(i) == a_two.get(j)){
            if(a_one.get(i).equals(a_two.get(j))){
                a_list.add(a_one.get(i));
                //System.out.println(a_one.get(i));
                i++;
                j++;
            }else if(a_one.get(i) < a_two.get(j)){
                i++;
            }else if(a_one.get(i) > a_two.get(j)){
                j++;
            }
        }
        //Integer[] a_return = new Integer[a_list.size()];
        //a_list.toArray(a_return);
        return a_list;
    }
    
    public static String getUnion(String list1, String list2){
        String[] str1 = list1.split(",");
        String[] str2 = list2.split(",");
        
        StringBuilder sb = new StringBuilder();
        int str1Idx = 0;
        int str2Idx = 0;
        while(str1Idx< str1.length && str2Idx< str2.length){
            if(str1[str1Idx].compareToIgnoreCase(str2[str2Idx])<0){
                sb.append(str1[str1Idx]);
                //System.out.println(str1Idx);
                sb.append(",");
                str1Idx++;
            }
            else if(str1[str1Idx].compareToIgnoreCase(str2[str2Idx])>0){
                sb.append(str2[str2Idx]);
                sb.append(",");
                //System.out.println(str2Idx);
                str2Idx++;
            }
            else{
                sb.append(str2[str2Idx]);
                sb.append(",");
                str2Idx++;
                str1Idx++;
            }
        }
        if(str1Idx==str1.length){
            while(str2Idx< str2.length){
                sb.append(str2[str2Idx]);
                //System.out.println(str1Idx);
                //System.out.println(str2Idx);
                sb.append(",");
                str2Idx++;
            }
        }
        else{
            while(str1Idx< str1.length){
                sb.append(str1[str1Idx]);
                sb.append(",");
                str1Idx++;
            }
        }        
        return sb.toString().substring(0, sb.length()-1);
    }
    
    public static ArrayList<Integer> getUnion(ArrayList<Integer> str1, ArrayList<Integer> str2){
        //String[] str1 = list1.split(",");
        //String[] str2 = list2.split(",");
        
        ArrayList<Integer> str3 = new ArrayList();
        int str1Idx = 0;
        int str2Idx = 0;
        while(str1Idx< str1.size() && str2Idx< str2.size()){
            if(str1.get(str1Idx)<(str2.get(str2Idx))){
                str3.add(str1.get(str1Idx));
                str1Idx++;
            }
            else if(str1.get(str1Idx)>str2.get(str2Idx)){                
                str3.add(str2.get(str2Idx));
                str2Idx++;
            }
            else{                
                str3.add(str2.get(str2Idx));
                str2Idx++;
                str1Idx++;
            }
        }
        if(str1Idx==str1.size()){
            while(str2Idx< str2.size()){
                //sb.append(str2[str2Idx]);
                //System.out.println(str1Idx);
                //System.out.println(str2Idx);
                //sb.append(",");
                str3.add(str2.get(str2Idx));
                str2Idx++;
            }
        }
        else{
            while(str1Idx< str1.size()){
                //sb.append(str1[str1Idx]);
                //sb.append(",");
                str3.add(str1.get(str1Idx));
                str1Idx++;
            }
        }        
//        return sb.toString().substring(0, sb.length()-1);
        return str3;
    }
    
    private static int[] getUnion(int[] a_one, int[] a_two){
        int i=0, j=0;
        int value = -1;
        int MAX_ELEM = 0;
        int[] a_return = new int[a_one.length + a_two.length -1];
        try{
            while(i<a_one.length || j<a_two.length){
                if(a_one[i] < a_two[j]){
                    value = a_one[i];
                    i++;
                }else{
                    value = a_two[j];
                    j++;
                }
                if(!found(a_return, value, 0, MAX_ELEM)){
                    a_return[MAX_ELEM++] = value;
                }
            }
        }catch(IndexOutOfBoundsException ex){
            if(i == a_one.length){
                for(int k=j; k<a_two.length; k++){
                    if(!found(a_return, a_two[k], 0, MAX_ELEM)){
                        a_return[MAX_ELEM++] = a_two[k];
                    }
                }
            }else{
                for(int l=i; l<a_one.length; l++){
                    if(!found(a_return, a_one[l], 0, MAX_ELEM)){
                        a_return[MAX_ELEM++] = a_one[l];
                    }
                }
            }
        }
        return a_return;
    }
    
    private static boolean found1(int[] array, int value,int start, int end){
        for(int i=0; i<array.length; i++){
            if( array[i] == value){
                return true;
            }
        }
        return false;
    }

    /* ---------- binary search ------------- */

    private static boolean found(int[] array, int value, int start, int end){
        if(end < 0 || start < 0){
            return false;
        }
        if(array[start] == value || array[end] == value){
            return true;
        }
        if(end-start == 1){
            if(array[end] == value){
                return true;
            }
            if(array[start] == value){
                return true;
            }
        }else{
            int mid = (start + end)/2;
            if(array[mid] == value){
                return true;
            }else if(array[mid] < value){
                return found1(array, value, mid+1, end);
            }else if(array[mid] > value){
                return found1(array, value, start, mid-1);
            }
        }
       return false;
    }


    
    public static String union1(String a, String b) {
        Set<String> set = new HashSet();
        String[] aArr = a.split(",");
        String[] bArr = b.split(",");
        
        List<String> list1 = new ArrayList(aArr.length);  
        list1.addAll(Arrays.asList(aArr));
        
        List<String> list2 = new ArrayList(bArr.length);  
        list2.addAll(Arrays.asList(bArr));
        
        set.addAll(list1);
        set.addAll(list2);

        //return new ArrayList<>(set);
        //System.out.println(set.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", ""));
        return set.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
    }
    
    public static String union(String a, String b){
        int aArrIdx = 0;
        int bArrIdx = 0;
        StringBuilder ret = new StringBuilder();
        String[] aArr = a.split(",");
        String[] bArr = b.split(",");
        
        Arrays.sort(aArr);
        Arrays.sort(bArr);
        
        for(aArrIdx=0; aArrIdx< aArr.length;){            
            for(bArrIdx = 0; bArrIdx< bArr.length;){
                if(aArr[aArrIdx].compareTo(bArr[bArrIdx])<0){
                    ret.append(aArr[aArrIdx]);
                    ret.append(",");
                    aArrIdx++;
                    //System.out.println(ret.toString());
                }
                else if(aArr[aArrIdx].compareTo(bArr[bArrIdx])>0){
                    ret.append(bArr[bArrIdx]);
                    ret.append(",");
                    bArrIdx++;
                    //System.out.println(ret.toString());
                }
                else{
                    ret.append(aArr[aArrIdx]);
                    ret.append(",");
                    aArrIdx++;
                    bArrIdx++;
                    //System.out.println(ret.toString());
                }                
//                if(aArrIdx == aArr.length){
//                    for(int i=bArrIdx; i< bArr.length; i++){
//                        ret.append(bArr[i]);
//                        ret.append(",");
//                        System.out.println(ret.toString());
//                    }
//                }
//                
//                if(bArrIdx == bArr.length){
//                    for(int i=aArrIdx; i< aArr.length; i++){
//                        ret.append(aArr[i]);
//                        ret.append(",");
//                        System.out.println(ret.toString());
//                    }
//                }
            }
            if(bArrIdx == bArr.length) break;
        }
                        if(aArrIdx == aArr.length){
                    for(int i=bArrIdx; i< bArr.length; i++){
                        ret.append(bArr[i]);
                        ret.append(",");
                        //System.out.println(ret.toString());
                    }
                }
                
                if(bArrIdx == bArr.length){
                    for(int i=aArrIdx; i< aArr.length; i++){
                        ret.append(aArr[i]);
                        ret.append(",");
                        //System.out.println(ret.toString());
                    }
                }
        if(ret.charAt(ret.length()-1)== ',')
            ret.deleteCharAt(ret.length()-1);
        System.out.println(ret.toString());
        return ret.toString();
    }
    
    public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {
        Set<T> tmp = new TreeSet<T>(setA);
        tmp.removeAll(setB);
        return tmp;
    }
    
    public static int integerfrmbinary(String str){
        double j=0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)== '1'){
             j=j+ Math.pow(2,str.length()-1-i);
         }
        }
        return (int) j;
    }
    
    public static Double doublefrmbinary(String str){
        double j=0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)== '1'){
             j=j+ Math.pow(2,str.length()-1-i);
         }
        }
        return (Double) j;
    }
    
    
    public static ArrayList<Integer> getIntersectOfIntervals(ArrayList<Integer> suppList1, ArrayList<Integer> suppList2){
        ArrayList<Integer> res = new ArrayList();
        ArrayList<Integer> al1 = new ArrayList();
        ArrayList<Integer> al2 = new ArrayList();
        al1.addAll(suppList1);
        al2.addAll(suppList2);
        int idx1=0;
        int idx2=0;
        int suppSum=0;
        while(idx1<al1.size()-1 && idx2<al2.size()-1){
            int a = al1.get(idx1);
            int b = al1.get(idx1+1);
            int c = al2.get(idx2);
            int d = al2.get(idx2+1);
            if(a<c){
                if(b<d){
                    res.add(c);
                    res.add(b);
                    suppSum += Math.abs(b-c+1);
//                    if(b-c+1<0){
//                        System.out.println("caught it");
//                    }
                    al2.set(idx2,b+1);
                    al2.set(idx2+1,d);
                    idx1+=2;
                }
                else{
                    res.add(c);
                    res.add(d);
                    suppSum += Math.abs(d-c+1);
//                    if(d-c+1<0){
//                        System.out.println("caught it");
//                    }
                    al1.set(idx1,d+1);
                    al1.set(idx1+1,b);
                    idx2+=2;
                }
            }
            else{
                if(b<d){
                    res.add(a);
                    res.add(b);
                    suppSum += Math.abs(b-a+1);
//                    if(b-a+1<0){
//                        System.out.println("caught it");
//                    }
                    al2.set(idx2,b+1);
                    al2.set(idx2+1,d);
                    idx1+=2;
                }
                else{
                    res.add(a);
                    res.add(d);
                    suppSum += Math.abs(d-a+1);
//                    if(d-a+1<0){
//                        System.out.println("caught it");
//                    }
                    al1.set(idx1,d+1);
                    al1.set(idx1+1, b);
                    idx2+=2;
                }
            }
        }
        res.add(suppSum);
        //System.out.println(res);
        return res;
    }
    
    public static int generateRandomNumber(int range){
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(range);
    } 
    
    public static int generateRandomNumberInRange(int Max, int Min){
        int ret = (Min + (int)(Math.random() * ((Max - Min) + 1)));
        return ret;
    }
    
    public static ArrayList<String> listFilesForFolder(String path) {        
        ArrayList<String> filePathList = new ArrayList<String>();   
        try{                         
            String files;
            File folder = new File(path);        
            File[] listOfFiles = folder.listFiles();  

            if(listOfFiles!=null){
                for (int i = 0; i < listOfFiles.length; i++) 
                {
                    if (listOfFiles[i].isFile()) 
                    {
                        files = listOfFiles[i].getName();
        //                if (files.endsWith(".txt") || files.endsWith(".TXT"))
        //                {
                            //System.out.println(path + files);
                            //filePathList.add(path + files);
                            filePathList.add(files);
                        //}
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    //}
        return filePathList;
    }    
    
    public static double getEuclideanDistance(int[] pt1, int[] pt2){
        double edist=0;
        for(int idx=0; idx< pt1.length; idx++){
            edist+=Math.pow((pt1[idx]-pt2[idx]),2);
        }
        return Math.sqrt(edist);
    }
    
    public static int editDistance(String s, String t){
        int m=s.length();
        int n=t.length();
        int[][]d=new int[m+1][n+1];
        for(int i=0;i<=m;i++){
            d[i][0]=i;
        }
        for(int j=0;j<=n;j++){
            d[0][j]=j;
        }
        for(int j=1;j<=n;j++){
            for(int i=1;i<=m;i++){
            if(s.charAt(i-1)==t.charAt(j-1)){
                d[i][j]=d[i-1][j-1];
            }
            else{
                d[i][j]=min((d[i-1][j]+1),(d[i][j-1]+1),(d[i-1][j-1]+1));
            }
            }
        }
        return(d[m][n]);
  }
  public static int min(int a,int b,int c){
    return(Math.min(Math.min(a,b),c));
  }
  
  public static int countMatches(String str, String sub) {
    if (str.isEmpty() || sub.isEmpty()) {
        return 0;
    }
    int count = 0;
    int idx = 0;
    while ((idx = str.indexOf(sub, idx)) != -1) {
        count++;
        idx += sub.length();
    }
    return count;
  }
  public static double mean(ArrayList<Double> a) {
        if (a.size() == 0) return Double.NaN;
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum = sum + a.get(i);
        }
        return sum / a.size();
    }

  public static double var(ArrayList<Double> a) {
        if (a.size() == 0) return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum += (a.get(i) - avg) * (a.get(i) - avg);
        }
        return sum / (a.size() - 1);
  } 
  
  
  public static void callDeleteDir(String SRC_FOLDER){
      File directory = new File(SRC_FOLDER); 
    	//make sure directory exists
    	if(!directory.exists()){ 
           System.out.println("Directory does not exist.");
           System.exit(0); 
        }else{ 
           try{ 
               deleteDir(directory); 
           }catch(Exception e){
               e.printStackTrace();
               System.exit(0);
           }
        }
  }
  
  private static void deleteDir(File file){         
    	if(file.isDirectory()){ 
    		//directory is empty, then delete it
    		if(file.list().length==0){ 
    		   file.delete();
    		   System.out.println("Directory is deleted : " 
                                                 + file.getAbsolutePath());
 
    		}else{ 
    		   //list all the directory contents
        	   String files[] = file.list(); 
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp); 
        	      //recursive delete
        	     deleteDir(fileDelete);
        	   } 
        	   //check the directory again, if empty then delete it
        	   if(file.list().length==0){
           	     file.delete();
        	     System.out.println("Directory is deleted : " 
                                                  + file.getAbsolutePath());
        	   }
    		} 
    	}else{
    		//if file, then delete it
    		file.delete();
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
    }
    
  
}
