package com.example.justeatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final static String DAILY_CALORIES_KEY = "Daily_calories";
    private final static String TOTAL_CALORIES_KEY = "Total_Calories";
    private final static String AVERAGE_CALORIES_KEY = "Average_Calories";
    private int dailyCal,totalCal, averageCal;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.JustEatIt";
    private Calories calories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        dailyCal = mPreferences.getInt(DAILY_CALORIES_KEY, 0);
        totalCal = mPreferences.getInt(TOTAL_CALORIES_KEY, 0);
        averageCal = mPreferences.getInt(AVERAGE_CALORIES_KEY, 0);
        calories = new Calories(dailyCal, totalCal, averageCal);



    }
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(DAILY_CALORIES_KEY, calories.getDailyCount());
        preferencesEditor.putInt(TOTAL_CALORIES_KEY, calories.getTotalCount());
        preferencesEditor.putInt(AVERAGE_CALORIES_KEY, calories.getAverageCount());
        preferencesEditor.apply();
    }
    ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private void resetCalories() {

    }
}
