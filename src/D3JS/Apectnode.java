/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package D3JS;

/**
 *
 * @author khaledd
 */

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;



public class Apectnode {

@SerializedName("name")
private String word;
private String senti;


/**
* 
* @return
* The word
*/
public String getWord() {
return word;
}

/**
* 
* @param word
* The word
*/
public void setWord(String word) {
this.word = word;
}

/**
* 
* @return
* The senti
*/
public String getSenti() {
return senti;
}

/**
* 
* @param senti
* The senti
*/
public void setSenti(String senti) {
this.senti = senti;
}



}