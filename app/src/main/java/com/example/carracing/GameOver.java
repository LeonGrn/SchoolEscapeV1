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

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        gameover_btn_resetGame = findViewById(R.id.game_btn_resetGame);
        gameover_btn_menu = findViewById(R.id.game_btn_menu);
        gameover_btn_resetGame.setOnClickListener(startNewGame);
        gameover_btn_menu.setOnClickListener(gotomenu);

        gameOver_text_score = findViewById(R.id.gameOver_text_score);


    }

    public void setScore(Game game)
    {
        gameOver_text_score.setText("     YOUR SCORE IS: ");
    }




    View.OnClickListener startNewGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent(GameOver.this, GameActivity.class);
            startActivity(intent);
            GameOver.this.finish();
        }
    };

    View.OnClickListener gotomenu = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            Intent intent = new Intent(GameOver.this, MenuActivity.class);
            startActivity(intent);
            GameOver.this.finish();
        }
    };
}
