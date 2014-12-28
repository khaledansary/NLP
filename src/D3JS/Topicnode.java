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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;


public class Topicnode {

@SerializedName("name")
private String apect;

@SerializedName("children")
private List<Apectnode> apectnode = new ArrayList<Apectnode>();


private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
private Integer size;


/**
* 
* @return
* The apect
*/
public String getApect() {
return apect;
}

/**
* 
* @param apect
* The apect
*/
public void setApect(String apect) {
this.apect = apect;
}

/**
* 
* @return
* The apectnode
*/

public List<Apectnode> getApectnode() {
return apectnode;
}

/**
* 
* @param apectnode
* The apectnode
*/
public void setApectnode(List<Apectnode> apectnode) {
this.apectnode = apectnode;
}



/**
* 
* @return
* The size
*/
public Integer getSize() {
return size;
}

/**
* 
* @param size
* The size
*/
public void setSize(Integer size) {
this.size = size;
}

}