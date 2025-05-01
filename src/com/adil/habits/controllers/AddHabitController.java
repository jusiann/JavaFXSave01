package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;


import com.adil.habits.models.*;

public class AddHabitController {

    @FXML
    private ListView<String> habitOptionsListView;

    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnBack;

    private HomeController homeController;

    @FXML
    public void initialize() {
        habitOptionsListView.getItems().addAll("Read", "Sleep", "Sport");
        
        btnBack.setOnAction(event -> {
        	Stage currentStage = (Stage) habitOptionsListView.getScene().getWindow();
        	currentStage.close();
        	
        });
        
        btnAdd.setOnAction(event -> {
            String selectedHabit = habitOptionsListView.getSelectionModel().getSelectedItem();

            if (selectedHabit != null && homeController != null) {
                Habit habit = null;
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Information Input");

                switch (selectedHabit) {
                    case "Read":
                        dialog.setHeaderText("How many pages did you read?");
                        dialog.setContentText("Page: ");
                        dialog.showAndWait().ifPresent(input -> {
                            ReadingHabit readingHabit = new ReadingHabit("Read");
                            readingHabit.setExtraData(input);
                            homeController.addHabit(readingHabit);
                        });
                        break;

                    case "Sleep":
                        dialog.setHeaderText("How many hours did you sleep?");
                        dialog.setContentText("Hour: ");
                        dialog.showAndWait().ifPresent(input -> {
                            SleepingHabit sleepingHabit = new SleepingHabit("Sleep");
                            sleepingHabit.setExtraData(input);
                            homeController.addHabit(sleepingHabit);
                        });
                        break;

                    case "Sport":
                        dialog.setHeaderText("How much sport did you do?");
                        dialog.setContentText("Activity:");
                        dialog.showAndWait().ifPresent(input -> {
                            ExerciseHabit exerciseHabit = new ExerciseHabit("Sport");
                            exerciseHabit.setExtraData(input);
                            homeController.addHabit(exerciseHabit);
                        });
                        break;

                    default:
                        break;
                }
                btnAdd.getScene().getWindow().hide();
            }
        });
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
}
