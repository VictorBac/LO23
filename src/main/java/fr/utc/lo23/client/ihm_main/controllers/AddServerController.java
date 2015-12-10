package fr.utc.lo23.client.ihm_main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 07/12/2015.
 */
public class AddServerController extends BaseController{

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField fieldIPAddress;

    @FXML
    private TextField fieldPort;

    @FXML
    void didClickAddButton(ActionEvent event) {
        // TODO appeler une méthode de managerData pour stocker nouveau server
        // puis retourner sur fenetre principale

        mController.showConnectionWindow();
    }

    @FXML
    void didClickCancelButton(ActionEvent event) {
        mController.showConnectionWindow();
    }

}
