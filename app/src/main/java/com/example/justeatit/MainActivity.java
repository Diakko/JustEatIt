package com.example.justeatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView dailystepcounter;
    SensorManager sensorManager;
    boolean running = false;

    private final static String DAILY_CALORIES_KEY = "Daily_calories";
    private final static String TOTAL_CALORIES_KEY = "Total_Calories";
    private final static String AVERAGE_CALORIES_KEY = "Average_Calories";
    private final static String DAILY_STEPS_KEY = "Daily_Steps";
    public static final String EXTRA_MESSAGE = "Profile_activity1";
    public static final String EXTRA_MESSAGE1 = "Profile_activity2";
    public static final String EXTRA_MESSAGE2 = "Profile_activity3";
    public static final String EXTRA_MESSAGE3 = "profile_activity4";
    private int dailyCal,totalCal, averageCal;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.JustEatIt";
    private Calories calories;
    private Ruoat ruoat;
    ArrayList<Ruoat> Ruoat = new ArrayList<>();
    StepCounter dailysteps = new StepCounter(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        dailyCal = mPreferences.getInt(DAILY_CALORIES_KEY, 0);
        totalCal = mPreferences.getInt(TOTAL_CALORIES_KEY, 0);
        averageCal = mPreferences.getInt(AVERAGE_CALORIES_KEY, 0);
        calories = new Calories(dailyCal, totalCal, averageCal);
        dailysteps.setValue(mPreferences.getInt(DAILY_STEPS_KEY, 0));
        dailystepcounter = (TextView) findViewById(R.id.dailystepcounter);
        updateUI();



    }
    protected void onPause() {
        super.onPause();
        running = false;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(DAILY_CALORIES_KEY, calories.getDailyCount());
        preferencesEditor.putInt(TOTAL_CALORIES_KEY, calories.getTotalCount());
        preferencesEditor.putInt(AVERAGE_CALORIES_KEY, calories.getAverageCount());
        preferencesEditor.putInt(DAILY_STEPS_KEY, dailysteps.stepsNow());
        preferencesEditor.apply();

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetCaloriesButtonPressed(View v) {
        Log.i("Button", "Reset-button pressed");
        calories.resetDailyCalories();
        dailysteps.reset();
        updateUI();
    }

    public void sendButtonPressed(View v){
        Log.i("Button", "Lähetä-nappia painettu");
        EditText editText = findViewById(R.id.foodBar);
        String ruoka = editText.getText().toString();
        EditText editText1 = findViewById(R.id.insertCalories);
        int caloriesSent = Integer.parseInt(editText1.getText().toString());
        calories.addCalories(caloriesSent);
        updateUI();
    }

    public void profileButtonPressed(View v){
        Log.i("Button", "Profiili-nappia painettu");
        Intent intent = new Intent(this, ProfileActivity.class);
        TextView tv1 = findViewById(R.id.caloriesTodayCount);
        String message1 = calories.getDailyCountString();
        String message2 = calories.getTotalCountString();
        String message3 = calories.getAverageCountString();
        String dailystepcount = "" + dailysteps.stepsNow();
        intent.putExtra(EXTRA_MESSAGE, message1);
        intent.putExtra(EXTRA_MESSAGE1, message2);
        intent.putExtra(EXTRA_MESSAGE2, message3);
        intent.putExtra(EXTRA_MESSAGE3, dailystepcount);
        startActivity(intent);
    }

    public void updateUI(){
        TextView tv1 = findViewById(R.id.caloriesTodayCount);
        tv1.setText(calories.getDailyCountString());
        dailystepcounter.setText(""+dailysteps.stepsNow());

    }

    //Lisää askeleen aina kun puhelimen sensori huomaa askeleen
    @Override
    public void onSensorChanged(SensorEvent event) {
        dailysteps.addStep();
        dailystepcounter.setText(""+dailysteps.stepsNow());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
