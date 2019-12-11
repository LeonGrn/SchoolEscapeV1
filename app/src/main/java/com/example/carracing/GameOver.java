package com.example.carracing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity
{

    private Button gameover_btn_resetGame;
    private Button gameover_btn_menu;
    private TextView gameOver_text_score;

    @Override
    protected void onCreate(Bundle savedInstanceState )
    {
        int temp = 0;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        gameover_btn_resetGame = findViewById(R.id.game_btn_resetGame);
        gameover_btn_menu = findViewById(R.id.game_btn_menu);

        gameover_btn_resetGame.setOnClickListener(startNewGame);
        gameover_btn_menu.setOnClickListener(goToMenu);

        gameOver_text_score = findViewById(R.id.gameOver_text_score);

        gameOver_text_score.setText("YOUR SCORE IS: " + getIntent().getExtras().getString("score"));

    }

    View.OnClickListener startNewGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(GameOver.this, GameActivity.class);
            intent.putExtra("mode" ,MenuActivity.mode);
            startActivity(intent);
            GameOver.this.finish();
        }
    };

    View.OnClickListener goToMenu = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(GameOver.this, MenuActivity.class);
            intent.putExtra("score", getIntent().getExtras().getString("score"));
            startActivity(intent);
            GameOver.this.finish();
        }
    };
}
