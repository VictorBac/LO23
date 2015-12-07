package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.InterfaceFromIHMMain;
import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.common.data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 04/11/15.
 */
public class ConnectionController extends BaseController {
    public InterfaceFromIHMMain interfromIHMMAIN;

    @FXML
    private Button buttonConnect;

    @FXML
    public TextField fieldUsername;
    public String log;

    @FXML
    public PasswordField fieldPassword;
    public String pass;

    @FXML
    public ListView listViewServers;

    @FXML
    void didButtonConnectClick(ActionEvent event) throws IOException {
        System.out.println("didButtonConnectClick");


        String login = fieldUsername.getText();
        String passwd = fieldPassword.getText();


        try { // User logged in
            IHMMainClientManager manager = mController.getManagerMain();
            manager.getInterDataToMain().logUser(login,passwd);
            mController.userLoggedIn();

        } catch (LoginNotFoundException e) {
            mController.showErrorPopup("Error", "Nom d'utilisateur inexistant sur le poste.");

        } catch (WrongPasswordException e) {
            mController.showErrorPopup("Error", "Mot de passe incorrect.");
        }

    }

    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ConnectionController start");

    }

    public void change(ActionEvent actionEvent) {
    }

    public void createnewuser(ActionEvent actionEvent) {
        User createUser = new User();
        //createUser.
    }

    public void CreateProfilClick(ActionEvent actionEvent) {
        System.out.println("CreateProfilButton");
        mController.ClickCreateProfil();

    }

    @FXML
    void didClickAddServerButton(ActionEvent event) {
        mController.showAddServerWindow();
    }

    @FXML
    void didClickRemoveServerButton(ActionEvent event) {
        // TODO appeler méthode de interface Data pour supprimer server
        // rafraichir la liste
    }
}
