/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SentiStrength;

/**
 *
 * @author khaledd
 */

import uk.ac.wlv.sentistrength.*;

public class SentiStrengthApp {
    
    public static int classificateSentiment(String received) {
		String[] values = received.split(" ");
		int v1 = Integer.valueOf(values[0]);
		int v2 = Integer.valueOf(values[1]);
		int result = v1+v2;
		return result;
	}
	
    
    public void SentiWordScore(String str) {
    //Method 1: one-off classification (inefficient for multiple classifications)
    //Create an array of command line parameters, including text or file to process
       // String ssthInitialisationAndText[] = {"sentidata", "c:/SentStrength_Data/", "text", "Love", "explain"};
        //SentiStrength.main(ssthInitialisationAndText); 

        //Method 2: One initialisation and repeated classifications
        SentiStrength sentiStrength = new SentiStrength(); 
        //Create an array of command line parameters to send (not text or file to process)
        String ssthInitialisation[] = {"sentidata", "c:/SentStrength_Data/", "explain"};
        sentiStrength.initialise(ssthInitialisation); //Initialise
        //can now calculate sentiment scores quickly without having to initialise again
        System.out.println(sentiStrength.computeSentimentScores(str)+" Opinion is: "+classificateSentiment(sentiStrength.computeSentimentScores(str))); 
       /* System.out.println(sentiStrength.computeSentimentScores("Good")+" Opinion is: "+classificateSentiment(sentiStrength.computeSentimentScores("Good"))); 
        System.out.println(sentiStrength.computeSentimentScores("Unpredictable")+" Opinion is: "+classificateSentiment(sentiStrength.computeSentimentScores("Unpredictable"))); 
        System.out.println(sentiStrength.computeSentimentScores("bad")+" Opinion is: "+classificateSentiment(sentiStrength.computeSentimentScores("bad"))); 
        System.out.println(sentiStrength.computeSentimentScores("Mobile Streen is large")+" Opinion is: "+classificateSentiment(sentiStrength.computeSentimentScores("Mobile Screen is large"))); 
        System.out.println(sentiStrength.computeSentimentScores("Live")+" Opinion is: "+classificateSentiment(sentiStrength.computeSentimentScores("Live"))); 
        System.out.println(sentiStrength.computeSentimentScores("small")+" Opinion is: "+classificateSentiment(sentiStrength.computeSentimentScores("small"))); 
      */
      
    }
}
