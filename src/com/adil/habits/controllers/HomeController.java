package com.adil.habits.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import com.adil.habits.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class HomeController {

    @FXML
    private Button btnAddHabit;

    @FXML
    private TableView<Habit> tableViewHabit;

    @FXML
    private TableColumn<Habit, Boolean> columnIsCompleted;
    
    @FXML
    private TableColumn<Habit, String> columnName;
    
    @FXML
    private TableColumn<Habit, String> columnDetails;
    
    @FXML
    private TableColumn<Habit, Void> columnAction;    

    private final ObservableList<Habit> listHabit = FXCollections.observableArrayList();
    
    private static final String FOLDER_PATH = "src/data/users_data";

    private final String user = UserSession.getSession().getUsername();
    
    @FXML
    public void initialize() {

        tableViewHabit.setEditable(true);
        columnIsCompleted.setEditable(true);

 
        columnIsCompleted.setCellValueFactory(cellData -> {
            BooleanProperty prop = cellData.getValue().completedProperty();
            prop.addListener((val, oldVal, newVal) -> saveHabitsToFile());
            return prop;
        });
        
        columnIsCompleted.setCellFactory(CheckBoxTableCell.forTableColumn(columnIsCompleted));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDetails.setCellValueFactory(new PropertyValueFactory<>("extraData"));

        columnAction.setCellFactory(column -> new TableCell<>() {
        	private final Button btnDetail = new Button("Details");
            {
            	btnDetail.setOnAction(event -> {
                    Habit habit = getTableView().getItems().get(getIndex());
                    showDetailDialog(habit);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnDetail);
            }
        });

        tableViewHabit.setItems(listHabit);
        btnAddHabit.setOnAction(event -> openAddHabitDialog());
        loadHabitsFromFile();
    }
    
    public void addHabit(Habit habit) {
    	listHabit.add(habit);
        saveHabitsToFile();
    }
    
    public ObservableList<Habit> getHabitList() {
        return listHabit;
    }

    
    private void openAddHabitDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adil/habits/resources/fxml/addHabit.fxml"));
            Parent root = loader.load();
            AddHabitController controller = loader.getController();
            controller.setHomeController(this);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Habit");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    private void showDetailDialog(Habit habit) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(habit.getName() + " Detayları");
        DialogPane pane = dialog.getDialogPane();
        pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent content;

            if (habit instanceof ReadingHabit) {
                loader.setLocation(getClass().getResource("/com/adil/habits/resources/fxml/ReadingHabitDetail.fxml"));
                content = loader.load();
                ReadingHabitDetailController controller = loader.getController();
                controller.setHabit((ReadingHabit) habit);
                dialog.setResultConverter(bt -> {
                    if (bt == ButtonType.OK) controller.applyChanges();
                    return null;
                });

            } else if (habit instanceof SleepingHabit) {
                loader.setLocation(getClass().getResource("/com/adil/habits/resources/fxml/SleepingHabitDetail.fxml"));
                content = loader.load();
                SleepingHabitDetailController controller = loader.getController();
                controller.setHabit((SleepingHabit) habit);
                dialog.setResultConverter(bt -> {
                    if (bt == ButtonType.OK) controller.applyChanges();
                    return null;
                });

            } else if (habit instanceof ExerciseHabit) {
                loader.setLocation(getClass().getResource("/com/adil/habits/resources/fxml/ExerciseHabitDetail.fxml"));
                content = loader.load();
                ExerciseHabitDetailController controller = loader.getController();
                controller.setHabit((ExerciseHabit) habit);
                dialog.setResultConverter(bt -> {
                    if (bt == ButtonType.OK) controller.applyChanges();
                    return null;
                });

            } else {
                return;
            }

            pane.setContent(content);
            dialog.showAndWait();
            saveHabitsToFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	*/
    
    private void showDetailDialog(Habit habit) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent content;

            if (habit instanceof ReadingHabit) {
                loader.setLocation(getClass().getResource("/com/adil/habits/resources/fxml/ReadingHabitDetail.fxml"));
                content = loader.load();
                ReadingHabitDetailController controller = loader.getController();
                controller.setHabit((ReadingHabit) habit);
                controller.setHomeController(this);
            } else if (habit instanceof SleepingHabit) {
                loader.setLocation(getClass().getResource("/com/adil/habits/resources/fxml/SleepingHabitDetail.fxml"));
                content = loader.load();
                SleepingHabitDetailController controller = loader.getController();
                controller.setHabit((SleepingHabit) habit);
                controller.setHomeController(this);
            } else if (habit instanceof ExerciseHabit) {
                loader.setLocation(getClass().getResource("/com/adil/habits/resources/fxml/ExerciseHabitDetail.fxml"));
                content = loader.load();
                ExerciseHabitDetailController controller = loader.getController();
                controller.setHabit((ExerciseHabit) habit);
                controller.setHomeController(this);
            } else return;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(content));
            stage.setTitle(habit.getName() + " Detayları");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void saveHabitsToFile() {
        try {
            LocalDate today = LocalDate.now();
            File folder = new File(FOLDER_PATH);
            File userFolder = new File(folder, user);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fileName = today.format(formatter) + ".json";
            File file = new File(userFolder, fileName);

            if (!folder.exists()) folder.mkdirs();
            if (!userFolder.exists()) userFolder.mkdirs();

            List<HabitJsonModel> habitJsonList = new ArrayList<>();
            for (Habit habit : listHabit) {
                HabitJsonModel model = new HabitJsonModel();
                model.className = habit.getClass().getSimpleName();
                model.name = habit.getName();
                model.detail = habit.getExtraData();
                model.isCompleted = habit.isCompleted();
                habitJsonList.add(model);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(habitJsonList, writer); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHabitsFromFile() {
        try {
            LocalDate today = LocalDate.now();
            listHabit.clear();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fileName = today.format(formatter) + ".json";
            File folder = new File(FOLDER_PATH, user);
            File file = new File(folder, fileName);

            if (!folder.exists() || !file.exists()) return;

            Gson gson = new Gson();
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<HabitJsonModel>>(){}.getType();
                List<HabitJsonModel> habitJsonList = gson.fromJson(reader, listType);

                for (HabitJsonModel model : habitJsonList) {
                    Habit habit = switch (model.className) {
                        case "ReadingHabit" -> new ReadingHabit(model.name);
                        case "SleepingHabit" -> new SleepingHabit(model.name);
                        case "ExerciseHabit" -> new ExerciseHabit(model.name);
                        default -> null;
                    };

                    if (habit != null) {
                        habit.setCompleted(model.isCompleted);
                        habit.setExtraData(model.detail);
                        listHabit.add(habit);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
