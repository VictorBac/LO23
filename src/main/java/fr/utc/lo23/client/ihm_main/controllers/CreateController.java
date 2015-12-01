package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by leclercvictor on 01/12/2015.
 */
public class CreateController extends BaseController {

    public TextField username;
    public TextField firstname;
    public TextField lastname;
    public TextField birthdate;
    public TextField email;
    public PasswordField password;
    public PasswordField repassword;


    public void createNewUser(ActionEvent actionEvent) {

        User createUser = new User();
        //TODO quand data aura commit dans le main on pourra recuperer les setters du USER.

        mController.getManagerMain().getInterfaceDataToMain().saveNewProfile(createUser);
        

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

