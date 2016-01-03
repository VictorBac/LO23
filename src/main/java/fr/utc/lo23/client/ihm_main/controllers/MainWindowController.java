package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.util.List;

/**
 * Controller for the Main Window
 * Created by jbmartin on 18/11/15.
 */
public class MainWindowController extends BaseController {

    /**
     * ObservableList of UserLight which represents the connected users
     */
    private ObservableList<UserLight> connectedUsers;

    /**
     * ObservableList of UserLight which represents the players on a table
     */
    private ObservableList<UserLight> playersInGame;

    /**
     * ObservableList of UserLight which represents the spectators on a table
     */
    private ObservableList<UserLight> spectatorsInGame;

    /**
     * ListView of UserLight which displays the connected users
     */
    @FXML
    public ListView<UserLight> listViewConnectedUsers;

    /**
     * ListView of UserLight which displays the players on a table
     */
    @FXML
    private ListView<UserLight> listViewPlayersInGame;

    /**
     * ListView of UserLight which displays the spectators on a table
     */
    @FXML
    private ListView<UserLight> listViewSpectatorsInGame;

    /**
     * TableView of Table which displays the current tables on the server
     */
    @FXML
    private TableView<Table> tableViewCurrentTables;

    /**
     * TableColumn of Table and String which displays various strings about a table. (Used in the above TableView)
     */
    @FXML
    private TableColumn<Table, String> columnTableCreator, columnTableName, columnTablePlayers, columnTableSpectators;

    /**
     * TableColumn of Table and Integer which displays the ante of the table
     */
    @FXML
    private TableColumn<Table, Integer> columnTableMise;

    /**
     * ListView of Table which represents the saved tables
     */
    @FXML
    public ListView<Table> listViewSavedTables;

    /**
     * Pane which displays a game
     */
    @FXML
    private Pane gamePane;

    /**
     * AnchorPane which displays the above ListViews
     */
    @FXML
    private AnchorPane listPane;

    /**
     * Pane which displays a profile
     */
    @FXML
    private Pane profilePane;

    /**
     * ObservableList of Table which represents current tables
     */
    private ObservableList<Table> tablesList;

    /**
     * ObservableList of Table which represents saved tables
     */
    private ObservableList<Table> tablesSavedList;

    /**
     * Accordion which manages ListViews of users
     */
    @FXML
    private Accordion accordionList;

    /**
     * TitledPane which contains the ListView of connected users
     */
    @FXML
    private TitledPane tpPlayersConnected;

    /**
     * TitledPane which contains the ListView of players on a table
     */
    @FXML
    private TitledPane tpPlayersInGame;

    /**
     * TitledPane which contains the ListView of spectators on a table
     */
    @FXML
    private TitledPane tpSpectatorsInGame;

    /**
     * FileChooser which handles the export action of the current profile
     */
    private FileChooser profileChooser;

    /**
     * Constant used to define a double click
     */
    private static final int DOUBLE_CLICK = 2;

    /**
     * Boolean which represents if the current user is in game (yes=true;no=false)
     */
    private boolean inGame = false;

