package com.example.studentgrade;

public interface Student {


    String getName();
    int getNumber();
    float getGrade();
    boolean isAttending();
    char getGender();

    void setName(String text);

    void setNumber(int parseInt);

    void setGrade(float parseFloat);

    void setAttending(boolean selected);

    void setGender(char charAt);
}
