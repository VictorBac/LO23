package fr.utc.lo23.client.ihm_main.controllers;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Created by leclercvictor on 08/12/2015.
 */
public class EditOwnProfilController extends BaseController {

    @FXML
    private Button cancel;

    @FXML
    private Button update;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField repassword;

    @FXML
    private TextField age;

    @FXML
    private TextField email;

    @FXML
    private ImageView imageviewer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void didClickCancelButton(ActionEvent actionEvent) {
        mController.showViewOwnWindow();
    }

    public void UpdateProfil(ActionEvent actionEvent) {
    //TODO update profil
        mController.showViewOwnWindow();
    }
}
