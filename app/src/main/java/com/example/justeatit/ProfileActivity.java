package com.example.justeatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * (C) Samuel Ahjoniemi
 */
public class ProfileActivity extends AppCompatActivity {

    ArrayList<Ruoka> ruoat = new ArrayList<>();
    private static final String TAG = "List_Item";
    public static final String EXTRA1 = "Activity1";
    public static final String EXTRA2 = "Activity2";
    public static final String EXTRA3 = "Activity3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String message1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String message2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        String message3 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        String message4 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE4);
        String message5 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE5);
        /* Capture the layout's TextView and set the string as its text */
        TextView tv1 = findViewById(R.id.caloriesTodayCount);
        tv1.setText(message1);
        TextView tv2 = findViewById(R.id.caloriesTotalCount);
        tv2.setText(message2);
        TextView tv3 = findViewById(R.id.stepsToday);
        tv3.setText(message4);
        Gson gson = new Gson();
        if(!message5.equals("")) {
            Type type = new TypeToken<ArrayList<Ruoka>>(){}.getType();
            ruoat = gson.fromJson(message5, type);
        }

        ListView lv = findViewById(R.id.foodItemsLayout);

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
