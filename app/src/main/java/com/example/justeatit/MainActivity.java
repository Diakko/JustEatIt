package com.example.justeatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private final static String DAILY_CALORIES_KEY = "Daily_calories";
    private final static String TOTAL_CALORIES_KEY = "Total_Calories";
    private final static String AVERAGE_CALORIES_KEY = "Average_Calories";
    public static final String EXTRA_MESSAGE = "Profile_activity";
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
        updateUI();



    }
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(DAILY_CALORIES_KEY, calories.getDailyCount());
        preferencesEditor.putInt(TOTAL_CALORIES_KEY, calories.getTotalCount());
        preferencesEditor.putInt(AVERAGE_CALORIES_KEY, calories.getAverageCount());

        updateUI();
    }

    public void resetCaloriesButtonPressed(View v) {
        Log.i("Button", "Reset-button pressed");
        calories.resetDailyCalories();
        updateUI();
    }

    public void sendButtonPressed(View v){
        Log.i("Button", "Lähetä-nappia painettu");
        EditText editText = findViewById(R.id.foodBar);
        String message = editText.getText().toString();
        EditText editText1 = findViewById(R.id.insertCalories);
        int caloriesSent = Integer.parseInt(editText1.getText().toString());
        calories.addCalories(caloriesSent);
        updateUI();
    }

    public void profileButtonPressed(View v){
        Log.i("Button", "Profiili-nappia painettu");
        Intent intent = new Intent(this, ProfileActivity.class);
        TextView tv1 =findViewById(R.id.caloriesCount);
        String message = tv1.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void updateUI(){
        TextView tv1 =findViewById(R.id.caloriesCount);
        tv1.setText(calories.getDailyCountString());
    }
}
