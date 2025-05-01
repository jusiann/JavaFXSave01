package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TrackController {

    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        for (int i = 1; i <= 30; i++) {
            Label day = new Label(String.valueOf(i));
            day.setStyle("-fx-padding: 10; -fx-background-color: lightgray;");
            calendarGrid.add(day, (i-1)%7, (i-1)/7);
        }
    }
}
