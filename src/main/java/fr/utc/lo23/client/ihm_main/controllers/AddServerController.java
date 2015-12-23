package fr.utc.lo23.client.ihm_main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller used to manage the window which allow to create a new server
 * Created by jbmartin on 07/12/2015.
 */
public class AddServerController extends BaseController{
    @FXML
    private TextField fieldIPAddress;

    @FXML
    private TextField fieldPort;

    /**
     * Add a server,
     * We check if the IP address and Port are filled and then we send it to DATA.
     */
    @FXML
    public void didClickAddButton() {
        if (fieldIPAddress.getText().isEmpty() || fieldPort.getText().isEmpty()) {
            mController.showErrorPopup("Toutes les informations doivent être insérées");
            return;
        }
        try {
            Integer.parseInt(fieldPort.getText());
        } catch (Exception e) {
            mController.showErrorPopup("Le port doit être entier!");
            return;
        }
        try {
            mController.getManagerMain().getInterDataToMain().addServer(fieldIPAddress.getText(), Integer.parseInt(fieldPort.getText()));
        } catch (Exception e) {
           mController.showErrorPopup("Hôte inconnu!");
        }
        mController.showConnectionWindow();
    }

    /**
     * Cancel Button
     * back to the Connection Window.
     */
    @FXML
    public void didClickCancelButton() {
        mController.showConnectionWindow();
    }

}
