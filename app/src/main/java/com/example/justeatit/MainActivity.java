package com.example.justeatit;

import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * (C) Matias Hätönen & Samuel Ahjoniemi
 */

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView dailystepcounter;
    SensorManager sensorManager;
    boolean running = false;

    private final static String DAILY_CALORIES_KEY = "Daily_calories";
    private final static String TOTAL_CALORIES_KEY = "Total_Calories";
    private final static String AVERAGE_CALORIES_KEY = "Average_Calories";
    private final static String ARRAYLIST_KEY = "Arraylist";
    private final static String DAILY_STEPS_KEY = "Daily_Steps";
    public static final String EXTRA_MESSAGE = "Profile_activity1";
    public static final String EXTRA_MESSAGE1 = "Profile_activity2";
    public static final String EXTRA_MESSAGE2 = "Profile_activity3";
    private final static String TIME_OF_CLICK = "Time_of_click";
    private final static String ERROR_MESSAGE = "Error";
    private final static String TOTAL_STEPS_KEY = "Total_Steps";
    /**
     * Making a save-file for few values in SharedPreferences
     */
    public static final String EXTRA_MESSAGE3 = "profile_activity4";
    public static final String EXTRA_MESSAGE4 = "profile_activity5";
    private int dailyCal,totalCal, averageCal;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.JustEatIt";
    private Calories calories;
    ArrayList<Ruoat> ruoat = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.ENGLISH);
    String currentDateandTime = sdf.format(new Date());
    //private Ruoat ruoat;
    ArrayList<Ruoat> Ruoat = new ArrayList<>();
    StepCounter dailysteps = new StepCounter(0);
    StepCounter totalsteps = new StepCounter(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int dailyCal = mPreferences.getInt(DAILY_CALORIES_KEY, 0);
        int totalCal = mPreferences.getInt(TOTAL_CALORIES_KEY, 0);
        int averageCal = mPreferences.getInt(AVERAGE_CALORIES_KEY, 0);
        calories = new Calories(dailyCal, totalCal, averageCal);
        dailysteps.setValue(mPreferences.getInt(DAILY_STEPS_KEY, 0));
        totalsteps.setValue(mPreferences.getInt(TOTAL_STEPS_KEY, 0));
        dailystepcounter = (TextView) findViewById(R.id.dailystepcounter);
        updateUI();

        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button", "Profiili-nappia painettu");
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                String message1 = calories.getDailyCountString();
                String message2 = calories.getTotalCountString();
                String message3 = calories.getAverageCountString();
                int dailyStepCount = dailysteps.stepsNow();
                int totalStepCount = totalsteps.stepsNow();
                intent.putExtra(EXTRA_MESSAGE, message1);
                intent.putExtra(EXTRA_MESSAGE1, message2);
                intent.putExtra(EXTRA_MESSAGE2, message3);
                intent.putExtra(EXTRA_MESSAGE3, dailyStepCount);
                intent.putExtra(EXTRA_MESSAGE4, totalStepCount);
                startActivityForResult(intent, 1);
            }
        });

    }
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        String json = gson.toJson(ruoat);

        running = false;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(DAILY_CALORIES_KEY, calories.getDailyCount());
        preferencesEditor.putInt(TOTAL_CALORIES_KEY, calories.getTotalCount());
        preferencesEditor.putInt(AVERAGE_CALORIES_KEY, calories.getAverageCount());
        preferencesEditor.putInt(DAILY_STEPS_KEY, dailysteps.stepsNow());
        preferencesEditor.putInt(TOTAL_STEPS_KEY, totalsteps.stepsNow());
        //preferencesEditor.putStringSet(ARRAYLIST_KEY, oos.);
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
        String foodRead = editText.getText().toString();
        EditText editText1 = findViewById(R.id.insertCalories);
        int caloriesSent = Integer.parseInt(editText1.getText().toString());
        if (caloriesSent > 0 && !foodRead.equals("[Insert food]")){
            calories.addCalories(caloriesSent);
            Log.i(TIME_OF_CLICK, currentDateandTime);
            ruoat.add( new Ruoat(currentDateandTime, foodRead, caloriesSent));

        }   else {
            Log.d(ERROR_MESSAGE, "No calories and/or item");
        }
        updateUI();
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
        totalsteps.addStep();
        dailystepcounter.setText(""+dailysteps.stepsNow());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (requestCode == RESULT_OK) {
                int dailyBack = data.getIntExtra("number1", 0);
                int totalBack = data.getIntExtra("number2", 0);
                dailysteps.setValue(dailyBack);
                totalsteps.setValue(totalBack);
            }if (resultCode == RESULT_CANCELED){
                setTitle("VIRHE TAPAHTUNUT");
            }
        }
    }
}
