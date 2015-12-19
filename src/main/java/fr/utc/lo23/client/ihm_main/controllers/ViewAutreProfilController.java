package fr.utc.lo23.client.ihm_main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewAutreProfilController extends BaseController {

    @FXML
    private Button back;

    @FXML
    private Label labelPlayer;

    @FXML
    private Label labelAge;

    @FXML
    private ImageView imageviewer;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void didClickBackButton(ActionEvent actionEvent) {
        mController.getMainWindowController().backFromViewProfile();
    }

    public void initdata(UserLight profilautre) {
        labelPlayer.setText(profilautre.getPseudo());
        /*
        try {
            imageviewer.setImage(profilautre.getAvatar().getImageAvatar());
        } catch (IOException e) {
            mController.showErrorPopup("Erreur", "Avatar introuvable !");
        }
        */
        try {
            mController.getManagerMain().getInterDataToMain().getUser(profilautre);
        } catch (ProfileNotFoundOnServerException e) {
            mController.showErrorPopup("Erreur", "Profil inconnu du côté serveur ??");
        } catch (NetworkFailureException e) {
            mController.showErrorPopup("Erreur réseau", "Vérifier votre connexion internet");
        }
    }

    public void setFullData(User profileReturnedByTheServer) {
        if (profileReturnedByTheServer != null) {
            labelAge.setText(Integer.toString(profileReturnedByTheServer.getAge()));
        }
    }
}
