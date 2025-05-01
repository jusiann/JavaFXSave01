package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ProfileController {

	@FXML
    private TextField kullaniciAdiLabel;
    
	@FXML
    private TextField soyisimLabel;
    
	@FXML
    private TextField yasLabel;
    
	@FXML
    private TextField bioLabel;

    
    @FXML
    private void logout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Çıkış");
        alert.setHeaderText(null);
        alert.setContentText("Çıkış yapıldı!");
        alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adil/habits/resources/fxml/login.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            Stage currentStage = (Stage) kullaniciAdiLabel.getScene().getWindow();
            loginStage.initStyle(StageStyle.DECORATED);
            loginStage.setTitle("Welcome to MyHabits");
            loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/adil/habits/resources/images/icon02.jpg")));
            loginStage.setResizable(false);
            loginStage.setScene(new Scene(root));
            loginStage.show();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

