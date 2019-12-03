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
import android.widget.TextView;
import android.widget.Toast;

/**
 * (C) Samuel Ahjoniemi
 */
public class ProfileActivity extends AppCompatActivity implements SensorEventListener {
    StepCounter dailySteps2 = new StepCounter(0);
    StepCounter totalSteps2 = new StepCounter(0);
    SensorManager sensorManager;
    boolean running = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
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
        TextView tv4 = findViewById(R.id.stepsTotal);

        tv3.setText(""+dailySteps2.stepsNow());
        tv4.setText(""+totalSteps2.stepsNow());
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
