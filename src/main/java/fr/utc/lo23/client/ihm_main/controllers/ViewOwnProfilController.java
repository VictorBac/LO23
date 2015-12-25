package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;


/**
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewOwnProfilController extends BaseController {

    @FXML
    private ImageView imageviewer;

    @FXML
    private Label labelPlayer;

    @FXML
    private Label labelAge;

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelLastName;

    @FXML
    private Label labelMail;

    @FXML
    private Label labelStats;


    @FXML
    void didClickBackButton() {
        mController.showMainWindow();
    }

    @FXML
    void openEditProfile() {
        mController.showEditProfilWindow();
    }

    public void initData(){
        User currentUser = mController.getManagerMain().getManagerData().getUserLocal();
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
