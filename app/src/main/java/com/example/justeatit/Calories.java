package com.example.justeatit;

/**
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
     * Method is used to add @param calories amount of calories into dailyCount and totalCount.
     */
    public void addCalories(int calories){
        this.dailyCount += calories;
        this.totalCount += calories;
    }

    /**
     * Method is used to reset dailyCount to 0.
     */
    public void resetDailyCalories(){
        this.dailyCount = 0;
    }

    /**
     * Method is used to get int data of dailyCount
     * @return returns int of dailyCount
     */
    public int getDailyCount(){
        return this.dailyCount;
    }

    /**
     * Method is used to get int data of totalCount
     * @return returns int of totalCount
     */
    public int getTotalCount(){
        return this.totalCount;
    }

    /**
     * Method is used to get int data of averageCount
     * @return returns int of totalCount
     */
    public int getAverageCount(){
        return this.averageCount;
    }

    /**
     * Method is used to get String data of averageCount
     * @return returns String of averageCount
     */
    public String getAverageCountString(){
        return Integer.toString(this.averageCount);
    }

    /**
     * Method is used to get String data of totalCount
     * @return returns String of totalCount
     */
    public String getTotalCountString(){
        return Integer.toString(this.totalCount);
    }

    /**
     * Method is used to get String data of dailyCount
     * @return returns String of dailyCount
     */
    public String getDailyCountString(){
        return Integer.toString(this.dailyCount);
    }
}

