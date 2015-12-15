package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.InterfaceFromIHMMain;
import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.common.data.Server;
import fr.utc.lo23.common.data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 04/11/15.
 */
public class ConnectionController extends BaseController {
    public InterfaceFromIHMMain interfromIHMMAIN;


    private ObservableList<Server> serverList;

    @FXML
    private Button buttonConnect;

    @FXML
    public TextField fieldUsername;
    public String log;

    @FXML
    public PasswordField fieldPassword;
    public String pass;

    @FXML
    public ListView<Server> listViewServers;

    @FXML
    void didButtonConnectClick(ActionEvent event) throws IOException {
        String login = fieldUsername.getText();
        String passwd = fieldPassword.getText();

        if (login.isEmpty()|| passwd.isEmpty())
        {
            mController.showErrorPopup("Erreur", "Vous devez insérer un nom d'utilisateur et un mot de passe");
            return;
        }

        try { // User logged in
            IHMMainClientManager manager = mController.getManagerMain();
            manager.getInterDataToMain().logUser(login,passwd);
            //TODO asynchronous...
            mController.userLoggedIn();
        } catch (LoginNotFoundException e) {
            mController.showErrorPopup("Erreur", "Nom d'utilisateur inexistant sur le poste.");

        } catch (WrongPasswordException e) {
            mController.showErrorPopup("Erreur", "Mot de passe incorrect.");
        }

    }

    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        //serverList = FXCollections.observableArrayList(mController.getManagerMain().getInterDataToMain().getServersList());
        serverList = FXCollections.observableArrayList(new ArrayList<Server>());
        listViewServers.setItems(serverList);
        listViewServers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewServers.setCellFactory(new Callback<ListView<Server>, ListCell<Server>>() {
            @Override
            public ListCell<Server> call(ListView<Server> param) {
                ListCell<Server> cell = new ListCell<Server>(){
                    @Override
                    protected void updateItem(Server t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getAddress() + ":" + t.getPort());
                        }
                    }
                };
                return cell;
            }
        });

        // test
        try
        {
            serverList.add(new Server(InetAddress.getByName("000.000.000.000"), "0000"));
        } catch (UnknownHostException e)
        {

        }
    }

    public void change(ActionEvent actionEvent) {
    }

    public void createnewuser(ActionEvent actionEvent) {
        User createUser = new User();
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
        Server selectedServer = listViewServers.getSelectionModel().getSelectedItem();
        if (selectedServer != null) {
            serverList.remove(selectedServer);
            mController.getManagerMain().getInterDataToMain().removeServer(selectedServer);
        } else {
            mController.showErrorPopup("Erreur", "Vous devez sélectionner un serveur pour le supprimer.");
        }
    }

    public void ImportProfilClick(ActionEvent actionEvent) {

    }
}
