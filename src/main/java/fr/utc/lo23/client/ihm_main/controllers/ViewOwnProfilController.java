package fr.utc.lo23.client.ihm_main.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


/**
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewOwnProfilController extends BaseController {

    @FXML
    private Button back;

    @FXML
    private Button edit;

    @FXML
    private ImageView imageviewer;

    @FXML
    private Label labelPlayer;

    @FXML
    private Label labelStats;


    @FXML
    void didClickBackButton(ActionEvent event) {
        mController.showMainWindow();
    }

    @FXML
    void openEditProfile(ActionEvent event) {
        mController.showEditProfilWindow();
    }


    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = mController.getManagerMain().getManagerData().getUserLocal();
        UserLight currentLight = currentUser.getUserLight();
        labelPlayer.setText(currentLight.getPseudo());


    }

}
