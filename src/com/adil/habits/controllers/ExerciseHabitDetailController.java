package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.adil.habits.models.ExerciseHabit;

public class ExerciseHabitDetailController {

    @FXML public ComboBox<String> choiceBox;
    @FXML public TextField valueField;
    @FXML public ComboBox<Integer> hourBox;
    @FXML public ComboBox<Integer> minuteBox;
    @FXML public HBox timeBox; // Saat ve dakika için bir container
    @FXML private javafx.scene.control.Button btnApply;
    @FXML private javafx.scene.control.Button btnDelete;
    private HomeController homeController;

    private ExerciseHabit habit;

    public void setHabit(ExerciseHabit habit) {
        this.habit = habit;

        choiceBox.getItems().addAll("Zaman", "Kalori");
        choiceBox.setValue("Zaman");

        for (int i = 0; i <= 23; i++) hourBox.getItems().add(i);
        for (int i = 0; i <= 59; i++) minuteBox.getItems().add(i);

        // Varsayılan değer
        hourBox.setValue(0);
        minuteBox.setValue(0);

        String data = habit.getExtraData();
        if (data != null) {
            if (data.contains("kcal")) {
                choiceBox.setValue("Kalori");
                valueField.setText(data.replace(" kcal", ""));
            } else if (data.contains("saat")) {
                choiceBox.setValue("Zaman");
                String[] parts = data.split(" ")[0].split(":");
                hourBox.setValue(Integer.parseInt(parts[0]));
                minuteBox.setValue(Integer.parseInt(parts[1]));
            }
        }

        updateFieldsVisibility(); // İlk seçimde güncelle

        choiceBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFieldsVisibility());
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }
    
    private void updateFieldsVisibility() {
        boolean isTime = choiceBox.getValue().equals("Zaman");
        timeBox.setVisible(isTime);
        timeBox.setManaged(isTime);

        valueField.setVisible(!isTime);
        valueField.setManaged(!isTime);
    }

    @FXML
    public void applyChanges() {
        if (choiceBox.getValue().equals("Kalori")) {
            String input = valueField.getText().trim();
            habit.setExtraData(input + " kcal");
        } else {
            int hour = hourBox.getValue();
            int minute = minuteBox.getValue();
            habit.setExtraData(String.format("%02d:%02d saat", hour, minute));
        }

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
