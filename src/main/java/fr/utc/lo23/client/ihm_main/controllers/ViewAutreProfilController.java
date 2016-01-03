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
 * Controller of the "View Distant Profile" action
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewAutreProfilController extends BaseController {
    /**
     * Label which represents the username
     */
    @FXML
    private Label labelPlayer;

    /**
     * Label which represents the age
     */
    @FXML
    private Label labelAge;

    /**
     * Label which represents the stats
     */
    @FXML
    private Label labelStats;

    /**
     * ImageView which displays a preview of the avatar
     */
    @FXML
    private ImageView imageviewer;

    /**
     * Called after a click on the "Back" button
     */
    @FXML
    public void didClickBackButton() {
        mController.getMainWindowController().backFromViewProfile();
    }

    /**
     * Function called after the initialization of the window to fill the window
     * @param profilautre UserLight of the user we want to display information about
     */
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

    /**
     * Function called after a response of the server about the user we want to display information about
     * @param profileReturnedByTheServer Full user profile of the user we want to display information about
     */
    public void setFullData(User profileReturnedByTheServer) {
        if (profileReturnedByTheServer != null) {
            labelAge.setText(Integer.toString(profileReturnedByTheServer.getAge()));
            if (profileReturnedByTheServer.getStats() != null) {
                labelStats.setText(profileReturnedByTheServer.getStats().toString());
            } else {
                labelStats.setText("Statistiques indisponibles...");
            }
        }
    }
}
