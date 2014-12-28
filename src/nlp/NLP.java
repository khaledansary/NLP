/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nlp;

import Anaphora_Resolution.AnaphoraDetection;
import Anaphora_Resolution.Resolver;
import D3JS.Apectnode;
import D3JS.Documentnode;
import D3JS.Topicnode;
import D3JS.ViewTopic;
import DependencyParser.Parser;
import DependencyParser.RunStanfordParser;
import File.FileOperation;
import File.SplitintoSentences;
import JsonParser.JsonData;
import JsonParser.ParseJson;
import LSA.Lsi;
import NER.NERAnnotation;
import NGram.NGramExtractor;
import POSTagging.PosTagger;
import SentiStrength.SentiStrengthApp;
import Sentiwordnet.SentiWordNetFunction;
import StopWords.StopWords;
import TFIDF.MatrixTF;
import TFIDF.PostingList;
import TFIDF.TfIdf;
import TopicModeling.TopicModel;
import com.google.gson.Gson;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import static edu.stanford.nlp.util.logging.RedwoodConfiguration.Handlers.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;


/**
 *
 * @author khaledd
 */
public class NLP {
    
     private static StanfordCoreNLP pipeline;
     static FileOperation fb=new FileOperation();
     static SplitintoSentences sb = new SplitintoSentences();
     private static String datapath="H:\\Data\\dataforresearch\\";
     private static String datasentences="H:\\Data\\datasentences\\";
     private static List<String> listoffilename;
     private static List<String> listofsentences;
     public static final String REGEX = "([a-zA-Z]*_NN\\w?\\w?\\b)||([a-zA-Z]*_JJ\\w?\\w?\\b)||([a-zA-Z]*_DT\\w?\\w?\\b)||([a-zA-Z]*_NNS\\w?\\w?\\b)||([a-zA-Z]*_VBG\\w?\\w?\\b)";
     public static final String Rem_REGEX = "(_NN\\w?\\w?\\b)||(_JJ\\w?\\w?\\b)||(_DT\\w?\\w?\\b)||(_NNS\\w?\\w?\\b)||(_VBG\\w?\\w?\\b)";
     static String pathToSWN="H:\\Thesis Development\\Thesis\\NLP\\src\\Sentiwordnet\\SentiWordNet_3.0.0_20130122.txt";
     static SentiWordNetFunction sentiwordnet = new SentiWordNetFunction(pathToSWN);
     public static final String ADJ_REGEX = "([a-zA-Z]*_JJ\\w?\\w?\\b)||([a-zA-Z]*_JJS\\w?\\w?\\b)";
    /**
     * @param args the command line arguments
     * 
     */
     
