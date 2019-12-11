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

    private Button menu_slow_Btn;
    private Button menu_fast_Btn;
    private Button menu_start_topscore;
    public static String mode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        menu_slow_Btn = findViewById(R.id.menu_slow_Btn);
        menu_fast_Btn = findViewById(R.id.menu_fast_Btn);
        menu_start_topscore = findViewById(R.id.menu_start_topscore);

        menu_slow_Btn.setOnClickListener(goNext);
        menu_fast_Btn.setOnClickListener(goNext);
        menu_start_topscore.setOnClickListener(goTopScore);


    }

    View.OnClickListener goNext = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String choosenMode = v.getResources().getResourceEntryName(v.getId());
            switch (choosenMode)
            {
                case "menu_slow_Btn":
                    mode = "Slow";
                    goToGameActivity();
                    break;

                case "menu_fast_Btn":
                    mode = "Fast";
                    goToGameActivity();
                    break;
            }
        }
    };

    View.OnClickListener goTopScore = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(MenuActivity.this, TopScoreActivity.class);
            //intent.putExtra("score", getIntent().getExtras().getString("score"));
            startActivity(intent);
            finish();
        }
    };

    private  void goToGameActivity()
    {
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        intent.putExtra("mode" ,mode);
        startActivity(intent);
        finish();
    }
}
