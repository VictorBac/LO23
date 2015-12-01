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


    private Stage pmStage;

    public static IHMMainClientManager getManagerMain() {
        return managerMain;
    }

    private static IHMMainClientManager managerMain;

    public static MainWindowController getMainWindowController() {
        return mainWindowController;
    }


    private static MainWindowController mainWindowController;



    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws IOException {

        managerMain = new IHMMainClientManager();

        pmStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ConnectionController controller = fxmlLoader.<ConnectionController>getController();
        controller.setMainController(this);
        primaryStage.setTitle("Connexion");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
        primaryStage.show();
    }


    public void userLoggedIn()
    {
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml"));
             root = (Parent) fxmlLoader.load();
                    mainWindowController = fxmlLoader.<MainWindowController>getController();
            mainWindowController.setMainController(this);
        } catch (IOException e) {
            // TODO ?
            e.printStackTrace();
        }
        pmStage.setTitle("Connexion");
        Scene scene = new Scene(root);
        pmStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
        pmStage.show();


    }

    public void ClickCreateProfil() {

        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/CreateProfil.fxml"));
            root = (Parent) fxmlLoader.load();
                mainWindowController = fxmlLoader.<MainWindowController>getController();
            mainWindowController.setMainController(this);
        } catch (IOException e) {
            // TODO ?
            e.printStackTrace();
        }
        pmStage.setTitle("Create Profile");
        Scene scene = new Scene(root);
        pmStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
        root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
        pmStage.show();

    }

}

