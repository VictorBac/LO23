package fr.utc.lo23.client.ihm_main.controllers;

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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller used to manage the Connection Window
 * Created by jbmartin on 04/11/15.
 */
public class ConnectionController extends BaseController {
    @FXML
    private AnchorPane bgimage;

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

    private FileChooser profileChooser;

    /**
     * Start the connection with the server
     * @throws IOException
     */


    @FXML
    /** On a click to connect, we start our function that manage it
     *
     */
    public void didButtonConnectClick(ActionEvent event) throws IOException {
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
        if (listViewServers.getSelectionModel().getSelectedItem() == null)
        {
            mController.showErrorPopup("Erreur", "Vous devez sélectionner au moins un serveur !");
            return;
        }
        String ip = listViewServers.getSelectionModel().getSelectedItem().getAddress();
        Integer port = listViewServers.getSelectionModel().getSelectedItem().getPort();

        if (login.isEmpty()|| passwd.isEmpty())
        {
            mController.showErrorPopup("Erreur", "Vous devez insérer un nom d'utilisateur et un mot de passe");
            return;
        }

        try { // User logged in
            IHMMainClientManager manager = mController.getManagerMain();
            manager.getInterDataToMain().logUser(login,passwd,ip,port);
            //TODO asynchronous...
            mController.userLoggedIn();
        } catch (LoginNotFoundException e) {
            mController.showErrorPopup("Erreur", "Nom d'utilisateur inexistant sur le poste.");

        } catch (WrongPasswordException e) {
            mController.showErrorPopup("Erreur", "Mot de passe incorrect.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Override display methods that display the listview of Servers
     */
    //@Override
    public void initialize(URL location, ResourceBundle resources) {
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
        profileChooser = new FileChooser();
        profileChooser.setTitle("Importer un profil");

        bgimage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                sendAction();
        });
    }

    /**
     * initialization of the Server list
     */
    public void initServerlist()
    {
        serverList.setAll(mController.getManagerMain().getInterDataToMain().getServersList());
        listViewServers.getSelectionModel().select(0);
    }


    /**
     * Open the window to create a new profil
     */
    public void CreateProfilClick(ActionEvent actionEvent) {
        System.out.println("CreateProfilButton");
        mController.ClickCreateProfil();

    }

    /**
     * Open the window to create a new server
     */
    @FXML
    public void didClickAddServerButton(ActionEvent event) {
        mController.showAddServerWindow();
    }

    /**
     * Delete the selected server
     */
    @FXML
    public void didClickRemoveServerButton(ActionEvent event)
    {
        Server selectedServer = listViewServers.getSelectionModel().getSelectedItem();
        if (selectedServer != null) {
            mController.getManagerMain().getInterDataToMain().removeServer(selectedServer);
            serverList.remove(selectedServer);
            listViewServers.refresh();
        } else {
            mController.showErrorPopup("Erreur", "Vous devez sélectionner un serveur pour le supprimer.");
        }
    }

    /**
     * Import a profile from a file
     */
    public void ImportProfilClick(ActionEvent actionEvent) {
        File file = profileChooser.showOpenDialog(buttonConnect.getScene().getWindow());
        if (file != null) {
            mController.getManagerMain().getInterDataToMain().importProfileFile(file.getPath());
        }
    }
}
