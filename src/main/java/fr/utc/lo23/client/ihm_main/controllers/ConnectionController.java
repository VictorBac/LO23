package fr.utc.lo23.client.ihm_main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 04/11/15.
 */
public class ConnectionController implements Initializable {



    @FXML
    private Button buttonConnect;

    @FXML
    private TextField fieldUsername;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    public ListView listViewServers;

    @FXML
    void didButtonConnectClick(ActionEvent event) {
        System.out.println("didButtonConnectClick");
        ObservableList<String> items = FXCollections.observableArrayList("Serveur 1", "Cerberus 2", "World 3", "Europe 3");
        listViewServers.setItems(items);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