    public static void main(String[] args) throws IOException {
        
        listoffilename = new ArrayList<String>();
        listofsentences = new ArrayList<String>();
        listoffilename=fb.readfile(datapath);
        String data="{\"version\":1,\n" +
"  \"groups\":[{\"context\":\"security departement\",\n" +
"  	      \"instances\":[{\"start\":2681,\"end\":2700,\"text\":\"Security Department\"}],\n" +
"  	      \"positives\":[{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":3948,\"end\":3973,\"text\":\"lot of the workforce here\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":4095,\"end\":4133,\"text\":\"very good on pay, terms and conditions\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":20609,\"end\":20665,\"text\":\"be fair, you don\\u0027t have favourites, you are approachable\"},{\"start\":22473,\"end\":22490,\"text\":\"belong to Airport\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":24919,\"end\":24954,\"text\":\"has to be a there and then decision\"}],\n" +
"  	      \"negatives\":[{\"start\":112,\"end\":125,\"text\":\"more pressure\"},{\"start\":331,\"end\":357,\"text\":\"don\\u0027t feel valued any more\"},{\"start\":613,\"end\":721,\"text\":\"feel that it doesn\\u0027t matter what they do, where they are nobody cares about what they\\u0027re doing really anyway\"},{\"start\":1228,\"end\":1237,\"text\":\"bad thing\"},{\"start\":1828,\"end\":1845,\"text\":\"somehow lost that\"},{\"start\":2589,\"end\":2623,\"text\":\"don\\u0027t have as much self-discipline\"},{\"start\":3184,\"end\":3215,\"text\":\"left to find out for themselves\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":3987,\"end\":4006,\"text\":\"doesn\\u0027t work for me\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":4201,\"end\":4222,\"text\":\"spoiling it basically\"},{\"start\":15375,\"end\":15383,\"text\":\"rejected\"},{\"start\":15420,\"end\":15446,\"text\":\"didn\\u0027t really support them\"},{\"start\":17465,\"end\":17531,\"text\":\"are not trusting either the Managers or supporting the trade union\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":23829,\"end\":23856,\"text\":\"complains about us bitterly\"},{\"start\":23864,\"end\":23891,\"text\":\"complain about him bitterly\"},{\"start\":24528,\"end\":24544,\"text\":\"obnoxious bugger\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"}],\n" +
"  	      \"aspects\":[{\"start\":90,\"end\":98,\"text\":\"security\"},{\"start\":324,\"end\":330,\"text\":\"People\"},{\"start\":606,\"end\":612,\"text\":\"people\"},{\"start\":1117,\"end\":1145,\"text\":\"management of telling people\"},{\"start\":1812,\"end\":1817,\"text\":\"rules\"},{\"start\":2574,\"end\":2588,\"text\":\"younger people\"},{\"start\":3159,\"end\":3174,\"text\":\"too many people\"},{\"start\":3914,\"end\":3923,\"text\":\"Airport S\"},{\"start\":3979,\"end\":3986,\"text\":\"airport\"},{\"start\":4065,\"end\":4090,\"text\":\"this company on the whole\"},{\"start\":4139,\"end\":4145,\"text\":\"people\"},{\"start\":15364,\"end\":15370,\"text\":\"ballot\"},{\"start\":15409,\"end\":15419,\"text\":\"shop-floor\"},{\"start\":17447,\"end\":17464,\"text\":\"shop-floor people\"},{\"start\":20225,\"end\":20249,\"text\":\"people on the shop-floor\"},{\"start\":22450,\"end\":22472,\"text\":\"Terminal Duty Managers\"},{\"start\":23826,\"end\":23828,\"text\":\"he\"},{\"start\":23861,\"end\":23863,\"text\":\"we\"},{\"start\":24498,\"end\":24509,\"text\":\"personality\"},{\"start\":24819,\"end\":24836,\"text\":\"nature of the job\"}]},{\"context\":\"management\",\"instances\":[{\"start\":4366,\"end\":4376,\"text\":\"management\"}],\n" +
"  	      \"positives\":[{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":6629,\"end\":6642,\"text\":\"firm and fair\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":7925,\"end\":7950,\"text\":\"used to love working here\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"}],\n" +
"  	      \"negatives\":[{\"start\":4530,\"end\":4543,\"text\":\"more militant\"},{\"start\":4837,\"end\":4873,\"text\":\"sort of tried to get that fence down\"},{\"start\":5046,\"end\":5050,\"text\":\"crap\"},{\"start\":5116,\"end\":5137,\"text\":\"wasn\\u0027t as bad as this\"},{\"start\":5364,\"end\":5371,\"text\":\"changed\"},{\"start\":5519,\"end\":5541,\"text\":\"changing for the worse\"},{\"start\":6288,\"end\":6314,\"text\":\"making people\\u0027s work worse\"},{\"start\":6492,\"end\":6502,\"text\":\"fairly lax\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":7768,\"end\":7784,\"text\":\"won\\u0027t listen now\"},{\"start\":7879,\"end\":7884,\"text\":\"shame\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":8006,\"end\":8017,\"text\":\". I hate it\"},{\"start\":8428,\"end\":8453,\"text\":\"has always been a problem\"},{\"start\":9534,\"end\":9558,\"text\":\"share all these problems\"},{\"start\":10827,\"end\":10875,\"text\":\"we ran around for a while like headless chickens\"},{\"start\":13268,\"end\":13279,\"text\":\"ill feeling\"}],\n" +
"  	      \"aspects\":[{\"start\":4516,\"end\":4526,\"text\":\"shop-floor\"},{\"start\":4664,\"end\":4670,\"text\":\"People\"},{\"start\":5016,\"end\":5036,\"text\":\"industrial relations\"},{\"start\":5101,\"end\":5107,\"text\":\"before\"},{\"start\":5348,\"end\":5358,\"text\":\"both sides\"},{\"start\":5582,\"end\":5610,\"text\":\"people don\\u0027t listen any more\"},{\"start\":6208,\"end\":6250,\"text\":\"changing the policy and the way of working\"},{\"start\":6471,\"end\":6481,\"text\":\"management\"},{\"start\":6657,\"end\":6678,\"text\":\"reasonable management\"},{\"start\":7758,\"end\":7767,\"text\":\"workforce\"},{\"start\":7824,\"end\":7843,\"text\":\"feeling of mistrust\"},{\"start\":7912,\"end\":7921,\"text\":\"long time\"},{\"start\":8018,\"end\":8021,\"text\":\"now\"},{\"start\":8414,\"end\":8427,\"text\":\"communication\"},{\"start\":9493,\"end\":9528,\"text\":\"workforce as much as the management\"},{\"start\":9801,\"end\":9812,\"text\":\"restructure\"},{\"start\":13071,\"end\":13077,\"text\":\"rumour\"}]},\n" +
"  	    {\"context\":\"Trade Unions\",\"instances\":[{\"start\":15500,\"end\":15512,\"text\":\"Trade Unions\"}],\n" +
"  	      \"positives\":[{\"start\":15603,\"end\":15625,\"text\":\"certainly have a place\"},{\"start\":16169,\"end\":16183,\"text\":\"make the union\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"}],\n" +
"  	      \"negatives\":[{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":16269,\"end\":16325,\"text\":\"lose sight of the fact that they are representing people\"},{\"start\":16644,\"end\":16665,\"text\":\"militant union people\"},{\"start\":17039,\"end\":17052,\"text\":\"are like that\"},{\"start\":17139,\"end\":17155,\"text\":\"problem straight\"}],\n" +
"  	      \"aspects\":[{\"start\":15648,\"end\":15659,\"text\":\"Trade Union\"},{\"start\":16156,\"end\":16163,\"text\":\"members\"},{\"start\":16234,\"end\":16255,\"text\":\"Union Representatives\"},{\"start\":16586,\"end\":16624,\"text\":\"problem between managements and unions\"},{\"start\":17027,\"end\":17038,\"text\":\"Politicians\"},{\"start\":17176,\"end\":17248,\"text\":\"can\\u0027t get your management and unions talking and listening to each other\"}]},{\"context\":\"new pay structure\",\"instances\":[{\"start\":17632,\"end\":17649,\"text\":\"new pay structure\"}],\n" +
"  	      \"positives\":[{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":19060,\"end\":19080,\"text\":\"realise this problem\"},{\"start\":19382,\"end\":19402,\"text\":\"operational business\"}],\n" +
"  	      \"negatives\":[{\"start\":17711,\"end\":17715,\"text\":\"Crap\"},{\"start\":17717,\"end\":17730,\"text\":\"Unfortunately\"},{\"start\":17978,\"end\":17988,\"text\":\"complacent\"},{\"start\":18266,\"end\":18284,\"text\":\"create bad feeling\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"},{\"start\":-100,\"end\":-100,\"text\":\"empty\"}],\n" +
"  	      \"aspects\":[{\"start\":17632,\"end\":17649,\"text\":\"new pay structure\"},{\"start\":17746,\"end\":17765,\"text\":\"very adverse affect\"},{\"start\":17918,\"end\":17936,\"text\":\"some of the people\"},{\"start\":17636,\"end\":17649,\"text\":\"pay structure\"},{\"start\":19049,\"end\":19059,\"text\":\"management\"},{\"start\":19322,\"end\":19329,\"text\":\"airport\"}]\n" +
"  	     }\n" +
"  	   ],\n" +
"  \"validationError\":false}";
       // PosTagger tag = new PosTagger();
       // NERAnnotation ner = new NERAnnotation();
        NGramExtractor ngram= new NGramExtractor();
        SentiStrengthApp sentiword = new SentiStrengthApp();
       // PostingList postlist = new PostingList();
                
        sentiword.SentiWordScore("bigger");
        sentiword.SentiWordScore("worse");
        sentiword.SentiWordScore("bad");
        sentiword.SentiWordScore("manage");
        sentiword.SentiWordScore("fine");
        
        ParseJson json = new ParseJson();
        List<JsonData> ls = new ArrayList<JsonData>();
        ls=json.parseJson(data);
        
        System.out.println("List......");
        for(JsonData a:ls)
        {
            if(a.getNegativeword().equals("empty"))
            {
                
                System.out.println(a.getTopic()+" :"+a.getAspect()+" :"+a.getPositiveword());
            }
            else{
                
                System.out.println(a.getTopic()+" :"+a.getAspect()+" :"+a.getNegativeword());
            }
                    
            
        }
       ViewTopic viewtopic = new ViewTopic();
        Gson gson = new Gson();
        
        List<Documentnode> doc = new ArrayList<Documentnode>();
        Documentnode document = new Documentnode();
        
        document.setTopic("Security Department");
        
        List<Topicnode> topic = new ArrayList<Topicnode>();
         
        List<Apectnode> asp=new ArrayList<Apectnode>();
        
        Apectnode aspect = new Apectnode();
        
        
        aspect.setWord("shop-floor");
        aspect.setSenti("postive");
        asp.add(aspect);
        
        
        Topicnode t = new Topicnode();
        t.setApect("Aspect 1");
        t.setApectnode(asp);
        topic.add(t);
        document.setTopicnode(topic);
        doc.add(document);
        
        document = new Documentnode();
        aspect = new Apectnode();
        aspect.setWord("People");
        aspect.setSenti("postive");
        asp.add(aspect);
        t = new Topicnode();
        t.setApect("Aspect 2");
        t.setApectnode(asp);
        topic.add(t);
        document.setTopicnode(topic);
        doc.add(document);
        
        document = new Documentnode();
        aspect = new Apectnode();
        aspect.setWord("management of telling people");
        aspect.setSenti("negative");
        asp.add(aspect);
        aspect = new Apectnode();
        aspect.setWord("mdd people");
        aspect.setSenti("positive");
        asp.add(aspect);
        t = new Topicnode();
        t.setApect("Aspect 3");
        t.setApectnode(asp);
        topic.add(t);
        document.setTopicnode(topic);
        doc.add(document);
        
        
         
        
        
        
       
        
        
        
        
        viewtopic.setDocument("doc 01");
        viewtopic.setDocumentnode(doc);
       
        
        System.out.println(gson.toJson(viewtopic));
        
        //AnaphoraDetection anotate = new AnaphoraDetection();
        
        //anotate.anophora();
        
  
        
      /*  Resolver resolver;
        resolver = new Resolver();
        try {
            resolver.loadProperNouns();
            resolver.loadActionNounsAndWordBases();
            resolver.readReferentsResolveAnaphora();
        }
        catch (Exception e) {
        System.err.println("Encountered problem -\n"+e);
        System.exit(0);
        }*/        
        
        //json.jsonParse("H:\\QualitativeData\\a.json");
        //pipeline=ner.initNER();
         /*TopicModel topic1 = new TopicModel();
         try {
                 //topic1.getTopic("H:/doc_sentence.txt");
                 topic1.getModelReady("H:/doc_sentence.txt");
                 topic1.applyLDA();
             } catch (Exception ex) {
                 Logger.getLogger(NLP.class.getName()).log(Level.SEVERE, null, ex);
             }*/
        /*for (String  filename: listoffilename) {
             
            System.out.println("file name: "+filename); 
            String filetxt=fb.ReadDocFiles(filename);
            listofsentences = sb.getSentences(filetxt);
          
            File sentencesfilefolder = new File(filename);
            System.out.println("Filename = " + datasentences+sentencesfilefolder.getName());
            StopWords stopwords = new StopWords();
            String doc_sentences="",clean_str="";
          
            for (String  sentence: listofsentences) {
                System.out.println(sentence);
                doc_sentences+=sentence;
                clean_str= stopwords.removeStopwords(sentence);
                
              //  String tag_text=tag.TagingText(sentence);
                //System.out.println(tag_text);
                
            }
            fb.CreateFolder(datasentences+sentencesfilefolder.getName());   
            fb.WriteIntoFile(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName(),clean_str);
            
          //  RunStanfordParser r = new RunStanfordParser(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName());
            RunStanfordParser r = new RunStanfordParser(filename);
            try {
                 topic.getTopic(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName());
             } catch (Exception ex) {
                 Logger.getLogger(NLP.class.getName()).log(Level.SEVERE, null, ex);
             }
            
             
        //runPythoncode();
       // MatrixTF   ms = new MatrixTF();
        //ms.cal_TF(postlist, "H:/Data/datasentences/doc_01.txt");
         //postlist.createPList("H:/Data/datasentences/doc_01.txt");
       /* Lsi lsi = new Lsi();
        
         try {
             lsi.getLSI();
         } catch (Exception ex) {
             Logger.getLogger(NLP.class.getName()).log(Level.SEVERE, null, ex);
         }
               
        } */
    }
    public static void runPythoncode()
    {
        StringWriter writer = new StringWriter(); //ouput will be stored here

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptContext context = new SimpleScriptContext();

        context.setWriter(writer); //configures output redirection
        ScriptEngine engine = manager.getEngineByName("python");
         try {
             engine.eval(new FileReader("H:\\Python code\\TDMatrix.py"), context);
         } catch (ScriptException ex) {
             Logger.getLogger(NLP.class.getName()).log(Level.SEVERE, null, ex);
         } catch (FileNotFoundException ex) {
             Logger.getLogger(NLP.class.getName()).log(Level.SEVERE, null, ex);
         }
        System.out.println(writer.toString()); 
    }
    public static void getTFIDF(String filepath)
    {
        TfIdf tf = new TfIdf(filepath);
		String word;
		Double[] corpusdata;
		for (Iterator<String> it = tf.allwords.keySet().iterator(); it.hasNext(); ) {
			word = it.next();
			corpusdata = tf.allwords.get(word);
			System.out.println(word + " " + corpusdata[0] + " " + corpusdata[1]);
		}	
		tf.buildAllDocuments();
		String[] bwords;
		String[] bdocs;
		for (Iterator<String> it = tf.documents.keySet().iterator(); it.hasNext(); ) {
			word = it.next();
			System.out.println(word);
			System.out.println("------------------------------------------");
			bwords = tf.documents.get(word).bestWordList(5);
			bdocs = tf.similarDocuments(word);
			for (int i = 0; i < 5; i++) {
				System.out.print(bwords[i] + " ");
			}
			System.out.println();
			for (int i = 0; i < 5; i++) {
				System.out.println(bdocs[i] + " ");
			}
			System.out.println("\n\n");
		}
    }
    public static void SentimentWordCollect()
    {
        
        
        /*
            // String tag_text=tag.TagingText(sentence);
                // pos_sentences +=tag_text+"\n";
                 //pos_sentences_for_words +=tag_text;
                
                 
                 
                // pos_words.add(tag.ExtractWordsByRegex(tag_text,REGEX));
                // ner_sentences +="{"+ner.MultiNER(sentence, pipeline)+"}"+"\n";
            
        */
        //   fb.WriteIntoFile(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName()+"_NER","{"+ner.MultiNER(sentence, pipeline)+"}"+"\n");
        /*
        //pos_words=tag.ExtractWordsByRegex(pos_sentences,REGEX);
            //opinioin_words=tag.ExtractAdjWordsByRegex(pos_sentences,ADJ_REGEX);
            //System.out.println("pos sentences:" +pos_sentences);
            //fb.CreateFolder(datasentences+sentencesfilefolder.getName());
            //fb.WriteIntoFile(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName(),doc_sentences);
            
             
                 
            //fb.WriteIntoFile(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName()+"_POS",pos_sentences);
            sentiword.SentiWordScore(doc_sentences);
            
            
            //RunStanfordParser r = new RunStanfordParser(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName());
            String important_words="";
            for(String words: pos_words)
            {
                /*(try{
                    Double score = sentiwordnet.extract(words.replaceAll("_NN\\w?\\w?\\b", ""),"a");
                    important_words +=words+"  "+ score +"\n";
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }*/
           /*     important_words +=words+"\n";
                
            }
            
            
            fb.WriteIntoFile(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName()+"_WORD",important_words);
            
            String sentiment_words="";
            for(String words: opinioin_words)
            {
                /*(try{
                    Double score = sentiwordnet.extract(words.replaceAll("_NN\\w?\\w?\\b", ""),"a");
                    important_words +=words+"  "+ score +"\n";
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }*/
             /*   sentiment_words +=words+"\n";
                
            }
            
            fb.WriteIntoFile(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName()+"_sintiwords",sentiment_words);
            
            List<String> ngramwords = new ArrayList<String>();
            ngramwords = ngram.extract(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName(),2,4);
            
            String ngramwordlist="";
            
            for(String nword: ngramwords)
            {
                ngramwordlist +=nword+"\n";
            }
            
            fb.WriteIntoFile(datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName()+"_NGramWords",ngramwordlist);
            
            
            String filePath = datasentences+sentencesfilefolder.getName()+"\\"+sentencesfilefolder.getName();
        
            TopicModel topic = new TopicModel();
             try {
                 topic.getTopic(filePath);
             } catch (Exception ex) {
                 Logger.getLogger(NLP.class.getName()).log(Level.SEVERE, null, ex);
             }
 
        // TODO code application logic here
        /*String pathToSWN="H:\\Thesis Development\\Thesis\\NLP\\src\\Sentiwordnet\\SentiWordNet_3.0.0_20130122.txt";
        SentiWordNetFunction sentiwordnet = new SentiWordNetFunction(pathToSWN);
    
        System.out.println("good#a "+sentiwordnet.extract("good", "a"));
        System.out.println("bad#a "+sentiwordnet.extract("bad", "a"));
        System.out.println("blue#a "+sentiwordnet.extract("blue", "a"));
        System.out.println("blue#n "+sentiwordnet.extract("blue", "n"));
        
        System.out.println("excellent#n "+sentiwordnet.extract("excellent", "a"));
        System.out.println("help#v "+sentiwordnet.extract("help", "v"));
        
        String sample = "My name is Khaled Ansary. This is a sample  1920 text. I love Bangladesh. I hate  New Work. The VW is good";
        
        PosTagger tag = new PosTagger();
        System.out.println(tag.TagingText(sample));
        
        Parser parser = new Parser();
        
        try{
            parser.CallParser("A quick brown fox jumped over the lazy dog.");
        }catch(Exception e)
        {
            System.out.println(e);
        }
        NERAnnotation ner = new NERAnnotation();
        pipeline=ner.initNER();
        ner.MultiNER(sample, pipeline);
      //  ner.GermanNER(sample, pipeline);*/
        //String filePath = "H:\\Data\\topicmodel\\ap.txt";
       
        /**/
            
    }
    
}
