package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;


/**
 * Controller for the "View Own Profile" window
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewOwnProfilController extends BaseController {

    /**
     * ImageView which displays a preview of the avatar
     */
    @FXML
    private ImageView imageviewer;

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
     * Label which represents the first name
     */
    @FXML
    private Label labelFirstName;

    /**
     * Label which represents the last name
     */
    @FXML
    private Label labelLastName;

    /**
     * Label which represents the email address
     */
    @FXML
    private Label labelMail;

    /**
     * Label which represents the stats
     */
    @FXML
    private Label labelStats;

    /**
     * Function called after a click on the "Back" button to display the main window
     */
    @FXML
    void didClickBackButton() {
        mController.showMainWindow();
    }

    /**
     * Function called after a click on the "Edit Profile" button to display the "Edit Profile" Window
     */
    @FXML
    void openEditProfile() {
        mController.showEditProfilWindow();
    }

    /**
     * Function used to initialize the window with information on the local user
     */
    public void initData(){
        User currentUser = mController.getManagerMain().getInterDataToMain().getLocalUserProfile();
        UserLight currentLight = currentUser.getUserLight();
        labelPlayer.setText(currentLight.getPseudo());
        labelAge.setText(Integer.toString(currentUser.getAge()));
        labelFirstName.setText(currentUser.getFirstName());
        labelLastName.setText(currentUser.getLastName());
        labelMail.setText(currentUser.getEmail());
        if (currentLight.getAvatar() != null) {
            try {
                imageviewer.setImage(currentLight.getAvatar().getImageAvatar());
            } catch (IOException e) {
                mController.showErrorPopup("Avatar introuvable !");
            }
        }
        if (currentUser.getStats() != null)
        {
            labelStats.setText(currentUser.getStats().toString());
        }
        else {
            labelStats.setText("Statistiques indisponibles...");
        }
    }
}
