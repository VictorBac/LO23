package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.UserAlreadyExistsException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.Server;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller used to manage the Connection Window
 * Created by jbmartin on 04/11/15.
 */
public class ConnectionController extends BaseController {
    /**
     * AnchorPane of the window
     */
    @FXML
    private AnchorPane bgimage;

    /**
     * ObservableList which contains servers
     */
    private ObservableList<Server> serverList;

    /**
     * Button for the connection action
     */
    @FXML
    private Button buttonConnect;

    /**
     * TextField which represents the username
     */
    @FXML
    public TextField fieldUsername;

    /**
     * TextField which represents the password of the user
     */
    @FXML
    public PasswordField fieldPassword;

    /**
     * ListView which represents informations on servers
     */
    @FXML
    public ListView<Server> listViewServers;

    /**
     * FileChooser which handles the import of a local profile
     */
    private FileChooser profileChooser;

    /**
     * Start the connection with the server
     */


    @FXML
    /** On a click to connect, we start our function that manage it
     *
     */
    public void didButtonConnectClick() {
        sendAction();
    }

    /**
     * Connection action,
     * We try if the login and the password aren't empty, then we send the login and password to DATA
     * in order to be connected
     */
    private void sendAction() {
        String login = fieldUsername.getText();
        String passwd = fieldPassword.getText();
        if (listViewServers.getSelectionModel().getSelectedItem() == null) {
            mController.showErrorPopup("Vous devez sélectionner au moins un serveur !");
            return;
        }
        String ip = listViewServers.getSelectionModel().getSelectedItem().getAddress();
        Integer port = listViewServers.getSelectionModel().getSelectedItem().getPort();

        if (login.isEmpty()|| passwd.isEmpty()) {
            mController.showErrorPopup("Vous devez insérer un nom d'utilisateur et un mot de passe");
            return;
        }

        // User logged in
        try {
            IHMMainClientManager manager = mController.getManagerMain();
            manager.getInterDataToMain().logUser(login,passwd,ip,port);
        } catch (LoginNotFoundException e) {
            mController.showErrorPopup("Nom d'utilisateur inexistant sur le poste.");
        } catch (WrongPasswordException e) {
            mController.showErrorPopup("Mot de passe incorrect.");
        } catch (Exception e) {
            mController.showErrorPopup("Impossible de joindre le serveur \n" + e);
        }

    }
    /**
     * Override display methods that display the listview of Servers
     */
    public void initialize() {
        serverList = FXCollections.observableArrayList(new ArrayList<>());
        listViewServers.setItems(serverList);
        listViewServers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewServers.setCellFactory(new Callback<ListView<Server>, ListCell<Server>>() {
            @Override
            public ListCell<Server> call(ListView<Server> param) {
                return new ListCell<Server>(){
                    @Override
                    protected void updateItem(Server t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getAddress() + ":" + t.getPort());
                        }
                    }
                };
            }
        });
        profileChooser = new FileChooser();
        profileChooser.setTitle("Importer un profil");

        bgimage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendAction();
            }
        });
    }

    /**
     * initialization of the Server list
     */
    public void initServerlist() {
        serverList.setAll(mController.getManagerMain().getInterDataToMain().getServersList());
        listViewServers.getSelectionModel().select(0);
    }


    /**
     * Open the window to create a new profil
     */
    public void createProfilClick() {
        Console.log("CreateProfilButton");
        mController.clickCreateProfil();

    }

    /**
     * Open the window to create a new server
     */
    @FXML
    public void didClickAddServerButton() {
        mController.showAddServerWindow();
    }

    /**
     * Delete the selected server
     */
    @FXML
    public void didClickRemoveServerButton() {
        Server selectedServer = listViewServers.getSelectionModel().getSelectedItem();
        if (selectedServer != null) {
            mController.getManagerMain().getInterDataToMain().removeServer(selectedServer);
            serverList.remove(selectedServer);
            listViewServers.refresh();
        } else {
            mController.showErrorPopup("Vous devez sélectionner un serveur pour le supprimer.");
        }
    }

    /**
     * Import a profile from a file
     */
    @FXML
    public void importProfilClick() {
        File file = profileChooser.showOpenDialog(buttonConnect.getScene().getWindow());
        if (file != null) {
            try {
                mController.getManagerMain().getInterDataToMain().importProfileFile(file.getPath());
            } catch (UserAlreadyExistsException e) {
                mController.showErrorPopup("L'utilisateur existe déjà sur le poste ! Supprimez d'abord celui existant");;
            }
        }
    }

    /**
     * The login action has been refused by the server for a reason
     * @param reason The reason why the login action has been refused
     */
    public void loginRefused(String reason) {
        buttonConnect.setDisable(false);
        mController.showErrorPopup("Vous avez été rejeté par le serveur\n Raison: "+reason);
    }
}
