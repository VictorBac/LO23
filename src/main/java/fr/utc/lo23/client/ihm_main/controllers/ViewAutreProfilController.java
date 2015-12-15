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
        mController.showMainWindow();
    }

    public void initdata(UserLight profilautre) {
        labelPlayer.setText(profilautre.getPseudo());
        //User userautre = mController.getManagerMain().getManagerData().
        //labelAge.setText(profilautre.get);
        // TODO quelque chose ici
//        imageviewer.setImage(user.getAvatar().getImg());
    }
    public void setProfile(UserLight user) {
        //TODO: username.setText(user.getPseudo());
        try {
            imageviewer.setImage(user.getAvatar().getImageAvatar());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
