package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.DataManagerClient;
import fr.utc.lo23.common.data.ImageAvatar;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
    private PasswordField oldpassword;

    @FXML
    private PasswordField newpassword;

    @FXML
    private TextField age;

    @FXML
    private TextField email;

    @FXML
    private ImageView imageviewer;

    private FileChooser avatarChooser;
    private String imagePath;

    public void initialize(URL location, ResourceBundle resources) {
        imagePath = "";
        avatarChooser = new FileChooser();
        avatarChooser.setTitle("Choix de l'avatar");
    }

    public void initdata(){
        User edituser = mController.getManagerMain().getManagerData().getUserLocal();
        fillUserform(edituser);
    }
    public void didClickCancelButton(ActionEvent actionEvent) {
        mController.showViewOwnWindow();
    }


    /**
     * mettre un profil à jour
     */
    public void UpdateProfil(ActionEvent actionEvent) {
        User edituser = mController.getManagerMain().getManagerData().getUserLocal();
        edituser.setAge(Integer.parseInt(age.getText()));
        edituser.setEmail(email.getText());
        edituser.getUserLight().setPseudo(username.getText());

        if (!oldpassword.getText().isEmpty()){
            if (edituser.getPwd().equals(oldpassword.getText())){
                edituser.setPwd(newpassword.getText());
            }
            else {
                mController.showErrorPopup("Erreur", "Le champ ancien mot de passe ne corespond pas au mot de passe actuel.");
            }
        }
        try {
            edituser.getUserLight().setAvatar(new ImageAvatar(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mController.getManagerMain().getInterDataToMain().saveNewProfile(edituser);
        mController.showViewOwnWindow();
    }

    /**
     * rempli le controller avec les informations actuelles
     */
    public void fillUserform(User UserLocal) {
        UserLight lightuser = UserLocal.getUserLight();
        username.setText(lightuser.getPseudo());
        age.setText(Integer.toString(UserLocal.getAge()));
        email.setText(UserLocal.getEmail());
        if (lightuser.getAvatar() != null) {
            imagePath = lightuser.getAvatar().getPath();
            try {
                imageviewer.setImage(lightuser.getAvatar().getImageAvatar());
            } catch (IOException e) {
                mController.showErrorPopup("Erreur", "Avatar introuvable !");
            }
        }
    }


    @FXML
    void avatarButtonClick(ActionEvent event) {
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
