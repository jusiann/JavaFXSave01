package com.adil.habits.models;

public class SleepingHabit extends Habit {
    public SleepingHabit(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Sleeping";
    }
}
