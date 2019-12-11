package com.example.carracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity
{
    private ImageView intro_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        intro_Img = findViewById(R.id.intro_Img);


        new CountDownTimer(3000, 1000) { // 3000 = 3 sec

            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                goToGameActivity();
                IntroActivity.this.finish();
            }
        }.start();

    }

    private  void goToGameActivity()
    {
        Intent intent = new Intent(IntroActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
