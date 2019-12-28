package com.example.carracing;

import android.content.Context;
import android.hardware.SensorEvent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RawRes;

import java.util.ArrayList;

public class Game
{
    private final int COIN = 3;
    private final int STUDENT = 2;
    private final int ROCK = 1;
    private final int EMPTY = 0;

    public int tempScore = 0;
    private int gameObjects[] = {EMPTY , ROCK , STUDENT , COIN};
    ArrayList<Obstacle> RockPosition = new ArrayList<Obstacle>();
    private int setTime = 0;
    private Obstacle myStudent = new Obstacle( STUDENT, STUDENT_INITIAL_POSITION);
    private static final int STUDENT_INITIAL_POSITION = 37;

    private MediaPlayer mpSound = null;

    public void ssMovement(SensorEvent sensorEvent , ImageView[] cars)
    {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];

        if((myStudent.getIndex() >= STUDENT_INITIAL_POSITION - 2) && (myStudent.getIndex() <= STUDENT_INITIAL_POSITION + 2))
        {
            if (x > -8 && x < -6)
            {
                cars[myStudent.getIndex()].setImageResource(0);
                myStudent.setIndex(STUDENT_INITIAL_POSITION + 2);
                cars[myStudent.getIndex()].setImageResource(R.drawable.ic_student);
                return;
            }

            if (x > -5 && x< -3)
            {
                cars[myStudent.getIndex()].setImageResource(0);
                myStudent.setIndex(STUDENT_INITIAL_POSITION + 1);
                cars[myStudent.getIndex()].setImageResource(R.drawable.ic_student);
                return;
            }
            else if (x > -2 && x< 2)
            {
                cars[myStudent.getIndex()].setImageResource(0);
                myStudent.setIndex(STUDENT_INITIAL_POSITION);
                cars[myStudent.getIndex()].setImageResource(R.drawable.ic_student);
                return;
            }

            else if (x < 8 && x>6)
            {
                cars[myStudent.getIndex()].setImageResource(0);
                myStudent.setIndex(STUDENT_INITIAL_POSITION - 2);
                cars[myStudent.getIndex()].setImageResource(R.drawable.ic_student);
                return;

            }

            else if (x < 5 && x>3)
            {
                cars[myStudent.getIndex()].setImageResource(0);
                myStudent.setIndex(STUDENT_INITIAL_POSITION - 1);
                cars[myStudent.getIndex()].setImageResource(R.drawable.ic_student);
                return;
            }
        }
    }

    public void changeCarPosition(String direction, ImageView[] cars)
    {
        switch(direction)
        {
            case "leftClick":
                if(myStudent.getIndex() > STUDENT_INITIAL_POSITION - 2)
                {
                    cars[myStudent.getIndex()].setImageResource(0);
                    myStudent.setIndex(myStudent.getIndex() - 1);
                    cars[myStudent.getIndex()].setImageResource(R.drawable.ic_student);
                }
                break;

            case "rigthClick":
                if(myStudent.getIndex() < STUDENT_INITIAL_POSITION + 2)
                {
                    cars[myStudent.getIndex()].setImageResource(0);
                    myStudent.setIndex(myStudent.getIndex() + 1);
                    cars[myStudent.getIndex()].setImageResource(R.drawable.ic_student);
                }
                break;
        }
    }

    public void playSound(@RawRes int sound , Context context)
    {
        if(mpSound != null) {
            if (mpSound.isPlaying())
                mpSound.stop();
            mpSound.release();
        }
        mpSound = MediaPlayer.create(context , sound);
        mpSound.start();
    }

    public int randobstacle()
    {
        return (int)(Math.random() * 1000) % 5;
    }

    public int randType()
    {
        return (int)(Math.random() * 1000) % 2;
    }

    public int gameProcces(ImageView[] obstacleImg , Context context)
    {
        if(randType() == 0)
            RockPosition.add(new Obstacle( ROCK, randobstacle()));
        else
            RockPosition.add(new Obstacle( COIN, randobstacle()));


        for(int i = RockPosition.size() - 1 ; i >= 0; i--)
        {
            int inx = RockPosition.get(i).getIndex();
            if(inx + 5 < obstacleImg.length)
                RockPosition.get(i).setIndex(inx + 5);
            else
                RockPosition.remove(i);
        }
//        if(setTime % 2 == 0)//Create only one time rock
//        {
//            RockPosition.add(new Obstacle( ROCK, randobstacle()));
//            Log.i("Time:" , " " + setTime);
//        }

        setTime += 1;

        if(crash(obstacleImg  , context) == 1)
        {
            MySignal.vibrate(context, 400);
            Toast.makeText(context , "BOOM! " , Toast.LENGTH_SHORT ).show();
            return 1;
        }

        return 0;
    }



    public void drawScene(ImageView[] obstacleImg)//draw the rocks on the map
    {
        for (int i = 0 ; i < obstacleImg.length; i++)
        {
            if(i != myStudent.getIndex())
                obstacleImg[i].setImageResource(0);
        }

        for (int n = 0 ; n < RockPosition.size(); n++)
        {
            if(RockPosition.get(n).getType() == ROCK)
                obstacleImg[RockPosition.get(n).getIndex()].setImageResource(R.drawable.ic_rockimage);
            if(RockPosition.get(n).getType() == COIN)
                obstacleImg[RockPosition.get(n).getIndex()].setImageResource(R.drawable.ic_goodgrade);

        }

    }

    public int crash(ImageView[] obstacleImg , Context context)//check crashes
    {
        for (int i = 0 ; i < RockPosition.size()  ; i++)
        {
            if (RockPosition.get(i).getType() == ROCK && RockPosition.get(i).getIndex() == myStudent.getIndex())
            {
                playSound(R.raw.crash , context);

                RockPosition.remove(i);
                return 1;
            }
            if(RockPosition.get(i).getType() == COIN && RockPosition.get(i).getIndex() == myStudent.getIndex())
            {
                playSound( R.raw.collect , context);

                setScore(10);
                RockPosition.remove(i);
                return 0;
            }
        }
        return 0;
    }


    public void resetGame()//reset game
    {
        for (int i = 0 ; i < RockPosition.size(); i++)
        {
            RockPosition.remove(i);
        }
        setTime = 0;
    }

    public void setScore(int tempScore)
    {
        this.tempScore += tempScore;
    }

    public int getScore()
    {
        return tempScore;
    }
}
