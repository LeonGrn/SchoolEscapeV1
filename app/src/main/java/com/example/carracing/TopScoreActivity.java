package com.example.carracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private Button topscore_btn_map;
    MySharedPreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_top_score);

        topscore_linear_layout = findViewById(R.id.topscore_linear_layout);
        topscore_btn_back = findViewById(R.id.topscore_btn_back);
        topscore_btn_map = findViewById(R.id.topscore_btn_map);

        topscore_btn_back.setOnClickListener(goBack);
        topscore_btn_map.setOnClickListener(showLocation);

        ArrayList<Player> scores = null;
        msp = new MySharedPreferences(this);
        try
        {
            scores = new Gson().fromJson(msp.getString("scores","")
                            , new TypeToken<List<Player>>(){}.getType());
//            Collections.sort(scores);
        }catch(Exception e)
        {
            e.printStackTrace();
            scores = new ArrayList<>();
        }


        if(scores != null) {
            for (int i = 0; i < scores.size(); i++) {
                TextView textView = new TextView(this);
                textView.setText("" + scores.get(i).getScore() + " " + scores.get(i).getName());
                textView.setTextSize(35);
                textView.setText(String.format(Locale.ENGLISH, "%d) %s: %d", i + 1, scores.get(i).getName(), scores.get(i).getScore()));

                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                topscore_linear_layout.addView(textView);
            }
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

    View.OnClickListener showLocation = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //to open fragment
        }
    };

    private  void goToGameActivity()
    {
        Intent intent = new Intent(TopScoreActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
