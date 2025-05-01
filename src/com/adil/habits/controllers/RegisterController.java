package com.adil.habits.controllers;

import com.adil.habits.models.UserSession;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {

    @FXML 
    private TextField tfNameRegister;
    
    @FXML 
    private TextField tfSurnameRegister;
    
    @FXML 
    private TextField tfUsernameRegister;
    
    @FXML 
    private PasswordField pfPasswordRegister;
    
    @FXML 
    private PasswordField pfConfirmPasswordRegister;

    private final String FILE_PATH = "src/data/UsersList.json";
    private final Gson gson = new Gson();

    @FXML
    private void handleRegister() {
        String name = tfNameRegister.getText().trim();
        String surname = tfSurnameRegister.getText().trim();
        String username = tfUsernameRegister.getText().trim();
        String password = pfPasswordRegister.getText();
        String confirmPassword = pfConfirmPasswordRegister.getText();
        List<UserSession> users = readUsersFromFile();
        
        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match!");
            return;
        }

        for (UserSession user : users) {
            if (user.getUsername().equals(username)) {
                showAlert(Alert.AlertType.ERROR, "Username already exists!");
                return;
            }
        }

        users.add(new UserSession(name, surname, username, password));
        writeUsersToFile(users);

        showAlert(Alert.AlertType.INFORMATION, "Account created successfully!");
        goToLogin();
    }

    @FXML
    private void goToLogin() {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/adil/habits/resources/fxml/login.fxml")));
            Stage stage = (Stage) tfUsernameRegister.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Could not load login screen.");
        }
    }
    
    private List<UserSession> readUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) 
        	return List.of();

        try (Reader reader = new FileReader(file)) {
            Type userListType = new TypeToken<ArrayList<UserSession>>(){}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeUsersToFile(List<UserSession> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }
}
