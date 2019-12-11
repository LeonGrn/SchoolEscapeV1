package com.example.carracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopScoreActivity extends AppCompatActivity
{
    private LinearLayout topscore_linear_layout;
    private Button topscore_btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_score);

        topscore_btn_back = findViewById(R.id.topscore_btn_back);
//        LinearLayout ll = (LinearLayout) findViewById(R.id.topscore_linear_layout);
//        TextView tv = new TextView(this);

        //tv.setText(getIntent().getExtras().getString("score"));

//        ll.addView(tv);

        topscore_btn_back.setOnClickListener(goBack);




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
