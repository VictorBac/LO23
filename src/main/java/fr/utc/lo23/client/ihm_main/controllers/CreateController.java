package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.Button;
import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by leclercvictor on 01/12/2015.
 */
public class CreateController extends BaseController {

    @FXML
    private javafx.scene.control.Button cancel;

    @FXML
    private javafx.scene.control.Button create;

    @FXML
    private javafx.scene.control.TextField password;

    @FXML
    private javafx.scene.control.TextField firstname;

    @FXML
    private javafx.scene.control.TextField lastname;

    @FXML
    private javafx.scene.control.TextField age;

    @FXML
    private javafx.scene.control.TextField email;

    @FXML
    private PasswordField username;

    @FXML
    private PasswordField repassword;

    @FXML
    private javafx.scene.image.ImageView imageviewer;


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

