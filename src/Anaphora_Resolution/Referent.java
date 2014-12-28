/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Anaphora_Resolution;

import java.util.Vector;

/**
 *
 * @author khaledd
 */
class Referent extends Object {
    // the most comprehensive (ideally) phrase describing the referent in the
    // text
    String phrase;
    // the noun of verb head representing the referent
    String head;
    // 'M', 'F', 'N' for neutral, 'U' for unknown;
    // 8 bits allow for more genders in the future -;)
    char gender;
    char number; // 's' for single, 'p' for multiple
    // 'e' for entity, 'a' for action/verb
    char type;
    // gender, number and type are constraint variables; they need to strictly
    // match between the referent and anaphora
    // the position in the text where it was last referred to; apart from
    // helping in determining that it is too old to keep in the
    // database/records, it can also be used to determine proximity to the
    // anaphora not used in this version
    // Referents have integer values; anaphora have values of the form n+1/2
    float ltp;
    // for future use
    int first_touch_index;
    Vector attributes; // vector of strings, attribute names; e.g. 'color',
    // 'size', 'park' (as value of the location attrib.)
    // etc. not used in the code for now.
    public Referent(String p) {
    // default values
    phrase = p;
    gender = 'N';
    number = 's';
    type = 'e';
    }
    public void print() {
    if (gender=='M') System.out.print(" male");
    else if (gender=='F')System.out.print(" female");
    if (number=='p') System.out.print(" plural");
    if (type=='a') System.out.print(" action: ");
    else System.out.print(" entity: ");
    System.out.println( phrase );
    }
    }
    // ----------------------------------------------------------------------
    class Num extends Object {
    private float val_;
    public Num( float v ) {
    val_ = v;
    }
    public void incr() { val_++; }
    public void decr() { val_--; }
    public void add(float v) { val_ += v; }
    public void value(float v) { val_ = v; }
    public float value() { return val_; }
    }