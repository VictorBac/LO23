package fr.utc.lo23.server.ihm_main.controllers;

import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.server.ihm_main.IHMMainServerManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 01/12/2015.
 */

public class MainWindowController {

    private IHMMainServerManager manager;

    @FXML
    private AnchorPane bgimage;

    @FXML
    private TextField fieldPort;

    @FXML
    private Button buttonStop;

    @FXML
    private Button buttonStart;

    @FXML
    private Label labelIp;

    @FXML
    private TextArea textLog;

    public void setManager(IHMMainServerManager manager) {
        this.manager = manager;
    }

    public MainWindowController(){

    }

    public void initialize() {
        try {
            labelIp.setText(InetAddress.getLocalHost().getHostAddress());

            bgimage.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    if (buttonStart.isDisabled())
                        stopServer();
                    else
                        startServer();
                }

            });
        } catch (UnknownHostException e) {
            addTextLog("Erreur lors du lancement du serveur "+ e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void handleButtonStartAction(ActionEvent event) {
        startServer();
    }

    public void startServer()
    {
        try {
            int portNumber = Integer.parseInt(fieldPort.getText());
            try {
                manager.getInterfaceComToMain().start(portNumber);
                addTextLog("Starting server on port " + portNumber + "...");
                fieldPort.setDisable(true);
                buttonStart.setDisable(true);
                buttonStop.setDisable(false);
            } catch (NetworkFailureException e) {
                addTextLog("Erreur lors du lancement du serveur " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                addTextLog("Erreur lors du lancement du serveur " + e.getMessage());
                e.printStackTrace();
            }
        }catch (NumberFormatException e) {
            showErrorPopup("Error", "Le champ Port doit impérativement être un entier !");
        }
    }

    public void stopServer() {
        addTextLog("Stopping server ! qui ne marche pas actuellement (pas implémentée par com)");
        fieldPort.setDisable(false);
        buttonStart.setDisable(false);
        buttonStop.setDisable(true);
    }

    @FXML
    void handleButtonStopAction(ActionEvent event) {
        stopServer();
        // TODO quand ils l'auront implémentée
//        manager.getInterfaceComToMain().stop();
    }


    public void addTextLog(String msg)
    {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        textLog.appendText("[" + timestamp + "] " + msg + "\n");
    }



    private void showErrorPopup(String title, String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}
