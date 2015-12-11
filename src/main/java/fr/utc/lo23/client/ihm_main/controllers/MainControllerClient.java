package fr.utc.lo23.client.ihm_main.controllers;
/**
 * Created by jbmartin on 20/10/15.
 */

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainControllerClient extends Application {

    private Stage pmStage;

    private static IHMMainClientManager managerMain;

    public IHMMainClientManager getManagerMain(){
        return managerMain;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    private MainWindowController mainWindowController;
    private ConnectionController connectionWindowController;
    private CreateController createProfileController;
    private ViewOwnProfilController viewOwnProfilWindowController;
    private EditOwnProfilController editProfilWindowController;
    private ViewAutreProfilController viewAutreProfilWindowController;
    private CreateTableController createTableController;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        pmStage = primaryStage;
        managerMain = new IHMMainClientManager();
        managerMain.setControllerMain(this);
        showConnectionWindow();
    }


    public void userLoggedIn() {
        mainWindowController = instantiateWindow("../ui/MainWindow.fxml", "Poker");
        mainWindowController.setConnectedUsers(managerMain.getConnectedUsers());
    }

    public void ClickCreateProfil() {
        createProfileController = instantiateWindow("../ui/CreateProfil.fxml", "Création de profile");
    }


    public void showAddServerWindow()
    {
        instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/AddServer.fxml", "Ajouter un serveur");
    }

    public void showConnectionWindow()
    {
        connectionWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml", "Connexion");
    }

    public void showMainWindow(){
        mainWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml","Poker");
    }

    public void showViewOwnWindow(){
        viewOwnProfilWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/ViewOwnProfil.fxml", "Your Profile");
    }

    public void showEditProfilWindow(){
        editProfilWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/EditProfil.fxml", "Edit your profile");
    }

    public void showAutreProfilWindow(){
        viewAutreProfilWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/ViewProfil.fxml", "His/Her Profile");
    }

    public CreateTableController showCreateTableView() {
        createTableController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/CreateTableWindow.fxml", "Création de table");
        return createTableController;
    }

    private <T extends BaseController>T instantiateWindow(String resource, String windowTitle)
    {
        Parent root = null;

        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(resource));
            root = (Parent) fxmlLoader.load();
            T controller =  fxmlLoader.getController();
            controller.setMainController(this);
            pmStage.setTitle(windowTitle);
            Scene scene = new Scene(root);
            pmStage.setScene(scene);
            //root.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
            root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
            pmStage.show();
            return (T) controller;

        } catch (IOException e) {
            // TODO ?
            e.printStackTrace();
        }

        return null;
    }


    public void showErrorPopup(String title, String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}

