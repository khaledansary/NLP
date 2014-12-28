/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package POSTagging;

import Sentiwordnet.SentiWordNetFunction;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author khaledd
 */
public class PosTagger {
    
   private MaxentTagger tagger;
   LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
   String pathToSWN="H:\\Thesis Development\\Thesis\\NLP\\src\\Sentiwordnet\\SentiWordNet_3.0.0_20130122.txt";
   SentiWordNetFunction sentiwordnet = new SentiWordNetFunction(pathToSWN);
   
   public PosTagger()
   {
       // Initialize the tagger
       
   }
   public String TagingText(String str)
   {
        // The sample string
        
        MaxentTagger tagger = new MaxentTagger("H:\\nlp jar files\\stanford-postagger-2014-08-27\\stanford-postagger-2014-08-27\\models\\english-left3words-distsim.tagger");
        // The tagged string
        String tagged = tagger.tagString(str);
        
        return tagged;
   }
   
   public String Taggedwords(String str)
   {
        String words = null;
        
        return words;
   }
   
   public List<String> ExtractWordsByRegex(String sentenceWithTags,String REGEX) {
    List<String> nouns = new ArrayList<String>();
    String[] words = sentenceWithTags.split("\\s+");
    for (int i = 0; i < words.length; i++) {
        if(words[i].matches(REGEX)) {
                //System.out.println(" Matched ");
                 //remove the suffix _NN* and retain  [a-zA-Z]*
                //nouns.add(words[i].replaceAll("_NN\\w?\\w?\\b", ""));
                nouns.add(words[i]);
            }
        }
        return nouns;
    }
   
   public List<String> ExtractAdjWordsByRegex(String sentenceWithTags,String REGEX) {
    List<String> adjectives = new ArrayList<String>();
    String[] words = sentenceWithTags.split("\\s+");
    for (int i = 0; i < words.length; i++) {
        if(words[i].matches(REGEX)) {
                //System.out.println(" Matched ");
                 //remove the suffix _NN* and retain  [a-zA-Z]*
                //nouns.add(words[i].replaceAll("_NN\\w?\\w?\\b", ""));
                
                try{
                    Double score = sentiwordnet.extract(words[i].replaceAll("(_JJ\\w?\\w?\\b)||(_JJS\\w?\\w?\\b)", ""),"a");
                    adjectives.add(words[i].replaceAll("(_JJ\\w?\\w?\\b)||(_JJS\\w?\\w?\\b)", "")+"\t"+score+"\n");
                   
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
        return adjectives;
    }

            
   
    
}
