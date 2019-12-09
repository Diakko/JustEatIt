package com.example.justeatit;

/**
 * The class Calories represents calories of our app
 * @author Matias Hätönen
 */
public class Calories {
    private int dailyCount, totalCount, averageCount;

    /**
     * Constructor of Calories class
     * @param dailyCount int is used to set dailyCount
     * @param totalCount int is used to set totalCount
     * @param averageCount int is used to set averageCount
     */
    public Calories(int dailyCount, int totalCount, int averageCount){
        this.averageCount = averageCount;
        this.dailyCount = dailyCount;
        this.totalCount = totalCount;
    }

    /**
     * Method is used to add calories
     * @param calories int add the amount into dailyCount and totalCount
     */
    public void addCalories(int calories){
        this.dailyCount += calories;
        this.totalCount += calories;
    }

    /**
     * Method is used to reset dailyCount value to 0.
     */
    public void resetDailyCalories(){
        this.dailyCount = 0;
    }

    /**
     * Method is used to get int data of dailyCount
     * @return returns int value of dailyCount
     */
    public int getDailyCount(){
        return this.dailyCount;
    }

    /**
     * Method is used to get int data of totalCount
     * @return returns int value of totalCount
     */
    public int getTotalCount(){
        return this.totalCount;
    }

    /**
     * Method is used to get int data of averageCount
     * @return returns int value of totalCount
     */
    public int getAverageCount(){
        return this.averageCount;
    }

    /**
     * Method is used to get String data of averageCount
     * @return returns String value of averageCount
     */
    public String getAverageCountString(){
        return Integer.toString(this.averageCount);
    }

    /**
     * Method is used to get String data of totalCount
     * @return returns String value of totalCount
     */
    public String getTotalCountString(){
        return Integer.toString(this.totalCount);
    }

    /**
     * Method is used to get String data of dailyCount
     * @return returns String value of dailyCount
     */
    public String getDailyCountString(){
        return Integer.toString(this.dailyCount);
    }
}

