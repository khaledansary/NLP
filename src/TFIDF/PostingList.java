/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TFIDF;

/**
 *
 * @author khaledd
 */
import java.util.*;   
import java.io.*;   
   
public class PostingList                   // main class PostingList   
{   
                                   //hashmap for dictionary posting list   
  HashMap<String,Vector<Integer>> hPosList=null;   
                                  //hashmap for storing document freq value (df)   
  HashMap<String,Integer> hmapDF=null;   
  int D=0;                                     // count total no. of documents   
  Vector<Integer> noTermsDoc=null;           //count no. of terms in each doc.   
     
  public void createPList(String fileIO) throws IOException // start of function   
  {   
    hPosList = new HashMap<String,Vector<Integer>>();   
    hmapDF=new HashMap<String,Integer>();   
    D=0;   
    String str=null;                           //for temp storing each line   
    Integer doc_id=0;                          // keep track of doc. id   
    noTermsDoc = new Vector<Integer>();        
       
  try   
  {   
   BufferedReader fin=new BufferedReader(new FileReader(fileIO));   
   String delimiter = ",.:;/-()'[]\\\" "; // used for breaking sentence   
    
   while((str=fin.readLine())!=null)   
   {   
    if(str.startsWith(".i"))            // check if start of new document   
    {   
     doc_id=Integer.valueOf(str.substring(3));   // find doc. number   
     noTermsDoc.addElement(0);                   // initialize    
     D=D+1;                                     // count no. of documents   
     continue;   
    }   
   
    StringTokenizer tokenizer=new StringTokenizer(str,delimiter);   
    while(tokenizer.hasMoreTokens())   
    {   
     String token=tokenizer.nextToken();   
     if(token.equals(".w"))                     // ignore line containing .w   
     continue;   
        
//     noTermsDoc.set(D-1,noTermsDoc.get(D-1)+1);// increment no. of terms by 1   
     if(hPosList.containsKey(token))           // if token already present   
     {   
      Vector<Integer> vTemp= (Vector<Integer>) hPosList.get(token);   
      if(! vTemp.contains(doc_id))    // if doc_id for current token not already present   
      {                               // otherwise ignore as needn't to add again   
       vTemp.addElement(doc_id);   
       hmapDF.put(token,vTemp.size());         //store doc. frequency   
      }   
     }   
     else                                     // if token already not present   
     {   
      Vector<Integer> vPList=new Vector<Integer>();   
      vPList.addElement(doc_id);             // create vector and add doc_id   
      hPosList.put(token,vPList);   
      hmapDF.put(token,vPList.size());      //store doc. frequency   
      System.out.println(hPosList);
      
      

     }   
    }   
   }   
  }   
  catch(FileNotFoundException e)   
  {   
   System.out.println("PostingList.java:  " + e.getMessage());   
  }   
  catch(NumberFormatException e)   
  {   
   System.out.println("PostingList.java:  " + e.getMessage());   
  }   
   
 }   
}  
