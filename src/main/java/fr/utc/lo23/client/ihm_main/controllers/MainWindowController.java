package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.UserLight;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 18/11/15.
 */
public class MainWindowController extends BaseController {
    @FXML
    private static ListView<UserLight> listViewConnectedUsers;

    @FXML
    private Button buttonQuit;


    public void addUsers(List<UserLight> users) {
        for (UserLight user : users) {
            addUser(user);
        }
    }

    public void addUser(UserLight user){
        listViewConnectedUsers.getItems().add(user);
    }
    public void removeUser(UserLight user){
        listViewConnectedUsers.getItems().remove(user);
    }

    public void change(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewConnectedUsers.setCellFactory(column-> {
            return new ListCell<UserLight>(){
                @Override
                protected void updateItem(UserLight user, boolean empty){
                    if(user==null || empty)
                        setText(null);
                    else
                        setText(user.getPseudo());
                }
            };
        });
    }


    public void refreshUsers(List<UserLight> userList)
    {

    }

    public void openViewOwnProfil(ActionEvent actionEvent) {
        mController.showViewOwnWindow();
    }

    public void createTable(ActionEvent actionEvent) {

        mController.getManagerMain().getInterTableToMain().showTableCreationForm(mController.showCreateTableView().getMainPane());

    }

    public void joinTable(ActionEvent actionEvent) {

    }


    @FXML
    void didClickQuitButton(ActionEvent event) {
        mController.getManagerMain().getInterDataToMain().exitAsked();
        Platform.exit();
    }
}
