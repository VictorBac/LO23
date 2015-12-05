package fr.utc.lo23.client.ihm_main.controllers;
/**
 * Created by jbmartin on 20/10/15.
 */

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainControllerClient extends Application {


    public static void main(String[] args) {
        IHMMainClientManager manager = new IHMMainClientManager();
        managerMain = manager;
        launch(args);
    }


    private Stage pmStage;

    public static IHMMainClientManager managerMain;

    public IHMMainClientManager getManagerMain(){
        return managerMain;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    private MainWindowController mainWindowController;
    private ConnectionController connectionWindowController;
    private CreateController createProfileController;



    @Override
    public void start(Stage primaryStage) throws IOException {

        pmStage = primaryStage;
        connectionWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml", "Connexion");
    }


    public void userLoggedIn() {
        mainWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml", "Poker");
    }

    public void ClickCreateProfil() {
        createProfileController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/CreateProfil.fxml", "Création de profile");
    }




    private <T extends BaseController>T instantiateWindow(String ressource, String windowTitle)
    {
        Parent root = null;

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ressource));
            root = (Parent) fxmlLoader.load();
            T controller = fxmlLoader.getController();
            controller.setMainController(this);
            pmStage.setTitle(windowTitle);
            Scene scene = new Scene(root);
            pmStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
            root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
            pmStage.show();
            return (T) controller;

        } catch (IOException e) {
            // TODO ?
            e.printStackTrace();
        }

        return null;
    }

}

