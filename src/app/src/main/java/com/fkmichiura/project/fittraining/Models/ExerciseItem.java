package com.fkmichiura.project.fittraining.Models;

import com.orm.SugarRecord;

public class ExerciseItem extends SugarRecord {

    private int series = 3;
    private int repetitions;
    private int weight;

    private String currentDate;
    private String currentTime;
    private String repetetitionsAvg = "10 a 12 repetições";
    private String pauseAvg = "60 a 90 segundos";

    private Exercise exercise;

    public ExerciseItem() {
    }

    public ExerciseItem(int repetitions, int weight, String date, String time, Exercise exercise) {
        this.repetitions = repetitions;
        this.weight = weight;
        this.currentDate = date;
        this.currentTime = time;
        this.exercise = exercise;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getRepetetitionsAvg() {
        return repetetitionsAvg;
    }

    public void setRepetetitionsAvg(String repetetitionsAvg) {
        this.repetetitionsAvg = repetetitionsAvg;
    }

    public String getPauseAvg() {
        return pauseAvg;
    }

    public void setPauseAvg(String pauseAvg) {
        this.pauseAvg = pauseAvg;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
