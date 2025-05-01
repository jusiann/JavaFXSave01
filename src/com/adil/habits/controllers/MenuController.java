package com.adil.habits.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.adil.habits.models.*;

public class MenuController {
    @FXML
    private BorderPane root;

    @FXML
    private Button btnHome;
    
    @FXML
    private Button btnTrack;
    
    @FXML
    private Button btnProfile;
    
    @FXML
    private Button btnAbout;
    
    @FXML
    private Label userLabel;
    
    @FXML
    public AnchorPane contentArea;
    
    String user = UserSession.getSession().getUsername();

    @FXML
    public void initialize() {
    	userLabel.setText(user);
    }
    
    @FXML
    private void goHome() {
        loadPage("/com/adil/habits/resources/fxml/home.fxml");
    }

    @FXML
    private void goTrack() {
        loadPage("/com/adil/habits/resources/fxml/track.fxml");
    }

    @FXML
    private void goProfile() {
        loadPage("/com/adil/habits/resources/fxml/profile.fxml");
    }

    @FXML
    private void goAbout() {
        loadPage("/com/adil/habits/resources/fxml/about.fxml");
    }

    private void loadPage(String page) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(page));
            contentArea.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
