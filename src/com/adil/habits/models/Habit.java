package com.adil.habits.models;

import javafx.beans.property.*;

public abstract class Habit {
    protected StringProperty name;
    protected BooleanProperty completed;
    protected StringProperty extraData;

    public Habit(String name) {
        this.name = new SimpleStringProperty(name);
        this.completed = new SimpleBooleanProperty(false);
        this.extraData = new SimpleStringProperty("");
    }

    public abstract String getType();

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public String getExtraData() {
        return extraData.get();
    }

    public void setExtraData(String extraData) {
        this.extraData.set(extraData);
    }

    public StringProperty extraDataProperty() {
        return extraData;
    }
}
