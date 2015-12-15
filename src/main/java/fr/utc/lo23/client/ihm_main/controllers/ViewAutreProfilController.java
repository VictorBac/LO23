package fr.utc.lo23.client.ihm_main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.utc.lo23.common.data.UserLight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Created by leclercvictor on 08/12/2015.
 */
public class ViewAutreProfilController extends BaseController {

    @FXML
    private Button back;

    @FXML
    private TextField username;

    @FXML
    private TextField age;

    @FXML
    private ImageView imageviewer;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void didClickBackButton(ActionEvent actionEvent) {
        mController.showMainWindow();
    }

    public void setProfile(UserLight user) {
        username.setText(user.getPseudo());
        try {
            imageviewer.setImage(user.getAvatar().getImageAvatar());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
