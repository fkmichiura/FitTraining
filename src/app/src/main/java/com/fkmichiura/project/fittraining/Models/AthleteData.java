package com.fkmichiura.project.fittraining.Models;

import com.orm.SugarRecord;

public class AthleteData extends SugarRecord{

    private float athleteWeight;
    private String date;
    private String time;

    private Athlete athlete;

    public AthleteData() {
    }

    public AthleteData(float athleteWeight, String date, String time, Athlete athlete) {
        this.athleteWeight = athleteWeight;
        this.date = date;
        this.time = time;
        this.athlete = athlete;
    }

    public float getAthleteWeight() {
        return athleteWeight;
    }

    public void setAthleteWeight(float athleteWeight) {
        this.athleteWeight = athleteWeight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }
}
