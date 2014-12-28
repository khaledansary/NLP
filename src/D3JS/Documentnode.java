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


public class Documentnode {

@SerializedName("name")
private String topic;

@SerializedName("children")
private List<Topicnode> topicnode = new ArrayList<Topicnode>();


/**
* 
* @return
* The topic
*/
public String getTopic() {
return topic;
}

/**
* 
* @param topic
* The topic
*/
public void setTopic(String topic) {
this.topic = topic;
}

/**
* 
* @return
* The topicnode
*/
public List<Topicnode> getTopicnode() {
return topicnode;
}

/**
* 
* @param topicnode
* The topicnode
*/
public void setTopicnode(List<Topicnode> topicnode) {
this.topicnode = topicnode;
}



}