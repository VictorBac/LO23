package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by leclercvictor on 01/12/2015.
 */
public class CreateController extends BaseController {

    @FXML
    private Button cancel;

    @FXML
    private Button create;

    @FXML
    private TextField username;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField birthdate;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField repassword;

    @FXML
    private ImageView imageviewer;


    public void createNewUser(ActionEvent actionEvent) {

        User createUser = new User();
        //TODO quand data aura commit dans le main on pourra recuperer les setters du USER.

        //createUser.setUSERNAME(username.getText());
        //createUser.setFIRSTNAME(firstname.getText());
        //createUser.setLASTNAME(lastname.getText());
        //createUser.setEMAIL(email.getText());
        // ETC....


        mController.getManagerMain().getInterfaceDataToMain().saveNewProfile(createUser);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

