package com.fkmichiura.project.fittraining.Models;

import com.orm.SugarRecord;

public class Athlete extends SugarRecord{

    private String name;
    private String gym;
    private int age;
    private float weight;
    private String evalDate;
    private String nextEvalDate;
    private String instructor;

    public Athlete() {
    }

    public Athlete(String name, int age, float weight, String gym, String evalDate, String nextEvalDate, String instructor) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.gym = gym;
        this.evalDate = evalDate;
        this.nextEvalDate = nextEvalDate;
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getEvalDate() {
        return evalDate;
    }

    public void setEvalDate(String evalDate) {
        this.evalDate = evalDate;
    }

    public String getNextEvalDate() {
        return nextEvalDate;
    }

    public void setNextEvalDate(String nextEvalDate) {
        this.nextEvalDate = nextEvalDate;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

}
