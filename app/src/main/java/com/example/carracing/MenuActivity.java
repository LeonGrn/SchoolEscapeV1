package com.example.carracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity
{

    private Button menu_start_Btn;
    private Button menu_start_topscore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        menu_start_Btn = findViewById(R.id.menu_start_Btn);
        menu_start_topscore = findViewById(R.id.menu_start_topscore);

        menu_start_Btn.setOnClickListener(goNext);
        menu_start_topscore.setOnClickListener(goTopScore);


    }

    View.OnClickListener goNext = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goToGameActivity();
        }
    };

    View.OnClickListener goTopScore = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

        }
    };

    private  void goToGameActivity()
    {
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
