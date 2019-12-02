package com.example.justeatit;

public class Calories {
    private int dailyCount, totalCount, averageCount;
    public Calories(int dailyCount, int totalCount, int averageCount){
        this.averageCount = averageCount;
        this.dailyCount = dailyCount;
        this.totalCount = totalCount;
    }
    public void addCalories(int calories){
        this.dailyCount += calories;
        this.totalCount += calories;
    }
    public void resetDailyCalories(){
        this.dailyCount = 0;
    }

    public int getDailyCount(){
        return this.dailyCount;
    }

    public int getTotalCount(){
        return this.totalCount;
    }
    public int getAverageCount(){
        return this.averageCount;
    }
    public String getAverageCountString(){
        return Integer.toString(this.averageCount);
    }
    public String getTotalCountString(){
        return Integer.toString(this.totalCount);
    }
    public String getDailyCountString(){
        return Integer.toString(this.dailyCount);
    }
}
