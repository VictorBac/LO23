package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewAutreProfilController extends BaseController {
    @FXML
    private Label labelPlayer;

    @FXML
    private Label labelAge;

    @FXML
    private ImageView imageviewer;

    @FXML
    public void didClickBackButton() {
        mController.getMainWindowController().backFromViewProfile();
    }

    public void initdata(UserLight profilautre) {
        labelPlayer.setText(profilautre.getPseudo());
        try {
            if (profilautre.getAvatar() != null) {
                imageviewer.setImage(profilautre.getAvatar().getImageAvatar());
            }
        } catch (IOException e) {
            mController.showErrorPopup("Avatar introuvable !");
        }
        try {
            mController.getManagerMain().getInterDataToMain().getUser(profilautre);
        } catch (ProfileNotFoundOnServerException e) {
            mController.showErrorPopup("Profil inconnu du côté serveur ??");
        } catch (NetworkFailureException e) {
            mController.showErrorPopup("Vérifier votre connexion internet");
        }
    }

    public void setFullData(User profileReturnedByTheServer) {
        if (profileReturnedByTheServer != null) {
            labelAge.setText(Integer.toString(profileReturnedByTheServer.getAge()));
        }
    }
}
