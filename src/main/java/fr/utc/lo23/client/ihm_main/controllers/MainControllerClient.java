package fr.utc.lo23.client.ihm_main.controllers;
/**
 * Created by jbmartin on 20/10/15.
 */

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.Server;
import fr.utc.lo23.common.data.UserLight;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Class use to manage the Main Window of the POKER App
 */
public class MainControllerClient extends Application {

    private Stage pmStage;

    private static IHMMainClientManager managerMain;

    public IHMMainClientManager getManagerMain(){
        return managerMain;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }
    public ViewAutreProfilController getViewAutreProfilWindowController() { return viewAutreProfilWindowController; }

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
        mainWindowController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/MainWindow.fxml", "Poker");
        if (mainWindowController != null) {
            mainWindowController.setConnectedUsers(managerMain.getConnectedUsers());
            mainWindowController.setTables(managerMain.getTables());
        }
    }

    public void ClickCreateProfil() {
        createProfileController = instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/CreateProfil.fxml", "Création de profile");
    }


    public void showAddServerWindow()
    {
        instantiateWindow("/fr/utc/lo23/client/ihm_main/ui/AddServer.fxml", "Ajouter un serveur");
    }

    public void showConnectionWindow()
    {
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

    private <T extends BaseController>T instantiateWindow(String resource, String windowTitle)
    {
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
            //root.getStylesheets().add(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/style.css").toExternalForm());
            root.setStyle("-fx-background-image: url('/fr/utc/lo23/client/ihm_main/ui/poker.png')");
            pmStage.centerOnScreen();
            pmStage.show();
            return controller;

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

    public void showOtherProfile(UserLight user, Pane profilePane) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fr/utc/lo23/client/ihm_main/ui/ViewProfil.fxml"));
            profilePane.getChildren().setAll((Node) loader.load());
            ViewAutreProfilController controller = loader.getController();
            controller.setMainController(this);
            viewAutreProfilWindowController = controller;
            if (viewAutreProfilWindowController != null)
                viewAutreProfilWindowController.initdata(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

