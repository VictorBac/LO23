package fr.utc.lo23.client.ihm_main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * lance la controller table
 */

public class CreateTableController extends BaseController{

    @FXML
    private AnchorPane mainPane;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public AnchorPane getMainPane() {
        return mainPane;
    }
}