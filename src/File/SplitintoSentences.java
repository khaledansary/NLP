/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package File;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author khaledd
 */
public class SplitintoSentences {
    
    public List<String> getSentences(String filetext)
            
    {
        List<String> sentences = new ArrayList<String>();
        
        Reader reader = new StringReader(filetext);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);

        List<String> sentenceList = new LinkedList<String>();
        Iterator<List<HasWord>> it = dp.iterator();
        while (it.hasNext()) {
           StringBuilder sentenceSb = new StringBuilder();
           List<HasWord> sentence = it.next();
           for (HasWord token : sentence) {
              if(sentenceSb.length()>1) {
                 sentenceSb.append(" ");
              }
              sentenceSb.append(token);
           }
           sentenceList.add(sentenceSb.toString());
        }

        for(String sentence:sentenceList) {
         //  System.out.println(sentence);
           sentences.add(sentence);
        }
        
        return sentences;
        
    }
    
}
