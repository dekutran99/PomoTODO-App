package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFxmlFile = new File(todoActionsPopUpFXML);

    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todoActionsPopUp;
    private JFXPopup todoOptionsPopUp;
    
    private Task task;
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEditDeleteBarPopUp();
        loadEditDeletePopUpActionListener();
        loadStatusOptionsPopUp();
        loadStatusOptionsPopUpActionListener();
    }

    // EFFECTS: load edit/delete options pop up
    private void loadEditDeleteBarPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new EditDeletePopUpControler());
            todoOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show edit/delete pop up when its icon is clicked
    private void loadEditDeletePopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // EFFECTS: load status selector pop up (to do, up next, in progress, done, pomodoro)
    private void loadStatusOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new StatusOptionsPopUpController());
            todoActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show status selector pop up when its icon is clicked
    private void loadStatusOptionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todoActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // Inner class: status selector pop up controller
    class StatusOptionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switchCase(selectedIndex);
            todoActionsPopUp.hide();
        }

        private void switchCase(int selectedIndex) {
            switch (selectedIndex) {
                case 0: Logger.log("TodoBarActionsPopUpController", "Set this task to To Do");
                    break;
                case 1: Logger.log("TodoBarActionsPopUpController", "Set this task to Up Next");
                    break;
                case 2: Logger.log("TodoBarActionsPopUpController", "Set this task to In Progress");
                    break;
                case 3: Logger.log("TodoBarActionsPopUpController", "Set this task to Done");
                    break;
                case 4: Logger.log("TodoBarActionsPopUpController", "Set this task to Pomodoro");
                    break;
                default: Logger.log("TodoBarActionsPopUpController",
                        "No action is implemented for the selected option");
            }
        }
    }

    // Inned class: edit/delete pop up controller
    class EditDeletePopUpControler {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodoBarActionsPopUpController", "Edit task");
                    PomoTodoApp.setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("TodoBarActionsPopUpController", "Delete task");
                    PomoTodoApp.getTasks().remove(task);
                    JsonFileIO.write(PomoTodoApp.getTasks());
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks())); //update Scene
                    break;
                default:
                    Logger.log("TodoBarActionsPopUpController", "No action is implement for the selected option");
            }
            todoOptionsPopUp.hide();
        }
    }
}
