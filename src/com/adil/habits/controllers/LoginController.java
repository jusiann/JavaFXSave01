package com.adil.habits.controllers;

import com.adil.habits.models.UserSession;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class LoginController {

    @FXML 
    private TextField tfUsernameLogin;
    
    @FXML 
    private PasswordField pfPasswordLogin;

    private final String FILE_PATH = "src/data/UsersList.json";
    private final Gson gson = new Gson();

    @FXML
    private void handleLogin() {
        String username = tfUsernameLogin.getText().trim();
        String password = pfPasswordLogin.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Fields can't be empty!");
            return;
        }

        List<UserSession> users = readUsersFromFile();
        for (UserSession user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                UserSession.setSession(user.getName(), user.getSurname(), user.getUsername(), user.getPassword());
                showAlert(Alert.AlertType.INFORMATION, "Login successful!");
                login();
                return;
            }
        }

        showAlert(Alert.AlertType.ERROR, "Incorrect username or password!");
    }

    private List<UserSession> readUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) 
        	return List.of();

        try (Reader reader = new FileReader(file)) {
            Type userListType = new TypeToken<List<UserSession>>(){}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private void login() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adil/habits/resources/fxml/menu.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) tfUsernameLogin.getScene().getWindow();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/adil/habits/resources/images/icon02.jpg")));
            stage.setTitle("Welcome to MyHabits");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            currentStage.close();

            //((Stage) tfUsernameLogin.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }

    @FXML
    private void goToRegister() {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/adil/habits/resources/fxml/register.fxml")));
            Stage stage = (Stage) tfUsernameLogin.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
