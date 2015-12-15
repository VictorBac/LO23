package fr.utc.lo23.client.ihm_main.controllers;

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
        //TODO retour au controlleur précedent. A voir une fois qu'on connaitra quand on appelle ce controlleur.
    }

    public void setProfile(UserLight user) {
        username.setText(user.getPseudo());
        imageviewer.setImage(user.getAvatar().getImg());
    }
}
