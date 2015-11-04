package fr.utc.lo23.client.ihm_main.controllers;/**
 * Created by jbmartin on 20/10/15.
 */

import com.guigarage.flatterfx.FlatterFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {

    public static void main(String[] args) {
        System.out.println("Hello");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/ConnectionWindow.fxml"));
        primaryStage.setTitle("Connexion");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        FlatterFX.style();
    }
}
