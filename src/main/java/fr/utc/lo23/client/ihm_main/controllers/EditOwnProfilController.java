package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
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

    public void initialize(URL location, ResourceBundle resources) {
        //TODO Fonction de data pour recuperer le Userlocal
        // User edituser = DataGiveMeUser();
        // fonction de remplissage des champs
        // fillUserform(edituser);
    }

    public void didClickCancelButton(ActionEvent actionEvent) {
        mController.showViewOwnWindow();
    }

    public void UpdateProfil(ActionEvent actionEvent) {
        //TODO fonction de data pour recuperer le Userlocal
        // User edituser = DataGiveMeUser();
        User edituser = new User();
        edituser.setAge(Integer.parseInt(age.getText()));
        edituser.setEmail(email.getText());


    //TODO update profil, fonction de data à appeler, laquelle ?
        mController.showViewOwnWindow();
    }

    public void fillUserform(User UserLocal) {
        UserLight lightuser = UserLocal.getUserLight();
        username.setText(lightuser.getPseudo());
        age.setText(Integer.toString(UserLocal.getAge()));
        email.setText(UserLocal.getEmail());
    }
}
