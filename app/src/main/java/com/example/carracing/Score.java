package com.example.carracing;

import android.location.Location;

public class Score implements Comparable<Score>
{
    private int score = 0;
    private String name = null;
    private double lat = 0;
    private double lon = 0;

    public Score(int score , String name , Location location)
    {
        this.score = score;
        this.name = name;
        if(location != null)
        {
            this.lat = location.getLatitude();
            this.lon = location.getLongitude();
        }
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public int compareTo(Score o)
    {
        if(o == null)
            return 0;
        return o.getScore() - this.getScore();
    }
}
