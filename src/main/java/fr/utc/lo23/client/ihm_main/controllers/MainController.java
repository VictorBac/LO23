package fr.utc.lo23.client.ihm_main.controllers;
/**
 * Created by jbmartin on 20/10/15.
 */

import fr.utc.lo23.client.data.DataManagerClient;
import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {


    private static IHMMainClientManager managerMain;



    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws IOException {

        managerMain = new IHMMainClientManager();


        Parent root = FXMLLoader.load(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml"));
        primaryStage.setTitle("Connexion");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
        primaryStage.show();
            }


}
