package fr.utc.lo23.client.ihm_main.controllers;
/**
 * Created by jbmartin on 20/10/15.
 */

import fr.utc.lo23.client.data.DataManagerClient;
import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.data.InterfaceDataFromIHMMain;
import fr.utc.lo23.client.data.InterfaceDataFromIHMTable;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.client.network.NetworkManagerClient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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



    private DataManagerClient DataClient;
    private IHMMainClientManager IHMmainClient;
    private NetworkManagerClient NetworkClient;



    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws IOException {

        DataClient = new DataManagerClient();
        IHMmainClient = new IHMMainClientManager(DataClient.getInterFromIHMMain());
        //NetworkClient = new NetworkManagerClient(DataClient.getInterFromCom());  pull les nouveaux trucs de com.
        //DataClient.setInterToIHMMain(IHMmainClient.getInterface qu'on envoie a DATA');
        DataClient.setInterToCom(NetworkClient);
        

        // Il faut passer en parametre de chaque controleur, la réference au manager IHM main qui contient tout pour passer
        // d'une fenetre à une autre.

        Parent root = FXMLLoader.load(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml"));
        primaryStage.setTitle("Connexion");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
        primaryStage.show();
            }


}
