package com.example.justeatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String message3 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        /* Capture the layout's TextView and set the string as its text */
        TextView tv1 = findViewById(R.id.caloriesTodayCount);
        tv1.setText(message1);
        TextView tv2 = findViewById(R.id.caloriesTotalCount);
        tv2.setText(message2);

    }
}
