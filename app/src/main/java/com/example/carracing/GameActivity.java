package com.example.carracing;//Leon Grinshpun

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity  {

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
    private String name = null;
    private double lat = 0;
    private double lon = 0;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SensorManager sensorManager;
    private Sensor sensor;

    MySharedPreferences msp;
    Location myLocation;

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

        sensorManager = (SensorManager) getSystemService(GameActivity.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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

        chooseMode();
    }

    boolean ssMode = false;
    private void chooseMode()
    {
        switch (getIntent().getExtras().getString("mode"))
        {
            case "Slow":
                chooseSpeed = slowMode;
                if(ssMode == true)
                    sensorManager.unregisterListener(sensorEventListener);
                ssMode = false;
                loopFun(chooseSpeed );
                break;

            case "Fast":
                chooseSpeed = fastMode;
                if(ssMode == true)
                    sensorManager.unregisterListener(sensorEventListener);
                ssMode = false;
                loopFun(chooseSpeed );
                break;

            case "ss":
                chooseSpeed = fastMode;
                if(ssMode == false)
                    sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_GAME);

                ssMode = true;
                main_btn_leftbtn.setVisibility(View.GONE);
                main_btn_rigthbtn.setVisibility(View.GONE);
                loopFun(chooseSpeed );
                break;
        }
    }

    private void loopFun(int chooseSpeed)//timer
    {
        timerRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                loopFun(GameActivity.chooseSpeed);

                gameOver -= mygame.gameProcces(obstacleImg, GameActivity.this );
                mygame.drawScene(obstacleImg);

                score = mygame.getScore();
                main_text_score.setText("SCORE: " + score);

                if(gameOver > -1 && gameOver < 3)
                {
                    animateItCode(lifeImage[gameOver]);
                    if(gameOver == 0)
                    {
                        timerHandler.removeCallbacks(timerRunnable);//timer
                        resetGame();//reset the gmae after lose
                        addPlayerToScoreView();
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

    private void addPlayerToScoreView()
    {
        final Player playerInfo = new Player(score , name , myLocation);
        final EditText result = new EditText(this);

        new AlertDialog.Builder(this).setTitle("Game Over!")
                .setMessage("Add your name")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        playerInfo.setName(result.getText().toString());
                        saveInformation(playerInfo);
                        goToGameActivity();//go to next activity
                    }


                }).setCancelable(false).setView(result).create().show();
    }

    private void saveInformation(Player playerInfo)
    {
        ArrayList<Player> list = null;

        Gson gson = new Gson();
        try
        {
            list = gson.fromJson(msp.getString("scores",""),new TypeToken<List<Player>>(){}.getType());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        if(list == null)
            list = new ArrayList<>();

        Log.e( "sadadssssssssssssasd",list.size()  + "");

        if(list.size() >= 10 && list.get(9).getScore() < playerInfo.getScore())
        {
            list.remove(9);
            mygame.resetGame();
        }

        list.add(playerInfo);
        Collections.sort(list);

        msp.putString("scores",gson.toJson(list));

    }

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location) {
                myLocation = location;
            }
        });

        if(ssMode == true)
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(ssMode == true)
            sensorManager.unregisterListener(sensorEventListener);
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



    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            mygame.ssMovement(event , obstacleImg);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}
