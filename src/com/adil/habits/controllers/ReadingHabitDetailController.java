package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.adil.habits.models.ReadingHabit;

public class ReadingHabitDetailController {
    @FXML public ComboBox<String> readingTypeBox;
    @FXML public TextField readingField;
    @FXML private javafx.scene.control.Button btnApply;
    @FXML private javafx.scene.control.Button btnDelete;
    private HomeController homeController;
    private ReadingHabit habit;

    public void setHabit(ReadingHabit habit) {
        this.habit = habit;
        readingTypeBox.getItems().addAll("Saat", "Sayfa");
        readingTypeBox.setValue("Saat");

        String data = habit.getExtraData();
        if (data != null && !data.isEmpty()) {
            if (data.contains("sayfa")) {
                readingTypeBox.setValue("Sayfa");
                readingField.setText(data.replace(" sayfa", ""));
            } else if (data.contains("saat")) {
                readingTypeBox.setValue("Saat");
                readingField.setText(data.replace(" saat", ""));
            }
        }
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }
    
    public void applyChanges() {
        String value = readingField.getText().trim();
        String unit = readingTypeBox.getValue().equals("Sayfa") ? " sayfa" : " saat";
        habit.setExtraData(value + unit);
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
