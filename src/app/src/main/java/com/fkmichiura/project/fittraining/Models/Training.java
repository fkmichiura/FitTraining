package com.fkmichiura.project.fittraining.Models;

import com.orm.SugarRecord;

public class Training extends SugarRecord{

    private String trainingName;
    private String trainingDesc;
    //Relação do Atleta ao seu Treino
    private Athlete athlete;

    public Training() {
    }

    public Training(String trainingName, String trainingDesc, Athlete athlete) {
        this.trainingName = trainingName;
        this.trainingDesc = trainingDesc;
        this.athlete = athlete;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingDesc() {
        return trainingDesc;
    }

    public void setTrainingDesc(String trainingDesc) {
        this.trainingDesc = trainingDesc;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }
}
