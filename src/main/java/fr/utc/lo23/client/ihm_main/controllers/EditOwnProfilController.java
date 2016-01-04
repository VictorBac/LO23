package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.exceptions.UserAlreadyExistsException;
import fr.utc.lo23.common.data.ImageAvatar;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * This controller is used to manage the Edit Profil Window
 * Created by leclercvictor on 08/12/2015.
 */
public class EditOwnProfilController extends BaseController {

    /**
     * TextField which represents the username
     */
    @FXML
    private TextField username;

    /**
     * PasswordField which represents the former password
     */
    @FXML
    private PasswordField oldPassword;

    /**
     * PasswordField which represents the new password
     */
    @FXML
    private PasswordField newPassword;

    /**
     * TextField which represents the age of the user
     */
    @FXML
    private TextField age;

    /**
     * TextField which represents the email address
     */
    @FXML
    private TextField email;

    /**
     * ImageView which displays a preview of the avatar
     */
    @FXML
    private ImageView imageviewer;

    /**
     * TextField which represents the first name of the user
     */
    @FXML
    private TextField firstname;

    /**
     * TextField which represents the last name of the user
     */
    @FXML
    private TextField lastname;

    /**
     * FileChooser which handles the selection of the avatar
     */
    private FileChooser avatarChooser;
    /**
     * Absolute path to the avatar chosen
     */
    private String imagePath;

    /**
     * initialization of the Window with the current Avatar of the User
     */
    public void initialize() {
        try {
            imagePath = getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/avatar.jpg").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        avatarChooser = new FileChooser();
        avatarChooser.setTitle("Choix de l'avatar");
    }

    /**
     * Data initialization of the Edit Window, we get the current user from DATA,
     * then we call our function to fill the user's information
     */
    public void initdata(){
        User edituser = mController.getManagerMain().getInterDataToMain().getLocalUserProfile();
        fillUserform(edituser);
    }

    /**
     * Cancel button, Exit the edition of profile,
     * go back to the Window which display your view your own profile
     */
    @FXML
    public void didClickCancelButton() {
        mController.showViewOwnWindow();
    }


    /**
     * Update Profile,
     * we get the User from DATA,
     * we set his new information,
     * if he wants to change his password, we check first that the "old password" is the same as the current one,
     * at the end, we call the update function of DATA.
     */
    @FXML
    public void updateProfil() {
        User olduser = mController.getManagerMain().getManagerData().getUserLocal();
        User edituser = new User(olduser);
        if (age.getText().isEmpty() || email.getText().isEmpty() || username.getText().isEmpty()
                || firstname.getText().isEmpty() || lastname.getText().isEmpty())
        {
            mController.showErrorPopup("Vous devez entrer tout les champs !");
            return;
        }
        edituser.setAge(Integer.parseInt(age.getText()));
        edituser.setEmail(email.getText());
        edituser.getUserLight().setPseudo(username.getText());
        edituser.setFirstName(firstname.getText());
        edituser.setLastName(lastname.getText());

        if (!oldPassword.getText().isEmpty()){
            if (olduser.getPwd().equals(oldPassword.getText())){
                edituser.setPwd(newPassword.getText());
            } else {
                mController.showErrorPopup("Le champ ancien mot de passe ne corespond pas au mot de passe actuel.");
            }
        }
        try {
            edituser.getUserLight().setAvatar(new ImageAvatar(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            mController.getManagerMain().getInterDataToMain().updateProfile(edituser);
        } catch (NetworkFailureException e) {
            mController.showErrorPopup("Erreur réseau inattendue... " + e);
            return;
        } catch (UserAlreadyExistsException e) {
            mController.showErrorPopup("Le pseudo est déjà pris ! Choisissez en un autre !");
            return;
        }

        mController.showPopup("Information", "Vous devez redémarrer le client pour prendre en compte " +
                "certaines modifications");
        mController.showViewOwnWindow();
    }

    /**
     * We fill the Window with the User's informations
     * @param userLocal the User from who we have to fill the information.
     */
    public void fillUserform(User userLocal) {
        UserLight lightuser = userLocal.getUserLight();
        username.setText(lightuser.getPseudo());
        age.setText(Integer.toString(userLocal.getAge()));
        email.setText(userLocal.getEmail());
        if (lightuser.getAvatar() != null) {
            imagePath = lightuser.getAvatar().getPath();
            try {
                imageviewer.setImage(lightuser.getAvatar().getImageAvatar());
            } catch (IOException e) {
                mController.showErrorPopup("Avatar introuvable !");
            }
        }
        firstname.setText(userLocal.getFirstName());
        lastname.setText(userLocal.getLastName());
    }


    @FXML
    /**
     * Button to change the Avatar of the profile, the user has to give the file of his image.
     */
    void avatarButtonClick() {
        File file = avatarChooser.showOpenDialog(username.getScene().getWindow());
        if (file != null) {
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
