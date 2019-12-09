package com.example.justeatit;

import androidx.annotation.NonNull;
/**
 * The class Ruoka represensts our items/foods in our app.
 * @author  Matias Hätönen
 */
public class Ruoka {
    private String ruoka;
    private int kalorit;
    private String aika;

    /**
     * Creates an object Ruoka to be saved into the arraylist<Ruoka>
     * Saves the values inputted in the call into the object
     * @param aika timestamp value from when the send button is pressed
     * @param ruoka food item which is inputted
     * @param kalorit The amount of calories inputted
     */
    public Ruoka(String aika, String ruoka, int kalorit){
        this.aika = aika;
        this.ruoka = ruoka;
        this.kalorit = kalorit;
    }

    /**
     * returns the food item as a String
     * @return return statement which returns the value of the foodItem to the arraylist and the screen as String values
     */
    public String getRuoka(){return this.ruoka;}

    /**
     * returns the calories value as a String
     * @return return statement which returns the amount of calories to the arraylist and the screen as String values
     */
    public String getKalorit(){return Integer.toString(this.kalorit);}

    /**
     * returns the timestamp value as a String
     * @return return statement which returns the value of the timestamp to the arraylist and the screen as String values
     */
    public String getAika(){return this.aika;}


    /**
     * toString method which returns a String value to the listview in profileActivity to be scrolled.
     */
    @NonNull
    @Override
    public String toString() {return "Timestamp: " + this.aika + " Food: " + this.ruoka;}
}
