package com.example.justeatit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @Author Matias Hätönen & Samuel Ahjoniemi
 */

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView dailystepcounter;
    SensorManager sensorManager;
    //boolean running = false;

    private final static String DAILY_CALORIES_KEY = "Daily_calories";
    private final static String TOTAL_CALORIES_KEY = "Total_Calories";
    private final static String AVERAGE_CALORIES_KEY = "Average_Calories";
    private final static String ARRAYLIST_KEY = "Arraylist";
    private final static String DAILY_STEPS_KEY = "Daily_Steps";
    public static final String EXTRA_MESSAGE1 = "Profile_activity1";
    public static final String EXTRA_MESSAGE2 = "Profile_activity2";
    public static final String EXTRA_MESSAGE3 = "Profile_activity3";
    public static final String EXTRA_MESSAGE4 = "Profile_activity4";
    public static final String EXTRA_MESSAGE5 = "Profile_activity5";
    public static final String EXTRA_MESSAGE6 = "Profile_activity6";
    private final static String TIME_OF_CLICK = "Time_of_click";
    private final static String ERROR_MESSAGE = "Error";
    private final static String TOTAL_STEPS_KEY = "Total_Steps";
    private final static String TEST_MESSAGE = "testiviesti";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.JustEatIt";
    private Calories calories;
    ArrayList<Ruoka> ruoat = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.ENGLISH);
    String currentDateandTime = sdf.format(new Date());
    StepCounter dailysteps = new StepCounter(0);
    StepCounter totalsteps = new StepCounter(0);

    /**
     * onCreate method brings all the data back from the save-file in sharedPreferences.
     * Then it transforms the data back into the calorie and step calculators and to the arraylist through Google gson
     * Finally it updates the screen with updateUI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Gson gson = new Gson();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int dailyCal = mPreferences.getInt(DAILY_CALORIES_KEY, 0);
        int totalCal = mPreferences.getInt(TOTAL_CALORIES_KEY, 0);
        int averageCal = mPreferences.getInt(AVERAGE_CALORIES_KEY, 0);
        String json = mPreferences.getString(ARRAYLIST_KEY, "");
        if(!json.equals("")) {
            Type type = new TypeToken<ArrayList<Ruoka>>(){}.getType();
            ruoat = gson.fromJson(json, type);
            Log.i(TEST_MESSAGE, ruoat.toString());
        }
        calories = new Calories(dailyCal, totalCal, averageCal);
        dailysteps.setValue(mPreferences.getInt(DAILY_STEPS_KEY, 0));
        totalsteps.setValue(mPreferences.getInt(TOTAL_STEPS_KEY, 0));
        dailystepcounter = (TextView) findViewById(R.id.dailystepcounter);
        updateUI();

    }

    /**
     * Saving all the relevant information when taking the app from the foreground.
     * First serializing the arraylist as a string and then saving all the values from the Calories calculator, stepcounter and the arraylist
     * Lastly it updates the screen
     */
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        String json = gson.toJson(ruoat);
        Log.i(TEST_MESSAGE, json);
        //running = false;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(DAILY_CALORIES_KEY, calories.getDailyCount());
        preferencesEditor.putInt(TOTAL_CALORIES_KEY, calories.getTotalCount());
        preferencesEditor.putInt(AVERAGE_CALORIES_KEY, calories.getAverageCount());
        preferencesEditor.putInt(DAILY_STEPS_KEY, dailysteps.stepsNow());
        preferencesEditor.putString(ARRAYLIST_KEY, json);
        preferencesEditor.putInt(TOTAL_STEPS_KEY, totalsteps.stepsNow());
        preferencesEditor.commit();

        updateUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        //running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Resets daily calories and steps counts in the Calories.java
     * @param v button searched with onButtonClicked from the activity_main.xml
     */

    public void resetDailiesButtonPressed(View v) {
        Log.i("Button", "Reset-button pressed");
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure want to reset?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                calories.resetDailyCalories();
                dailysteps.reset();
                dialog.cancel();
                updateUI();
            }
        });
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
       /* calories.resetDailyCalories();
        dailysteps.reset();
        updateUI(); */
    }

    /**
     * Sends the information inputted into the food-item and calories textviews
     * The default values of the both textviews must be changed to be able to send the data.
     * The data is sent into the Calories-class as well as to the Arraylist as a Ruoka-item.
     * If both parameters are not changed, it will log an error message
     * @param v  button searched with onButtonClicked from the activity_main.xml
     *
     *
     */
    public void sendButtonPressed(View v){
        Log.i("Button", "Lähetä-nappia painettu");
        EditText editText = findViewById(R.id.foodBar);
        final String foodRead = editText.getText().toString();
        EditText editText1 = findViewById(R.id.insertCalories);

        final int caloriesSent = Integer.parseInt(editText1.getText().toString());
        if (caloriesSent > 0 && !foodRead.equals("[Insert food]")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Item: " + foodRead + "\n" + caloriesSent + " calories" );
            builder.setCancelable(true);
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    calories.addCalories(caloriesSent);
                    Log.i(TIME_OF_CLICK, currentDateandTime);;
                    ruoat.add(new Ruoka(currentDateandTime, foodRead, caloriesSent));
                    dialog.cancel();
                    updateUI();
                }
            });
            builder.setPositiveButton("Close!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }   else {
            Log.d(ERROR_MESSAGE, "No calories and/or item");
        }
        updateUI();


        /*    int caloriesSent = Integer.parseInt(editText1.getText().toString());
        if (caloriesSent > 0 && !foodRead.equals("[Insert food]")){
            calories.addCalories(caloriesSent);
            Log.i(TIME_OF_CLICK, currentDateandTime);;
            ruoat.add(new Ruoka(currentDateandTime, foodRead, caloriesSent));
        }   else {
            Log.d(ERROR_MESSAGE, "No calories and/or item");
        }
        updateUI(); */
    }

    public void profileButtonPressed(View v){
                Log.i("Button", "Profiili-nappia painettu");
                Gson gson = new Gson();
                String json = gson.toJson(ruoat);
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                String message1 = calories.getDailyCountString();
                String message2 = calories.getTotalCountString();
                String message3 = calories.getAverageCountString();
                int dailyStepCount = dailysteps.stepsNow();
                int totalStepCount = totalsteps.stepsNow();
                intent.putExtra(EXTRA_MESSAGE1, message1);
                intent.putExtra(EXTRA_MESSAGE2, message2);
                intent.putExtra(EXTRA_MESSAGE3, message3);
                intent.putExtra(EXTRA_MESSAGE4, dailyStepCount);
                intent.putExtra(EXTRA_MESSAGE5, totalStepCount);
                intent.putExtra(EXTRA_MESSAGE6, json);
                startActivityForResult(intent, 1);
    }


    /**
     * Updates the screen with daily steps and calories
     */
    @SuppressLint("SetTextI18n")
    public void updateUI(){
        TextView tv1 = findViewById(R.id.caloriesTodayCount);
        tv1.setText(calories.getDailyCountString());
        dailystepcounter.setText(""+dailysteps.stepsNow());

    }

    //Lisää askeleen aina kun puhelimen sensori huomaa askeleen
    @SuppressLint("SetTextI18n")
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
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
