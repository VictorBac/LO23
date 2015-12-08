package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
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
    private javafx.scene.control.TextField username;

    @FXML
    private javafx.scene.control.TextField firstname;

    @FXML
    private javafx.scene.control.TextField lastname;

    @FXML
    private javafx.scene.control.TextField age;

    @FXML
    private javafx.scene.control.TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField repassword;

    @FXML
    private javafx.scene.image.ImageView imageviewer;


    public void createNewUser(ActionEvent actionEvent) {
        String testpass;
        String verifpass;

        testpass = password.getText();
        verifpass = repassword.getText();
        int createAge = Integer.parseInt(age.getText());

        UserLight createUserLight = new UserLight(username.getText());


        if (testpass.equals(verifpass)) {
            System.out.println("Test ok");
        } else {
            mController.showErrorPopup("Error", "Mot de passe incorrect.");
            return;
        }

        User createUser = new User();
        createUser.setFirstName(firstname.getText());
        createUser.setLastName(lastname.getText());
        createUser.setEmail(email.getText());
        createUser.setAge(createAge);
        createUser.setCore(createUserLight);
        createUser.setPwd(password.getText());

        System.out.println("user ok");

        //Appel de la fonction de data pour creer un utilisateur.
        mController.getManagerMain().getInterDataToMain().saveNewProfile(createUser);

        //Retour à la fenetre de connexion
        mController.showConnectionWindow();

    }

    @FXML
    void didClickCancelButton(ActionEvent event) {
        mController.showConnectionWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

