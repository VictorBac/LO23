package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.InterfaceFromIHMMain;
import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.common.data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 04/11/15.
 */
public class ConnectionController extends BaseController {
    public InterfaceFromIHMMain interfromIHMMAIN;


    private ObservableList<String> serverList;

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
            //TODO asynchronous...
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

        serverList = FXCollections.observableArrayList();
        listViewServers.setItems(serverList);
        listViewServers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // TODO get server list from DATA
        //  mController.getManagerMain().getInterDataToMain().getServerList();

        // test
        // TODO remove when data methods are implemented
        serverList.add("000.000.000.000:0000");
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
    void didClickRemoveServerButton(ActionEvent event)
    {
        int selectedIndex = listViewServers.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {

            // TODO appeler méthode de interface Data pour supprimer server
            serverList.remove(listViewServers.getSelectionModel().getSelectedIndex());

        }else {

            mController.showErrorPopup("Error", "Vous devez sélectionner un server pour le supprimer.");
        }
    }
}
