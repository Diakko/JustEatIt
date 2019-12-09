package com.example.justeatit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
/**
 * @author  Matias Hätönen
 */
public class FoodItemsDetailsActivity extends AppCompatActivity {
    ArrayList<Ruoka> ruoat = new ArrayList<>();

    Gson gson = new Gson();

    /**
     * Gets the information from the previous instance and transforms in back to arraylist where from picks up the correct input with the value i which is the number of the clicked object in the listview in previous instance
     * Then it presents the Ruoka-object's values - foodItem's name, timestamp of the input and calorie value - in the screen
     * @param savedInstanceState gets the instance from the previous instance(ProfileActivity in this case) so the values are possible to be retrieved
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.food_layout);
        Bundle b = getIntent().getExtras();

        final String json = b.getString(ProfileActivity.EXTRA1, "");
        int i = b.getInt(ProfileActivity.EXTRA2, 0);


        if(!json.equals("")) {
            Type type = new TypeToken<ArrayList<Ruoka>>(){}.getType();
            ruoat = gson.fromJson(json, type);
        }


        TextView tv1 = findViewById(R.id.foodItem);
        tv1.setText(ruoat.get(i).getRuoka());

        TextView tv2 = findViewById(R.id.timestamp);
        tv2.setText(ruoat.get(i).getAika());

        TextView tv3 = findViewById(R.id.calories);
        tv3.setText(ruoat.get(i).getKalorit());

    }
}
