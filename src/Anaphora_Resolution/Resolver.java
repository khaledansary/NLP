/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Anaphora_Resolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author khaledd
 */
public class Resolver {
    
    HashSet maleNames, femaleNames;
Hashtable actionNouns, wordBases;
static String synonyms;
// referent entities/actions that are not refered to for very long in the
// text (say 15-20 sentences) are removed from consideration in anaphora
// resolution; not used for now though.
int purge_referents_every_this_many_words;
// ----------------------------------------------------------------------
public void loadProperNouns() throws IOException, FileNotFoundException {
    maleNames = new HashSet();
    femaleNames = new HashSet();
    String line, token;
    // File names of lists are hard coded to avoid unnecessary flexibility
    // Files are assumed to contain one name per line and possibly white
    // space but nothing else
    File file = new File("males.names");
    BufferedReader in = new BufferedReader( new FileReader(file) );
    while ( (line=in.readLine()) != null ) {
        token = line.trim();
        maleNames.add( token );
    }
    maleNames.add("man"); maleNames.add("boy");
    maleNames.add("male");
    file = new File("females.names");
    in = new BufferedReader( new FileReader(file) );
    while ( (line=in.readLine()) != null ) {
        token = line.trim();
        femaleNames.add( token );
    }
    femaleNames.add("woman"); femaleNames.add("girl");
    femaleNames.add("female");
}
// ----------------------------------------------------------------------
public void loadActionNounsAndWordBases() throws IOException, FileNotFoundException {
    actionNouns = new Hashtable();
    String line, key, value;
    // File names of lists are hard coded to avoid unnecessary flexibility
    // Files are assumed to contain one noun-verb pair per line and possibly
    // white space but nothing else
    File file = new File("action.nouns");
    BufferedReader in = new BufferedReader( new FileReader(file) );
    while ( (line=in.readLine()) != null ) {
        StringTokenizer st = new StringTokenizer(line);
        if ( st.countTokens() == 2 ) {
            key = st.nextToken();
            value = st.nextToken();
            actionNouns.put( key, value );
        }
    }
    wordBases = new Hashtable();
    file = new File("word.bases");
        in = new BufferedReader( new FileReader(file) );
        while ( (line=in.readLine()) != null ) {
            StringTokenizer st = new StringTokenizer(line);
            if ( st.countTokens() == 2 ) {
                key = st.nextToken();
                value = st.nextToken();
                wordBases.put( key, value );
        }
    }
}
// ----------------------------------------------------------------------
public void printTestFile() throws IOException, FileNotFoundException {
    String line;
    // File name for the sentence file is hard coded to 'resolve.sentences'
    System.out.println( "\nThe discourse:");
    System.out.println( "--------------");
    File file = new File("resolve.sentences");
    BufferedReader in = new BufferedReader( new FileReader(file) );
    while ( (line=in.readLine()) != null ) {
        System.out.println( line );
    }
    System.out.println( "" );
}
// ----------------------------------------------------------------------
public void printLexicalData() {
    String token;
    Iterator it;
    System.out.println("Repository of male names:");
    it = femaleNames.iterator();
    while ( it.hasNext() ) {
        token = (String)it.next();
        System.out.println(token);
    }
    System.out.println("Repository of female names:");
    it = femaleNames.iterator();
    while ( it.hasNext() ) {
    token = (String)it.next();
    System.out.println(token);
    }
    String verb, noun;
    Enumeration e = actionNouns.keys();
    while ( e.hasMoreElements() ) {
    noun = (String)e.nextElement();
    verb = (String)actionNouns.get( noun );
    System.out.println( verb+"<->"+noun );
    }
    }
    // ----------------------------------------------------------------------
    int essenceSynonymity(String word) throws IOException {
    String w = (String)wordBases.get(word); // e.g. tried -> try
    if (w==null) w=word; // essence is the same as the word
    int index = synonyms.indexOf(w);
    return index;
    }
    // ----------------------------------------------------------------------
    void readVerbSynonymsOfActionNoun(String word)
    throws IOException, InterruptedException {
    String w = (String)actionNouns.get(word);
    if (w==null) return;
    Runtime rt = Runtime.getRuntime();
    Process p = rt.exec("wn "+ w +" -synsv");
    p.waitFor();
    InputStream in = p.getInputStream();
    byte[] byte_arr = new byte[in.available()];
    in.read( byte_arr );
    String process_output = new String(byte_arr);
    synonyms = process_output; // for use in essenceSynonymity()
    }
    // ----------------------------------------------------------------------
    public void readReferentsResolveAnaphora()
    throws IOException, FileNotFoundException, InterruptedException {
    // Each sentence in the original file is supposed to be terminated by
    // a period i.e. '.'
    // Assumes that NN occurs as '(NN <string>)'; similar for NNS, NNP
    // Allows expressions of the form "DT [JJ]* [NN|NNS|NNP]"
    try {
    printTestFile();
    }
    catch (Exception e) {
    System.err.println("Could not read file\n"+e);
    System.exit(0);
    }
    Vector referents = new Vector();
    Vector anaphora = new Vector();
    String line, token, sentence, referent, next;
    String det = "";
    sentence = new String(""); referent = new String("");
    token = new String("");
    File file = new File("test.tree");
    BufferedReader in = new BufferedReader( new FileReader(file) );
    char mode = 0;
    System.out.println("\nThe anaphora used in the discourse, in "
    + "the order of appearance,\nand the actions/entities "
    + "they refer to:");
    System.out.println("-----------------------------------------");
    System.out.println("anaphora -> referent");
    System.out.println("--------------------");
    while ( (line=in.readLine()) != null ) {
    sentence += line.trim();
    if ( line.indexOf('.') > -1 ) { // sentence terminates
    anaphora.clear();
    // Collect all the referents in this sentence in two parses of
    // the tree description
    // Collect all the anaphora in the first of these parses
    String tag="";
    // 'true' here indicates that delimiters are to be returned
    StringTokenizer st = new StringTokenizer(sentence," )(",true);
    boolean dont_read = false; char num='s';
    while ( st.hasMoreTokens() || dont_read ) {
    if (!dont_read) token = st.nextToken();
    dont_read = false;
    while (token.equals("(")) {
    // get the next token until a pos tag
    token = st.nextToken();
    tag = token;
    }
    if (token.equals("DT")) {
    // will collect until the next "NN|NNS"
    mode = 'd';
    }
    // option 2:
    else if ( token.equals("NN") || token.equals("NNS")
    || token.equals("NNP") ) {
    num = 's';
    if ( token.equals("NNS") ) num = 'p';
    token = st.nextToken(); // space ' '
    token = st.nextToken();
    if ( mode == 'd' ) {
    if (referent.length()>0) referent += " ";
    referent += token;
    mode=0;
    if ( det.equals("the") || det.equals("The") ) {
    Referent a = new Referent(referent);
    a.head = token;
    a.number = num;
    a.ltp = 0.5f+referents.size();
    if ( actionNouns.get(token) != null ) {
    a.type = 'a';
    }
    anaphora.add( a );
    // considered an anaphora, not referent,
    // if determiner 'the' is used
    referent = "";
    }
    }
    else referent = token;
    }
    // keep it last option
    else if ( !token.equals(")") && !token.equals(" ")
    && st.hasMoreTokens() ) {
    next = st.nextToken();
    if ( next.equals(")") ) {
    // token is a word in the sentence
    if ( mode == 'd' ) {
    if (referent.length()>0) referent += " ";
    referent += token;
    }
    if ( tag.equals("DT") ) {
    det = token;
    }
    else if ( tag.equals("PRP")
    || tag.equals("PRP$") ) {
    Referent a = new Referent(token);
    a.head = token;
    a.ltp = 0.5f+referents.size();
    if ( token.equals("his") || token.equals("he")
    || token.equals("His")
    || token.equals("He") ) {
    a.gender = 'M';
    }
    else if ( token.equals("her")
    || token.equals("she")
    || token.equals("Her")
    || token.equals("She") ) {
    a.gender = 'F';
    }
    else if ( token.equals("they")
    || token.equals("they're")
    || token.equals("them")
    || token.equals("their")) {
    a.number = 'p';
    }
    anaphora.add( a );
    } // if(PRP)
    }
    else {
    token = next;
    dont_read = true;
    }
    }
    if ( mode==0 && referent.length()>0 ) {
    Referent r = new Referent(referent);
    r.head = token; // coming from option 2
    r.number = num;
    r.ltp = referents.size();
    if ( maleNames.contains(token) ) r.gender = 'M';
    if ( femaleNames.contains(token) ) {
    if (r.gender == 'M') r.gender = 'U';
    else r.gender = 'F';
    }
    referents.add( r );
    referent = "";
    }
    }
    // second pass through the sentence; in search of verb phrases
    st = new StringTokenizer(sentence," )(",true);
    dont_read = false; mode = 0; referent = "";
    int count=0; tag=""; String head="";
    while ( st.hasMoreTokens() || dont_read ) {
    if (!dont_read) token = st.nextToken();
    dont_read = false;
    while (token.equals("(")) {
    // get the next token until a pos tag
    if (mode=='v') count++;
    token = st.nextToken();
    tag = token;
    }
    if (token.equals("VP")) {
    // will collect until VP is completed
    mode = 'v';
    count = 1;
    }
    else if ( mode == 'v' && token.equals(")") ) {
    count--;
    }
    // keep it last option
    else if ( mode == 'v' && !token.equals(")")
    && !token.equals(" ") && st.hasMoreTokens() ) {
    next = st.nextToken();
    if ( next.equals(")") ) {
    if (referent.length()>0) referent += " ";
    referent += token;
    count--;
    // more verb forms can be added
    if ( tag.equals("VB") || tag.equals("VBD")
    || tag.equals("VBZ") || tag.equals("VBP") ) {
    if ( token.equals("is") || token.equals("was")
    || token.equals("are")
    || token.equals("were") ) {
    referent = "";
    mode = 0;
    }
    else if ( head.length()==0 ) { // head of the
    // verb phrase head of the verb phrase is
    // assumed to occur before all other verbs
    // in the phrase
    head = token;
    }
    }
    }
    else {
    token = next;
    dont_read = true;
    }
    }
    if ( count==0 && referent.length()>0 ) {
    Referent r = new Referent(referent);
    r.head = head;
    r.type = 'a';
    r.ltp = referents.size();
    referents.add( r );
    referent = "";
    mode = 0;
    head="";
    }
    }
    sentence = "";
    // Now resolve all the anaphora encountered in this sentence
    // Necessary data is in the vector anaphora and referents
    for (int i=0; i<anaphora.size(); i++) {
    Referent an = (Referent)anaphora.elementAt(i);
    if ( an.type == 'a' ) {
    readVerbSynonymsOfActionNoun(an.head);
    }
    float best_prior = 100;
    Referent best_r = null;
    for (int j=referents.size()-1; j>=0; j--) {
    Referent rf = (Referent)referents.elementAt(j);
    float prior = Math.abs(rf.ltp-an.ltp);
    if ( rf.type == an.type && rf.gender == an.gender
    && rf.number == an.number ) {
    if ( rf.type == 'a' ) {
    // reward for synonymity
    if (essenceSynonymity(rf.head)>-1) {
    // 10 is an arbitrary amount
    prior -= 10;
    }
    }
    // proximity as a preference; an antecedent is given
    // priority over a post-cedent when tie occurs
    if ( prior<=best_prior ) {
    best_r = rf;
    best_prior = prior;
    }
    }
    }
    if ( best_r == null ) {
    System.out.println( " " + an.phrase + " -> unknown!" );
    }
    else {
    System.out.println( " " + an.phrase
    + " -> " + best_r.phrase );
    }
    }
    } // if (sentence_terminated)
    } // while(line)
    System.out.println("\nThe referents (entities and actions) mentioned");
    System.out.println("in the discourse, in the order of appearance:");
    System.out.println("---------------------------------------------");
    for (int j=0; j<referents.size(); j++) {
        Referent rf = (Referent)referents.elementAt(j);
        rf.print();
    }
    System.out.println("");
    } // readReferentsResolveAnaphora()
} // class Resolver
// ----------------------------------------------------------------------
    
