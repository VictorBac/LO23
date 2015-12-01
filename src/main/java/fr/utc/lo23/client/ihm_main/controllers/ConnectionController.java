package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.InterfaceDataFromIHMMain;
import fr.utc.lo23.client.data.InterfaceFromIHMMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
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
    void didButtonConnectClick(ActionEvent event) throws IOException {
        System.out.println("didButtonConnectClick");

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        URL tmp = getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/principal.fxml");
        System.out.println(tmp.toString());
        Parent root = FXMLLoader.load(tmp);/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
        stage.show();
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
