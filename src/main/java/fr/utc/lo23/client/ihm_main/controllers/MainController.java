package fr.utc.lo23.client.ihm_main.controllers;
/**
 * Created by jbmartin on 20/10/15.
 */

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.data.InterfaceDataFromIHMMain;
import fr.utc.lo23.client.data.InterfaceDataFromIHMTable;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;
import fr.utc.lo23.client.network.InterfaceClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {

    public static InterfaceDataFromIHMMain getIntfaceDataToMain() {
        return intfaceDataToMain;
    }

    public static InterfaceDataFromCom getInterfaceDataToCom() {
        return interfaceDataToCom;
    }

    public static InterfaceDataFromIHMTable getInterfaceDataToTable() {
        return interfaceDataToTable;
    }

    public static InterfaceMainToData getInterfaceMainToData() {
        return interfaceMainToData;
    }

    public static InterfaceMainToTable getInterfaceMainToTable() {
        return interfaceMainToTable;
    }

    public static InterfaceClient getInterfaceComToData() {
        return interfaceComToData;
    }



    /**
     * Interfaces from DATA
     */
    private static InterfaceDataFromIHMMain intfaceDataToMain;
    private static InterfaceDataFromCom interfaceDataToCom;
    private static InterfaceDataFromIHMTable interfaceDataToTable;

    /**
     * Interfaces from MAIN
     */
    private static InterfaceMainToData interfaceMainToData;
    private static InterfaceMainToTable interfaceMainToTable;

    /**
     * Interfaces from COM
     */
    private static InterfaceClient interfaceComToData;




    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml"));
        primaryStage.setTitle("Connexion");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
        primaryStage.show();
    }
}
