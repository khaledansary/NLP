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


public class ViewTopic {

@SerializedName("name")
private String document;

@SerializedName("children")
private List<Documentnode> documentnode = new ArrayList<Documentnode>();


/**
* 
* @return
* The document
*/
public String getDocument() {
return document;
}

/**
* 
* @param document
* The document
*/
public void setDocument(String document) {
this.document = document;
}

/**
* 
* @return
* The documentnode
*/
public List<Documentnode> getDocumentnode() {
return documentnode;
}

/**
* 
* @param documentnode
* The documentnode
*/
public void setDocumentnode(List<Documentnode> documentnode) {
this.documentnode = documentnode;
}


}