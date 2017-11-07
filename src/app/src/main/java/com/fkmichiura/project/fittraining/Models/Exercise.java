package com.fkmichiura.project.fittraining.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Exercise extends SugarRecord implements Parcelable {

    private int idImage;
    private String exerciseName;
    private int exerciseReps;
    private float exerciseWeight;

    private Training training;

    public Exercise() {
    }

    //Construtor do SugarORM
    public Exercise(int idImage, String exerciseName, Training training) {
        this.exerciseName = exerciseName;
        this.idImage = idImage;
        this.training = training;
    }

    public Exercise(int idImage, String exerciseName) {
        this.exerciseName = exerciseName;
        this.idImage = idImage;
    }

    protected Exercise(Parcel in) {
        idImage = in.readInt();
        exerciseName = in.readString();
        exerciseReps = in.readInt();
        exerciseWeight = in.readFloat();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExerciseReps() {
        return exerciseReps;
    }

    public void setExerciseReps(int exerciseReps) {
        this.exerciseReps = exerciseReps;
    }

    public float getExerciseWeight() {
        return exerciseWeight;
    }

    public void setExerciseWeight(float exerciseWeight) {
        this.exerciseWeight = exerciseWeight;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exerciseName);
    }
}