    /**
     * Function used to initialize our JavaFX elements (called automatically)
     */
    public void initialize() {

        columnTableCreator.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCreator().getPseudo()));


        columnTableName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));

        columnTableMise.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getCurrentGame().getMaxStartMoney()).asObject());

        columnTablePlayers.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getNbPlayerMin() + " / " + param.getValue().getListPlayers().getListUserLights().size() + " / " + param.getValue().getNbPlayerMax()));

        columnTableSpectators.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().isAcceptSpectator() ? "Oui" : "Non"));

        tablesList = FXCollections.observableArrayList();
        tableViewCurrentTables.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewCurrentTables.setOnMouseClicked(event -> {
            // Double-click, l'utilisateur veut entrer en jeu
            if (event.getClickCount() == DOUBLE_CLICK) {
                joinTableAction(EnumerationTypeOfUser.PLAYER);
            }
        });
        tableViewCurrentTables.setPlaceholder(new Label("Aucune table en cours actuellement..."));

        tablesSavedList = FXCollections.observableArrayList();
        listViewSavedTables.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewConnectedUsers.setOnMouseClicked(event -> {
            if (event.getClickCount() == DOUBLE_CLICK) {
                showOtherProfile(listViewConnectedUsers);
            }
        });

        listViewPlayersInGame.setOnMouseClicked(event -> {
            if (event.getClickCount() == DOUBLE_CLICK) {
                showOtherProfile(listViewPlayersInGame);
            }
        });

        listViewSpectatorsInGame.setOnMouseClicked(event -> {
            if (event.getClickCount() == DOUBLE_CLICK) {
                showOtherProfile(listViewSpectatorsInGame);
            }
        });

        listViewSavedTables.setCellFactory(new Callback<ListView<Table>, ListCell<Table>>() {
            @Override
            public ListCell<Table> call(ListView<Table> param) {
                return new ListCell<Table>(){
                    @Override
                    protected void updateItem(Table t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " de " + t.getCreator().getPseudo());
                        }
                    }
                };
            }
        });
        listViewSavedTables.setItems(tablesSavedList);
        Callback<ListView<UserLight>, ListCell<UserLight>> cb =
                new Callback<ListView<UserLight>, ListCell<UserLight>>() {
            @Override
            public ListCell<UserLight> call(ListView<UserLight> param) {
                return new ListCell<UserLight>(){
                    @Override
                    protected void updateItem(UserLight t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getPseudo());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        };

        listViewConnectedUsers.setCellFactory(cb);
        listViewPlayersInGame.setCellFactory(cb);
        listViewSpectatorsInGame.setCellFactory(cb);

        listViewConnectedUsers.setItems(connectedUsers);
        listViewPlayersInGame.setItems(playersInGame);
        listViewSpectatorsInGame.setItems(spectatorsInGame);

        profileChooser = new FileChooser();
        profileChooser.setTitle("Exporter un profil");

        accordionList.setExpandedPane(tpPlayersConnected);
    }

    /**
     * Function used to display "View Own Profile" window
     */
    @FXML
    public void openViewOwnProfil() {
        mController.showViewOwnWindow();
    }

    /**
     * Function used to show "View a distant profile" window
     * @param list ListView of UserLight we want to attach this function
     */
    public void showOtherProfile(ListView<UserLight> list) {
        if (list.getSelectionModel().getSelectedItem() != null) {
            profilePane.setVisible(true);
            profilePane.setDisable(false);
            mController.showOtherProfile(list.getSelectionModel().getSelectedItem(), profilePane);
            gamePane.setVisible(false);
            listPane.setVisible(false);
        }
    }

    /**
     * Function used to show the gamePane and hide others.
     */
    @FXML
    public void createTable() {
        gamePane.setVisible(true);
        gamePane.setDisable(false);
        gamePane.getStylesheets().clear();
        mController.getManagerMain().getInterTableToMain().showTableCreationForm(gamePane);
        listPane.setVisible(false);
        profilePane.setVisible(false);

        inGame = true;
    }

    /**
     * Function used when a user wants to join a table (by double-click or pressed "Rejoindre" button)
     * @param mode EnumerationTypeOfUser the user wants to enter on the table
     */
    public void joinTableAction(EnumerationTypeOfUser mode) {
        if (tableViewCurrentTables.getSelectionModel().getSelectedItem() != null) {
            try {
                mController.getManagerMain().getInterDataToMain().joinTableWithMode(tableViewCurrentTables.getSelectionModel().getSelectedItem().getIdTable(),
                        mode);
            } catch (FullTableException e) {
                mController.showErrorPopup("Table pleine !");
                e.printStackTrace();
            } catch (NetworkFailureException e) {
                mController.showErrorPopup("Erreur réseau !");
                e.printStackTrace();
            }
        } else {
            mController.showErrorPopup("Vous devez sélectionner une table avant de pouvoir en rejoindre une");
        }
    }

    /**
     * Function used to join a table as a player (see above)
     */
    @FXML
    public void joinTableAsPlayer() {
        joinTableAction(EnumerationTypeOfUser.PLAYER);
    }

    /**
     * Function used to join a table as a spectator (see above)
     */
    @FXML
    public void joinTableAsSpectator() {
        joinTableAction(EnumerationTypeOfUser.SPECTATOR);
    }

    /**
     * Function used after a positive response of the server for a join action
     * @param t Instance of the table the user wants to join
     * @param e EnumerationTypeOfUser the user wants to join
     */
    public void joinAcceptedTable(Table t, EnumerationTypeOfUser e) {
        gamePane.setVisible(true);
        gamePane.setDisable(false);
        gamePane.getStylesheets().clear();
        mController.getManagerMain().getInterTableToMain().joinTable(gamePane, t);
        listPane.setVisible(false);
        profilePane.setVisible(false);

        playersInGame = FXCollections.observableArrayList(t.getListPlayers().getListUserLights());
        spectatorsInGame = FXCollections.observableArrayList(t.getListSpectators().getListUserLights());
        listViewPlayersInGame.setItems(playersInGame);
        listViewSpectatorsInGame.setItems(spectatorsInGame);
        tpPlayersInGame.setVisible(true);
        tpPlayersInGame.setDisable(false);
        tpSpectatorsInGame.setVisible(true);
        tpSpectatorsInGame.setDisable(false);
        if (e.equals(EnumerationTypeOfUser.PLAYER)) {
            accordionList.setExpandedPane(tpPlayersInGame);
        } else {
            accordionList.setExpandedPane(tpSpectatorsInGame);
        }

        inGame = true;
    }

    /**
     * Function used after a negative response of the server after a join action
     * Show a popup
     */
    public void joinRefusedTable() {
        mController.showErrorPopup("Impossible de rejoindre la table");
    }

    /**
     * Function used after a positive response of the server for a table creation
     * @param t Instance of the table the user created
     */
    public void joinCreatedTable(Table t) {
        playersInGame = FXCollections.observableArrayList(t.getListPlayers().getListUserLights());
        spectatorsInGame = FXCollections.observableArrayList(t.getListSpectators().getListUserLights());
        listViewPlayersInGame.setItems(playersInGame);
        listViewSpectatorsInGame.setItems(spectatorsInGame);
        accordionList.setExpandedPane(tpPlayersInGame);
        tpPlayersInGame.setVisible(true);
        tpPlayersInGame.setDisable(false);
        tpSpectatorsInGame.setVisible(true);
        tpSpectatorsInGame.setDisable(false);

        inGame = true;
    }

    /**
     * Function used to refresh the ListView of connected users
     * @param users List of UserLight which contains the connected users
     */
    public void setConnectedUsers(List<UserLight> users) {
        connectedUsers = FXCollections.observableArrayList(users);
        listViewConnectedUsers.setItems(connectedUsers);
        listViewConnectedUsers.refresh();
    }

    /**
     * Function used to handle an export profile action
     */
    @FXML
    public void exportProfil() {
        File file = profileChooser.showSaveDialog(tableViewCurrentTables.getScene().getWindow());
        if (file != null) {
            mController.getManagerMain().getInterDataToMain().exportProfileFile(file.getPath());
        }
    }

    /**
     * Function used to refresh the TableView of current tables
     * @param tables List of Table which represents the current tables
     */
    public void setTables(List<Table> tables) {
        tablesList = FXCollections.observableArrayList(tables);
        tableViewCurrentTables.setItems(tablesList);
        // Sale hack pour JavaFX <3
        tableViewCurrentTables.getColumns().get(0).setVisible(false);
        tableViewCurrentTables.getColumns().get(0).setVisible(true);
    }

    /**
     * Function used after an action on the "Quit" button
     */
    @FXML
    public void clickQuit() {
        try {
            mController.getManagerMain().getInterDataToMain().exitAsked();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

    /**
     * Function called after a "View distant profile" window to display the correct pane
     */
    public void backFromViewProfile() {
        profilePane.setVisible(false);
        if (inGame) {
            // On est en jeu, on doit réafficher la table de jeu
            gamePane.setVisible(true);
            gamePane.setDisable(false);
        } else {
            // Sinon on affiche le menu principal
            listPane.setVisible(true);
        }
    }

    /**
     * Function called after the local user quits a table to display the correct pane
     */
    public void backFromGame() {
        gamePane.setVisible(false);
        gamePane.setDisable(true);
        listPane.setVisible(true);
        inGame = false;

        tpPlayersInGame.setVisible(false);
        tpPlayersInGame.setDisable(true);
        tpSpectatorsInGame.setVisible(false);
        tpSpectatorsInGame.setDisable(true);

        accordionList.setExpandedPane(tpPlayersConnected);
    }

    /**
     * Function called when a distant user join the local table to add him to the ListViews
     * @param userWhoJoinTheTable UserLight of the user who joins the table
     * @param typeOfUserWhoJoinTable EnumerationTypeOfUser of the user who joins the table
     */
    public void userJoinTableLocal(UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable) {
        if (typeOfUserWhoJoinTable.equals(EnumerationTypeOfUser.PLAYER)) {
            playersInGame.add(userWhoJoinTheTable);
            listViewPlayersInGame.setItems(playersInGame);
            listViewPlayersInGame.refresh();
        } else {
            spectatorsInGame.add(userWhoJoinTheTable);
            listViewSpectatorsInGame.setItems(spectatorsInGame);
            listViewSpectatorsInGame.refresh();
        }
    }

    /**
     * Function called when a distant user left the local table to remove him from the ListViews
     * @param userLightLeavingGame UserLight of the user who left the table
     * @param typeOfUserWhoLeftTable EnumerationTypeOfUser of the user who left the table
     */
    public void userLeftTableLocal(UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
        if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)) {
            playersInGame.remove(userLightLeavingGame);
            listViewPlayersInGame.setItems(playersInGame);
            listViewPlayersInGame.refresh();
        } else {
            spectatorsInGame.remove(userLightLeavingGame);
            listViewSpectatorsInGame.setItems(spectatorsInGame);
            listViewSpectatorsInGame.refresh();
        }
    }
}
