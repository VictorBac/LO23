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
        if (currentLight.getAvatar() != null) {
            try {
                imageviewer.setImage(currentLight.getAvatar().getImageAvatar());
            } catch (IOException e) {
                mController.showErrorPopup("Avatar introuvable !");
            }
        }
    }
}
