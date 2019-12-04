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
 * (C) Samuel Ahjoniemi
 */
public class ProfileActivity extends AppCompatActivity implements SensorEventListener {
    StepCounter dailySteps2 = new StepCounter(0);
    StepCounter totalSteps2 = new StepCounter(0);
    SensorManager sensorManager;
    boolean running = false;


    ArrayList<Ruoka> ruoat = new ArrayList<>();
    private static final String TAG = "List_Item";
    public static final String EXTRA1 = "Activity1";
    public static final String EXTRA2 = "Activity2";
    public static final String EXTRA3 = "Activity3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        final String message1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        String message3 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        String message4 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE4);
        String message5 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE5);
        int number1 = intent.getIntExtra(MainActivity.EXTRA_MESSAGE3,0);
        int number2 = intent.getIntExtra(MainActivity.EXTRA_MESSAGE4,0);

        number1 -= 1;
        number2 -= 1;

        dailySteps2.setValue(number1);
        totalSteps2.setValue(number2);

        String message1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String message3 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        String message4 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        /* Capture the layout's TextView and set the string as its text */

        TextView tv3 = findViewById(R.id.stepsToday);
        TextView tv4 = findViewById(R.id.stepsTotal);
        tv3.setText(""+dailySteps2.stepsNow());

        tv4.setText(""+totalSteps2.stepsNow());


        TextView tv1 = findViewById(R.id.caloriesTodayCount);
        tv1.setText(message1);
        TextView tv2 = findViewById(R.id.caloriesTotalCount);
        tv2.setText(message2);

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dailyBack = dailySteps2.stepsNow();
                int totalBack = totalSteps2.stepsNow();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("paiva", dailyBack);
                resultIntent.putExtra("koko", totalBack);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensor){
        totalSteps2.addStep();
        dailySteps2.addStep();
        TextView tv3 = findViewById(R.id.stepsToday);
        tv3.setText(message4);
        Gson gson = new Gson();
        if(!message5.equals("")) {
            Type type = new TypeToken<ArrayList<Ruoka>>(){}.getType();
            ruoat = gson.fromJson(message5, type);
        }

        ListView lv = findViewById(R.id.foodItemsLayout);
        TextView tv4 = findViewById(R.id.stepsTotal);

        tv3.setText(""+dailySteps2.stepsNow());
        tv4.setText(""+totalSteps2.stepsNow());
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

        lv.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.fooditemslayout,
                ruoat
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(ProfileActivity.this, FoodItemsDetailsActivity.class);
                nextActivity.putExtra(EXTRA1, message1);
                startActivity(nextActivity);
            }
        });
    }
}
