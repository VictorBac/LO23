package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.InterfaceDataFromIHMMain;
import fr.utc.lo23.client.data.InterfaceFromIHMMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 04/11/15.
 */
public class ConnectionController implements Initializable {
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
    void didButtonConnectClick(ActionEvent event) {
        System.out.println("didButtonConnectClick");

        String log = fieldUsername.getText();

        log = fieldUsername.getText();
        pass = fieldPassword.getText();

        //interfromIHMMAIN.logUser(log, pass);



        List<String> listerecue = new ArrayList<String>();
        listerecue.add("Premier");
        listerecue.add("Deuxieme");

        ObservableList<String> items = FXCollections.observableArrayList(listerecue);

        //ObservableList<String> items = FXCollections.observableArrayList("Serveur 1", "Cerberus 2", "World 3", "Europe 3");
        listViewServers.setItems(items);
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void change(ActionEvent actionEvent) {
    }

}
