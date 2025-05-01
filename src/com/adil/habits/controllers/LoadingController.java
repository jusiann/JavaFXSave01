package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.InputStream;

import com.adil.habits.Main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadingController {
    @FXML
    private ProgressBar pbLoading;
    
    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
    	
    	final double[] progress = {0};
    	Timeline timeLine = new Timeline();
    	pbLoading.setProgress(0);

        timeLine.setCycleCount(100);
        timeLine.getKeyFrames().add(new KeyFrame(Duration.millis(15), evt -> {
            progress[0] += 0.01;
            pbLoading.setProgress(progress[0]);
            if (progress[0] >= 1.0) {
                loadLoginScene();
            }
        }));
        timeLine.play();
    }
    private void loadLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adil/habits/resources/fxml/login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            Stage currentStage = (Stage) pbLoading.getScene().getWindow();
            loginStage.initStyle(StageStyle.DECORATED);
            loginStage.setTitle("Welcome to MyHabits");
            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/images/icon02.jpg")));
            loginStage.setResizable(false);
            loginStage.setScene(new Scene(root));
            loginStage.show();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}