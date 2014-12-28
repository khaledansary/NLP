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
   
public class MatrixTF   
{   
  //hashmap for term-frequency matrix    
  HashMap<String,Vector<Double>> hmapTF=null;   
  //hashmap for storing weighted term-frequency matrix   
  HashMap<String,Vector<Double>> hmapWTF=null;   
     
 public void  cal_TF(PostingList p, String fileIO ) throws IOException   
 {   
  hmapTF = new HashMap<String,Vector<Double>>(); //tf   
  hmapWTF = new HashMap<String,Vector<Double>>(); //weigthed tf   
  String str=null;   
  Integer doc_id=0;   
  String delimiter = ",.:;/-()'[]\\\" ";   
   
  try   
  {   
  BufferedReader fin=new BufferedReader(new FileReader(fileIO));   
   
  while((str=fin.readLine())!=null)      //read a line   
  {   
   if(str.startsWith(".i")) // start of document   
   {   
    doc_id=Integer.valueOf(str.substring(3)); //get document id   
    continue;   
   }   
   
   StringTokenizer tokenizer=new StringTokenizer(str,delimiter);  //break line to line token   
   while(tokenizer.hasMoreTokens())   
   {   
     String token=tokenizer.nextToken();   
     if(token.equals(".w")) // ignore line containing .w   
     continue;   
   
    //**** hashmap for implementing term-document matrix *****************   
   
    if(hmapTF.containsKey(token))        //hashmap already contain word   
     {   
      Vector<Integer> vPosList = p.hPosList.get(token);  //get posting list   
      Integer did = vPosList.indexOf(doc_id);     //get index of doc_id to store freq info   
                                               // at same index in term-doc matrix   
      Vector<Double> vTF= (Vector<Double>) hmapTF.get(token); //get vector for word   
      if(vTF.size()<did+1)     //add info 1st time for already present word   
      vTF.addElement(1.0);               // but not for the current doc_id   
      else   
      vTF.set(did,vTF.get(did)+1.0);  //increment freq info by 1   
     }   
     else                             //hashmap doesnt contain word   
     {   
      Vector<Double> freq=new Vector<Double>();   
      freq.addElement(1.0);             //store 1 for that doc_id   
      hmapTF.put(token,freq);   
     }   
    }//end of inner while   
  } //end of outermost while   
  fin.close();   
   
 //*** hashmap for weighted term-document matrix ********************   
   
   Iterator iterator = hmapTF.keySet().iterator();   
   while( iterator. hasNext() )   
   {   
    String tempToken=(String)iterator.next();         //store token temporarily   
    Vector<Double> vtemp= (Vector<Double>) hmapTF.get(tempToken);   
   
    Vector<Double> vWTF= new Vector<Double>();  //vector for weight    
   
    double NumberDoc=p.D;       //to convert into double for division   
    for(int i=0;i<vtemp.size();i++)     //calculate weight   
    vWTF.addElement(vtemp.get(i)*(Math.log(NumberDoc/p.hmapDF.get(tempToken))/Math.log(10.0)));   
   
    hmapWTF.put(tempToken,vWTF);  
    System.out.println(hmapWTF);
   }   
  }   
   catch(FileNotFoundException e)   
   {   
    System.out.println("MatrixTF.java:  " + e.getMessage());   
   }   
   catch(NullPointerException e)   
   {   
    System.out.println("MatrixTF.java:  " + e.getMessage());   
   }   
   catch(ArrayIndexOutOfBoundsException e)   
   {   
    System.out.println("MatrixTF.java:  " + e.getMessage());   
   }   
   
  }   
 }  