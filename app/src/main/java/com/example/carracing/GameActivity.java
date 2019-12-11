package com.example.carracing;//Leon Grinshpun

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ImageView main_imgView_lifeScore;
    private Button main_btn_leftbtn;
    private Button main_btn_rigthbtn;
    private ImageView[] obstacleImg = new ImageView[40];
    private Game mygame = null;
    private ImageView[] lifeImage;
    private int gameOver = 3;
    private final Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private TextView main_text_score;
    private GridLayout main_LAY_gridlayout;
    private int speedUp = 1;
    private static int chooseSpeed = 0;
    private int fastMode = 300;
    private int slowMode = 500;
    private int score = 0;
    ArrayList<Integer> ScoreTable = new ArrayList<>();
    MySharedPreferences msp;

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
        main_LAY_gridlayout = findViewById(R.id.main_LAY_gridview);

        msp = new MySharedPreferences(this);

        for(int i = 0 ; i < obstacleImg.length ; i++)
        {
            obstacleImg[i] = (ImageView) main_LAY_gridlayout.getChildAt(i);
        }


        lifeImage = new ImageView[]// life image
        {
               findViewById(R.id.likeImage1),
               findViewById(R.id.likeImage2),
               findViewById(R.id.likeImage3),
        };

        obstacleImg[37].setImageResource(R.drawable.ic_student);
        mygame = new Game();
        main_btn_leftbtn.setOnClickListener(changeDirection);
        main_btn_rigthbtn.setOnClickListener(changeDirection);

        switch (getIntent().getExtras().getString("mode"))
        {
            case "Slow":
                chooseSpeed = slowMode;
                loopFun(chooseSpeed );
                break;

            case "Fast":
                chooseSpeed = fastMode;
                loopFun(chooseSpeed );
                break;

            case "SS":

                break;
        }
    }

    private void loopFun(int chooseSpeed )//timer
    {
        timerRunnable = new Runnable()
        {

            @Override
            public void run()
            {
                    loopFun(GameActivity.chooseSpeed );

                    gameOver -= mygame.gameProcces(obstacleImg, GameActivity.this);
                    mygame.drawScene(obstacleImg);

                    score = mygame.getScore();
                    main_text_score.setText("SCORE: " + score);

                    if(gameOver > -1 && gameOver < 3)
                    {
                        animateItCode(lifeImage[gameOver]);
                        if(gameOver == 0)
                        {

                            msp.putInt("Score" , score);
                            ScoreTable.add(score);

                            resetGame();//reset the gmae after lose
                            timerHandler.removeCallbacks(timerRunnable);//timer
                            goToGameActivity();//go to next activity
                        }
                    }

            }
        };
        timerHandler.postDelayed(timerRunnable, chooseSpeed);
    }


    View.OnClickListener changeDirection = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
           String move = v.getResources().getResourceEntryName(v.getId());
           mygame.changeCarPosition(move, obstacleImg);
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
        intent.putExtra("score", score + "");
        startActivity(intent );
        finish();
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
        timerHandler.postDelayed(timerRunnable, chooseSpeed);
    }

    private void animateItCode(ImageView lifeImg)
    {
        lifeImg.animate().scaleX(0).scaleY(0).setDuration(1500).setInterpolator(new BounceInterpolator()).start();
    }
}
