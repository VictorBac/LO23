package fr.utc.lo23.server.ihm_main.controllers;
/**
 * Created by jennypau on 24/11/15.
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {
    Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fr/utc/lo23/server/ihm_main/ui/Principal.fxml"));
        primaryStage.setTitle("Administration du serveur");
        mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        mainScene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/server/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/server/ihm_main/ui/poker.png')");
        primaryStage.show();
    }

    @FXML
    private void handleButtonStartAction(ActionEvent event) {
        // Button was clicked, do something...
        Button btnStart = (Button)event.getSource();
        btnStart.setDisable(true);

        Button btnStop = (Button)btnStart.getScene().lookup("#buttonStop");
        btnStop.setDisable(false);

        TextField textPort = (TextField)btnStart.getScene().lookup("#textPort");
        textPort.setDisable(true);

        TextArea textLog = (TextArea)btnStart.getScene().lookup("#textLog");
        textLog.appendText("Lancement du serveur\n");
    }

    @FXML
    private void handleButtonStopAction(ActionEvent event) {
        // Button was clicked, do something...
        Button btnStop = (Button)event.getSource();
        btnStop.setDisable(true);

        Button btnStart = (Button)btnStop.getScene().lookup("#buttonStart");
        btnStart.setDisable(false);

        TextField textPort = (TextField)btnStop.getScene().lookup("#textPort");
        textPort.setDisable(false);

        TextArea textLog = (TextArea)btnStop.getScene().lookup("#textLog");
        textLog.appendText("Fin du serveur\n");
    }
}
