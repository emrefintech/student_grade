package com.example.studentgrade;

public class MaleStudent implements Student {
    private String name;
    private int number;
    private float grade;
    private boolean attending;
    private char gender;

    public MaleStudent(String name, int number, float grade, boolean attending, char gender) {
        this.name = name;
        this.number = number;
        this.grade = grade;
        this.attending = attending;
        this.gender = gender;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public float getGrade() {
        return grade;
    }

    @Override
    public boolean isAttending() {
        return attending;
    }

    @Override
    public char getGender() {
        return gender;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public void setAttending(boolean attending) {
        this.attending = attending;
    }

    @Override
    public void setGender(char gender) {
        this.gender = gender;
    }
}
