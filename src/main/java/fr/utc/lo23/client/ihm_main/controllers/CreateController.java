package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.ImageAvatar;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

    private FileChooser avatarChooser;

    private String imagePath;
    /**
     * Create a new user, the User need to have filled the nickname, his age, and his password
     * check if the password is well writen twice, then create a new user and sending him to DATA.
     */
    public void createNewUser(ActionEvent actionEvent) {
        String testpass;
        String verifpass;

        testpass = password.getText();
        verifpass = repassword.getText();
        int createAge = 0;
        try {
            createAge = Integer.parseInt(age.getText());
        } catch (NumberFormatException e)
        {
            mController.showErrorPopup("Erreur", "L'âge doit être numérique !");
            return;
        }

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
        try {
            createUserLight.setAvatar(new ImageAvatar(imagePath));
        } catch (IOException e) {
            mController.showErrorPopup("Erreur", "Avatar introuvable, le chemin est-il correct ?");
        }

        createUser.setCore(createUserLight);
        createUser.setPwd(password.getText());

        System.out.println("user ok");

        //Appel de la fonction de data pour creer un utilisateur.
        mController.getManagerMain().getInterDataToMain().saveNewProfile(createUser);

        //Retour à la fenetre de connexion
        mController.showConnectionWindow();

    }

    @FXML
    /**
     * Cancel Button, come back to the Connection Window
     */
    public void didClickCancelButton(ActionEvent event) {
        mController.showConnectionWindow();
    }

    /**
     * Initialization of the Window, with the standard avatar image
     */
    public void initialize(URL location, ResourceBundle resources) {
        avatarChooser = new FileChooser();
        avatarChooser.setTitle("Choix de l'avatar");
        imagePath = "../ui/avatar.jpg";
    }

    @FXML
    /**
     * click to change the avatar, the user has to give the path of his picture.
     */
    public void didClickAvatar(ActionEvent event) {
        File file = avatarChooser.showOpenDialog(username.getScene().getWindow());
        if (file != null)
        {
            imagePath = file.getAbsolutePath();
            try {
                imageviewer.setImage(new Image(file.toURI().toURL().toString(),
                        imageviewer.getFitWidth(), imageviewer.getFitHeight(), true, true));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }


}

