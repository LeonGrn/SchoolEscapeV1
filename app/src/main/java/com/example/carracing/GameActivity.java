package com.example.carracing;//Leon Grinshpun

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private ImageView main_imgView_lifeScore;
    private Button main_btn_leftbtn;
    private Button main_btn_rigthbtn;
    private ImageView[] obstacleImg;
    private ImageView[] carImg;
    private Game mygame = null;
    private ImageView[] lifeImage;
    private int gameOver = 3;
    final Handler timerHandler = new Handler();
    Runnable timerRunnable;
    private TextView main_text_score;
    private boolean checkActiveStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        main_text_score = findViewById(R.id.main_text_score);
        main_btn_leftbtn = findViewById(R.id.leftClick);
        main_btn_rigthbtn = findViewById(R.id.rigthClick);

        obstacleImg = new ImageView[]//all obstacles in the game
        {
                findViewById(R.id.imageView0),
                findViewById(R.id.imageView1),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
                findViewById(R.id.imageView5),
                findViewById(R.id.imageView6),
                findViewById(R.id.imageView7),
                findViewById(R.id.imageView8),
                findViewById(R.id.imageView9),
                findViewById(R.id.imageView10),
                findViewById(R.id.imageView11),
                findViewById(R.id.imageView12),
                findViewById(R.id.imageView13),
                findViewById(R.id.imageView14),
                findViewById(R.id.imageView15),
                findViewById(R.id.imageView16),
                findViewById(R.id.imageView17),
                findViewById(R.id.imageView21),
                findViewById(R.id.imageView22),
                findViewById(R.id.imageView23),
        };

        carImg = new ImageView[]//car
        {
                findViewById(R.id.imageView18),
                findViewById(R.id.imageView19),
                findViewById(R.id.imageView20),
        };

        lifeImage = new ImageView[]// life image
        {
               findViewById(R.id.likeImage1),
               findViewById(R.id.likeImage2),
               findViewById(R.id.likeImage3),
        };

        main_btn_leftbtn.setOnClickListener(changeDirection);
        main_btn_rigthbtn.setOnClickListener(changeDirection);


        for(int i = 0; i < obstacleImg.length ; i++)//init
        {
            obstacleImg[i].setVisibility(View.INVISIBLE);
        }

        for(int i = 0; i < carImg.length ; i++)
        {
            carImg[i].setVisibility(View.INVISIBLE);
        }

        carImg[1].setVisibility(View.VISIBLE);

        mygame = new Game();

        loopFun();
    }

    private void loopFun()//timer
    {
        timerRunnable = new Runnable()
        {

            @Override
            public void run()
            {
                    loopFun();

                    gameOver -= mygame.gameProcces(obstacleImg, GameActivity.this ,main_text_score);
                    mygame.drawScene(obstacleImg);

                    if(gameOver > -1 && gameOver < 3)
                    {
                        lifeImage[gameOver].setVisibility(View.INVISIBLE);
                        if(gameOver == 0)
                        {
                            resetGame();//reset the gmae after lose
                            timerHandler.removeCallbacks(timerRunnable);//timer
                            goToGameActivity();//go to next activity
                        }
                    }

            }
        };
        timerHandler.postDelayed(timerRunnable, 300);
    }


    View.OnClickListener changeDirection = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
           String move = v.getResources().getResourceEntryName(v.getId());
           mygame.changeCarPosition(move, carImg);
        }
    };


    private void resetGame()
    {
        gameOver = 3;
        mygame.resetGame();
    }

    private  void goToGameActivity()
    {
        Intent intent = new Intent(GameActivity.this, GameOver.class);
        startActivity(intent );
        GameActivity.this.finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        timerHandler.removeCallbacks(timerRunnable);
    }

    /**
     * When the player returns to the activity it starts from the place he left it!
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        timerHandler.postDelayed(timerRunnable, 300);
    }
}
