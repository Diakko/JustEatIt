package com.example.justeatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @Author  Samuel Ahjoniemi
 */
    public class ProfileActivity extends AppCompatActivity implements SensorEventListener {
    StepCounter dailySteps2 = new StepCounter(0);
    StepCounter totalSteps2 = new StepCounter(0);
    SensorManager sensorManager;
    ArrayList<Ruoka> ruoat = new ArrayList<>();
    private static final String TAG = "List_Item";
    public static final String EXTRA1 = "Activity1";
    public static final String EXTRA2 = "Activity2";
    public static final String EXTRA3 = "Activity3";

    /**
     * @param savedInstanceState gets the instance from the previous instance(MainActivity in this case) so the values are possible to be retrieved
     * onCreate method brings all the data from the last activity with intent mechanism
     * Sets data into new classes.
     * Finally it updates the screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        String message1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        String message3 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        int number1 = intent.getIntExtra(MainActivity.EXTRA_MESSAGE4,0);
        int number2 = intent.getIntExtra(MainActivity.EXTRA_MESSAGE5,0);
        final String message4 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE6);

        //Vähennetään yhdet koska sovellus lisäsi aina yhden joka activityn vaihdon jälkeen.
        number1 -= 1;
        number2 -= 1;

        dailySteps2.setValue(number1);
        totalSteps2.setValue(number2);


        TextView tv3 = findViewById(R.id.stepsToday);
        TextView tv4 = findViewById(R.id.stepsTotal);
        tv3.setText(""+dailySteps2.stepsNow());

        tv4.setText(""+totalSteps2.stepsNow());


        TextView tv1 = findViewById(R.id.caloriesTodayCount);
        tv1.setText(message1);
        TextView tv2 = findViewById(R.id.caloriesTotalCount);
        tv2.setText(message2);
        Gson gson = new Gson();
        if(!message4.equals("")) {
            Type type = new TypeToken<ArrayList<Ruoka>>(){}.getType();
            ruoat = gson.fromJson(message4, type);
        }
        ListView lv = findViewById(R.id.foodItemsLayout);
        lv.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.fooditemslayout,
                ruoat
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Method is called when item from listView is clicked
             * Starts new activity with startActivity
             */

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(ProfileActivity.this, FoodItemsDetailsActivity.class);
                nextActivity.putExtra(EXTRA1, message4);
                nextActivity.putExtra(EXTRA2, i);
                startActivity(nextActivity);
            }
        });
    }

    /**
     * onResume method search and register right sensor from device
     * if there is no sensor called STEP_COUNTER app tells "Sensor not found" to the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method is used to get back to MainActivity.
     * @param v button searched with onButtonClicked from the activity_main.xml.
     * Data is transferred back with intent mechanism.
     */
    public void backButtonPressed(View v){
                int dailyBack = dailySteps2.stepsNow();
                int totalBack = totalSteps2.stepsNow();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("paiva", dailyBack);
                resultIntent.putExtra("koko", totalBack);

                setResult(RESULT_OK, resultIntent);
                finish();
    }

    /**
     * SensorEventListener implement needs this method.
     * Method is called when sensor detects changes.
     * Method is used to add steps and update view.
     * @param sensor SensorEvent values of event, which we didn't use.
     * Tutorial used to implement step counting sensors <a href="https://www.youtube.com/watch?v=pDz8y5B8GsE">https://www.youtube.com/watch?v=pDz8y5B8GsE</a>
     */
    @Override
    public void onSensorChanged(SensorEvent sensor){
        totalSteps2.addStep();
        dailySteps2.addStep();

        TextView tv3 = findViewById(R.id.stepsToday);
        TextView tv4 = findViewById(R.id.stepsTotal);

        tv3.setText(""+dailySteps2.stepsNow());
        tv4.setText(""+totalSteps2.stepsNow());
    }

    /**
     * SensorEventListener implement needs this method.
     * @param sensor What sensor is used
     * @param accuracy Accuracy of sensor data
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
