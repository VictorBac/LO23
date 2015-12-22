package fr.utc.lo23.client.ihm_main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Controller used to manage the window which allow to create a new server
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

    /**
     * Add a server,
     * We check if the IP address and Port are filled and then we send it to DATA.
     */
    @FXML
    public void didClickAddButton(ActionEvent event) {
        if (fieldIPAddress.getText().isEmpty() || fieldPort.getText().isEmpty()) {
            mController.showErrorPopup("Erreur", "Toutes les informations doivent être insérées");
            return;
        }
        try {
            Integer.parseInt(fieldPort.getText());
        } catch (Exception e) {
            mController.showErrorPopup("Erreur", "Le port doit être entier!");
            return;
        }
        try {
            mController.getManagerMain().getInterDataToMain().addServer(InetAddress.getByName(fieldIPAddress.getText()), fieldPort.getText());
        } catch (UnknownHostException e) {
           mController.showErrorPopup("Erreur", "Hôte inconnu!");
        }
        mController.showConnectionWindow();
    }

    /**
     * Cancel Button
     * back to the Connection Window.
     * @param event
     */
    @FXML
    public void didClickCancelButton(ActionEvent event) {
        mController.showConnectionWindow();
    }

}
