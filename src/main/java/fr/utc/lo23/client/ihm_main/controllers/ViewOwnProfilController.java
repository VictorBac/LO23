package fr.utc.lo23.client.ihm_main.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewOwnProfilController extends BaseController {

    @FXML
    private Button back;

    @FXML
    private Button edit;

    @FXML
    private TextField username;

    @FXML
    private TextField age;

    @FXML
    private TextField email;

    @FXML
    private ImageView imageviewer;

    @FXML
    void didClickBackButton(ActionEvent event) {
        mController.showMainWindow();
    }

    @FXML
    void openEditProfile(ActionEvent event) {
        mController.showEditOwnWindow();
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
