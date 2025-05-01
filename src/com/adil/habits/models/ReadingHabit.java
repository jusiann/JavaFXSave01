package com.adil.habits.models;

public class ReadingHabit extends Habit {
    public ReadingHabit(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Reading";
    }
}
