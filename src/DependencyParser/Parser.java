/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DependencyParser;

/**
 *
 * @author khaledd
 */
import com.chaoticity.dependensee.Main;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

import edu.stanford.nlp.process.*;
import edu.stanford.nlp.process.TokenizerFactory;


import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    public void CallParser(String text)  // start of the main method

    {
        try{
            
        
            TreebankLanguagePack tlp = new PennTreebankLanguagePack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
            LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
            lp.setOptionFlags(new String[]{"-maxLength", "500", "-retainTmpSubcategories"});
            TokenizerFactory<CoreLabel> tokenizerFactory =                    PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
            List<CoreLabel> wordList = tokenizerFactory.getTokenizer(new StringReader(text)).tokenize();
            Tree tree = lp.apply(wordList);
            
            GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
            Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed(true);
            System.out.println(tdl);
            
           
            
            PrintWriter pw = new PrintWriter("H:\\Thesis Development\\Thesis\\NLP\\src\\nlp\\Text-Parsed.txt");
            TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
            tp.printTree(tree,pw);
            
            pw.close();
            Main.writeImage(tree, tdl, "H:\\Thesis Development\\Thesis\\NLP\\src\\nlp\\image.png", 3);
            assert (new File("image.png").exists());
        }catch(FileNotFoundException f)
        {
            
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    } // end of the main method
    
}