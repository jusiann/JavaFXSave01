package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import com.adil.habits.models.SleepingHabit;

public class SleepingHabitDetailController {
    @FXML public ComboBox<Integer> hourBox;
    @FXML public ComboBox<Integer> minuteBox;
    @FXML private javafx.scene.control.Button btnApply;
    @FXML private javafx.scene.control.Button btnDelete;
    private HomeController homeController;
    private SleepingHabit habit;

    public void setHabit(SleepingHabit habit) {
        this.habit = habit;

        for (int i = 0; i <= 23; i++) hourBox.getItems().add(i);
        for (int i = 0; i <= 59; i++) minuteBox.getItems().add(i);

        hourBox.setValue(0);
        minuteBox.setValue(0);

        String raw = habit.getExtraData();
        if (raw != null && raw.contains(":")) {
            String[] parts = raw.split(" ")[0].split(":");
            hourBox.setValue(Integer.parseInt(parts[0]));
            minuteBox.setValue(Integer.parseInt(parts[1]));
        }
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }
    
    public void applyChanges() {
        int hour = hourBox.getValue();
        int minute = minuteBox.getValue();
        habit.setExtraData(String.format("%02d:%02d saat", hour, minute));
        homeController.saveHabitsToFile();
        closeWindow();
    }
    
    @FXML
    public void deleteHabit() {
        homeController.getHabitList().remove(habit);
        homeController.saveHabitsToFile();
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) btnApply.getScene().getWindow()).close();
    }
}
