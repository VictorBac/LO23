package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.UserLight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 18/11/15.
 */
public class MainWindowController extends BaseController {
    @FXML
    private static ListView listViewConnectedUsers;

    public static ListView getListViewConnectedUsers() {
        return listViewConnectedUsers;
    }

    public static void setListViewConnectedUsers(ListView listViewConnectedUsers) {
        MainWindowController.listViewConnectedUsers = listViewConnectedUsers;
    }





    public void change(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void refreshUsers(List<UserLight> userList)
    {

    }
}
