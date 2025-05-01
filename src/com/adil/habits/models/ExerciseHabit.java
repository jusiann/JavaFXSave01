package com.adil.habits.models;

public class ExerciseHabit extends Habit {
    public ExerciseHabit(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Exercise";
    }
}
