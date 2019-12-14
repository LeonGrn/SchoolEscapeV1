package com.example.carracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TopScoreActivity extends AppCompatActivity
{
    private LinearLayout topscore_linear_layout;
    private Button topscore_btn_back;
    MySharedPreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_score);
        topscore_linear_layout = findViewById(R.id.topscore_linear_layout);
        topscore_btn_back = findViewById(R.id.topscore_btn_back);
        topscore_btn_back.setOnClickListener(goBack);

        msp = new MySharedPreferences(this);

        ArrayList<Score> scores =
                new Gson().fromJson(msp.getString("scores","")
                        , new TypeToken<List<Score>>(){}.getType());

        Collections.sort(scores);

        for (int i = 0 ; i < scores.size() ; i++)
        {
            TextView textView = new TextView(this);
            textView.setText("" + scores.get(i).getScore() + " " + scores.get(i).getName());
            textView.setTextSize(35);
            textView.setText(String.format(Locale.ENGLISH , "%d) %s: %d", i+1 , scores.get(i).getName() , scores.get(i).getScore() ));

            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            topscore_linear_layout.addView(textView);
        }


    }

    View.OnClickListener goBack = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goToGameActivity();
        }
    };

    private  void goToGameActivity()
    {
        Intent intent = new Intent(TopScoreActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
