package fr.utc.lo23.server.ihm_main.controllers;
/**
 * Created by jennypau on 24/11/15.
 */
import fr.utc.lo23.server.ihm_main.IHMMainServerManager;
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

public class MainControllerServer extends Application {
    Scene mainScene;

    //MAIN FUNCTION
    public static void main(String[] args) {
        IHMMainServerManager managerMain =  new IHMMainServerManager();
        MainControllerServer.manager = managerMain;
        MainControllerServer.launch(args);
    }

    public static IHMMainServerManager manager;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fr/utc/lo23/server/ihm_main/ui/MainWindow.fxml"));
        Parent root = loader.load();
        MainWindowController controller = loader.getController();
        primaryStage.setTitle("Administration du serveur");
        mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        mainScene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/server/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/server/ihm_main/ui/poker.png')");
        controller.setManager(manager);
        primaryStage.show();
    }


}
