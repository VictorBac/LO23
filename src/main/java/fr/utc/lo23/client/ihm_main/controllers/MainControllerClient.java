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
 * Class used to manage the Main Window of the POKER App
 */
public class MainControllerClient extends Application {

    /**
     * Instance of the IHMMainClientManager
     */
    private static IHMMainClientManager managerMain;

    /**
     * Stage of the application
     */
    private Stage pmStage;

    private MainWindowController mainWindowController;
    private ConnectionController connectionWindowController;
    private CreateController createProfileController;
    private ViewOwnProfilController viewOwnProfilWindowController;
    private EditOwnProfilController editProfilWindowController;
    private ViewAutreProfilController viewAutreProfilWindowController;

    /**
     * Main function... (deprecated)
     * @param args useless
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Getter for the IHMainClientManager instance
     * @return instance of the IHMMainClientManager
     */
    public IHMMainClientManager getManagerMain(){
        return managerMain;
    }

    /**
     * Getter for the MainWindowController instance
     * @return instance of the MainWindowController
     */
    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    /**
     * Getter for the ViewAutreProfilController instance
     * @return instance of the ViewAutreProfilController
     */
    public ViewAutreProfilController getViewAutreProfilWindowController() {
        return viewAutreProfilWindowController;
    }

    /**
     * Getter of ConnectionController instance
     * @return instance of the ConnectionController
     */
    public ConnectionController getConnectionWindowController() { return connectionWindowController; }

    /**
     * Start point of the application (automatically called by JavaFX)
     * @param primaryStage Main stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
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

    /**
     * Function used to show the MainWindow after a valid response for the login action
     */
    public void userLoggedIn() {
        mainWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml", "Poker");
        if (mainWindowController != null) {
            mainWindowController.setConnectedUsers(managerMain.getConnectedUsers());
            mainWindowController.setTables(managerMain.getTables());
        }
    }

    /**
     * Function used to display the create profile GUI
     */
    public void clickCreateProfil() {
        createProfileController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/CreateProfil.fxml", "Création de profil");
    }

    /**
     * Function used to display the add server GUI
     */
    public void showAddServerWindow() {
        instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/AddServer.fxml", "Ajouter un serveur");
    }

    /**
     * Function used to display the connection window
     */
    public void showConnectionWindow() {
        connectionWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/Connection.fxml", "Connexion");
        if (connectionWindowController != null) {
            connectionWindowController.initServerlist();
        }
    }

    /**
     * Function used to display the main window
     */
    public void showMainWindow(){
        mainWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml","Poker");
        mainWindowController.setConnectedUsers(managerMain.getConnectedUsers());
        mainWindowController.setTables(managerMain.getTables());
    }

    /**
     * Function used to display the "view own profile" window
     */
    public void showViewOwnWindow(){
        viewOwnProfilWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/ViewOwnProfil.fxml", "Your Profile");
        if (viewOwnProfilWindowController != null) {
            viewOwnProfilWindowController.initData();
        }
    }

    /**
     * Function used to display the "edit profile" window
     */
    public void showEditProfilWindow(){
        editProfilWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/EditProfil.fxml", "Edit your profile");
        if (editProfilWindowController != null) {
            editProfilWindowController.initdata();
        }
    }

    /**
     * Function used to instantiate a window and display it
     * @param resource Path to the FXML template
     * @param windowTitle Title of the window
     * @param <T> Type of the controller for the window displayed
     * @return Controller for the window displayed
     */
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

    /**
     * Function used to show a popup
     * @param title Title of the popup
     * @param msg Message of the popup
     */
    public void showPopup(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Function used to show an error popup
     * @param msg Message of the popup
     */
    public void showErrorPopup(String msg) {
        showPopup("Erreur!", msg);
    }

    /**
     * Function used to instantiate an "View Other Profile" Window
     * @param user UserLight of the User we want to display informations
     * @param profilePane Instance of the pane where to display the window
     */
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

