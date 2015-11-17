package fr.utc.lo23.client.ihm_main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    void didButtonConnectClick(ActionEvent event) {
        System.out.println("didButtonConnectClick");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
