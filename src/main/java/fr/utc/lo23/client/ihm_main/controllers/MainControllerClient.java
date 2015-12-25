package fr.utc.lo23.client.ihm_main.controllers;
/**
 * Created by jbmartin on 20/10/15.
 */

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Class use to manage the Main Window of the POKER App
 */
public class MainControllerClient extends Application {

    private static IHMMainClientManager managerMain;

    private Stage pmStage;

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

    public IHMMainClientManager getManagerMain(){
        return managerMain;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }
    public ViewAutreProfilController getViewAutreProfilWindowController() {
        return viewAutreProfilWindowController;
    }
    public ConnectionController getConnectionWindowController() { return connectionWindowController; }

    @Override
    public void start(Stage primaryStage) throws IOException {
        pmStage = primaryStage;
        // Catch the close event (ALT+F4)
        pmStage.setOnCloseRequest(event -> {
            try {
                managerMain.getInterDataToMain().exitAsked();
            } catch (NetworkFailureException e) {
                e.printStackTrace();
            }
        });

        managerMain = new IHMMainClientManager();
        managerMain.setControllerMain(this);
        showConnectionWindow();
    }


    public void userLoggedIn() {
        mainWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml", "Poker");
        if (mainWindowController != null) {
            mainWindowController.setConnectedUsers(managerMain.getConnectedUsers());
            mainWindowController.setTables(managerMain.getTables());
        }
    }

    public void clickCreateProfil() {
        createProfileController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/CreateProfil.fxml", "Création de profil");
    }


    public void showAddServerWindow() {
        instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/AddServer.fxml", "Ajouter un serveur");
    }

    public void showConnectionWindow() {
        connectionWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml", "Connexion");
        if (connectionWindowController != null) {
            connectionWindowController.initServerlist();
        }
    }

    public void showMainWindow(){
        mainWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml","Poker");
        mainWindowController.setConnectedUsers(managerMain.getConnectedUsers());
        mainWindowController.setTables(managerMain.getTables());
    }

    public void showViewOwnWindow(){
        viewOwnProfilWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/ViewOwnProfil.fxml", "Your Profile");
        if (viewOwnProfilWindowController != null) {
            viewOwnProfilWindowController.initData();
        }
    }

    public void showEditProfilWindow(){
        editProfilWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/EditProfil.fxml", "Edit your profile");
        if (editProfilWindowController != null) {
            editProfilWindowController.initdata();
        }
    }

    public CreateTableController showCreateTableView() {
        createTableController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/CreateTableWindow.fxml", "Création de table");
        return createTableController;
    }

    private <T extends BaseController>T instantiateWindow(String resource, String windowTitle) {
        Parent root;

        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(resource));
            root = fxmlLoader.load();
            T controller =  fxmlLoader.getController();
            controller.setMainController(this);
            pmStage.setTitle(windowTitle);
            Scene scene = new Scene(root);
            pmStage.setScene(scene);
            root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
            pmStage.setResizable(false);
            pmStage.centerOnScreen();
            pmStage.show();
            return controller;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void showPopup(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void showErrorPopup(String msg) {
        showPopup("Erreur!", msg);
    }

    public void showOtherProfile(UserLight user, Pane profilePane) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/ViewProfil.fxml"));
            profilePane.getChildren().setAll((Node) loader.load());
            ViewAutreProfilController controller = loader.getController();
            controller.setMainController(this);
            viewAutreProfilWindowController = controller;
            viewAutreProfilWindowController.initdata(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

