package com.example.carracing;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Game
{
    private final int CAR = 2;
    private final int ROCK = 1;
    private final int EMPTY = 0;
    private int gameObjects[] = {EMPTY , ROCK , CAR};
    ArrayList<Obstacle> RockPosition = new ArrayList<Obstacle>();
    private int setTime = 0;
    private Obstacle myCar = new Obstacle( CAR, 1);
    public int score = 0;


    public void changeCarPosition(String direction, ImageView[] cars)
    {
        switch(direction)
        {
            case "leftClick":
                if(myCar.getIndex() > 0)
                {
                    cars[myCar.getIndex()].setVisibility(View.INVISIBLE);
                    myCar.setIndex(myCar.getIndex() - 1);
                    cars[myCar.getIndex()].setVisibility(View.VISIBLE);
                }
                break;

            case "rigthClick":
                if(myCar.getIndex() < 2)
                {
                    cars[myCar.getIndex()].setVisibility(View.INVISIBLE);
                    myCar.setIndex(myCar.getIndex() + 1);
                    cars[myCar.getIndex()].setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public int randobstacle()
    {
        return (int)(Math.random() * 1000) % 3;
    }

    public int gameProcces(ImageView[] obstacleImg , Context context , TextView main_text_score)
    {
        for(int i = RockPosition.size() - 1 ; i >= 0; i--)
        {
            int inx = RockPosition.get(i).getIndex();
            if(inx + 3 < obstacleImg.length)
                RockPosition.get(i).setIndex(inx + 3);
            else
                RockPosition.remove(i);
        }
        if(setTime % 2 == 0)//Create only one time rock
        {
            RockPosition.add(new Obstacle( ROCK, randobstacle()));
            Log.i("Time:" , " " + setTime);
        }

        setTime += 1;
        setScore(10);
        main_text_score.setText("SCORE: " + score);

        if(crash(obstacleImg) == 1)
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
            obstacleImg[i].setVisibility(View.INVISIBLE);
        for (int n = 0 ; n < RockPosition.size(); n++)
            obstacleImg[RockPosition.get(n).getIndex()].setVisibility(View.VISIBLE);
    }

    public int crash(ImageView[] obstacleImg)//check crashes
    {
        for (int i = 0 ; i < RockPosition.size()  ; i++)
        {
            if (RockPosition.get(i).getType() == ROCK && RockPosition.get(i).getIndex() == myCar.getIndex() + 18)
            {
                RockPosition.remove(i);
                return 1;
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

    public static int getScore(int score)//its doesnt done (in the second game will work
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score += score;
    }

}
