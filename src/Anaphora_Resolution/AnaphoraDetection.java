/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Anaphora_Resolution;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author khaledd
 */
public class AnaphoraDetection {
    
 private MaxentTagger tagger;
    
    public void anophora(){
          String text = "Tom is a smart boy. He know a lot of thing.";
          
        Annotation document = new Annotation(text);
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit,parse, lemma, ner, dcoref");
        
        
    
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        pipeline.annotate(document); 
        
        
         
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
        for(Integer i : graph.keySet()){
                    System.out.println("GROUP " + i);
                    CorefChain x = graph.get(i);
                    for( CorefMention m : x.getMentionsInTextualOrder()){
                            System.out.println(m.mentionSpan);
                    }
            }
        
    }
    
    
}
