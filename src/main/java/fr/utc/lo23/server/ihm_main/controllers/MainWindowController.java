package fr.utc.lo23.server.ihm_main.controllers;

import fr.utc.lo23.server.ihm_main.IHMMainServerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 01/12/2015.
 */

public class MainWindowController implements Initializable {

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

    @FXML
    void handleButtonStartAction(ActionEvent event) {
        addTextLog("Starting server...");
        fieldPort.setDisable(true);
        buttonStart.setDisable(true);
        buttonStop.setDisable(false);

        // TODO quand ils l'auront implémentée
//        IHMMainServerManager.getInterfaceComToData().start(fieldPort.getText());
    }

    @FXML
    void handleButtonStopAction(ActionEvent event) {
        addTextLog("Stopping server !");
        fieldPort.setDisable(false);
        buttonStart.setDisable(false);
        buttonStop.setDisable(true);

        // TODO quand ils l'auront implémentée
//        IHMMainServerManager.getInterfaceComToData().stop();
    }


    public void addTextLog(String msg)
    {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        textLog.appendText("[" + timestamp + "] " + msg + "\n");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("In MainWindowController");
        manager = new IHMMainServerManager();
    }
}
