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
 * Created by jbmartin on 18/11/15.
 */
public class MainWindowController extends BaseController {

    private ObservableList<UserLight> connectedUsers;

    private ObservableList<UserLight> playersInGame;

    private ObservableList<UserLight> spectatorsInGame;

    @FXML
    public ListView<UserLight> listViewConnectedUsers;

    @FXML
    private ListView<UserLight> listViewPlayersInGame;

    @FXML
    private ListView<UserLight> listViewSpectatorsInGame;

    @FXML
    private TableView<Table> tableViewCurrentTables;

    @FXML
    private TableColumn<Table, String> columnTableCreator, columnTableName, columnTablePlayers, columnTableSpectators;

    @FXML
    private TableColumn<Table, Integer> columnTableMise;

    @FXML
    public ListView<Table> listViewSavedTables;

    @FXML
    private Pane gamePane;

    @FXML
    private AnchorPane listPane;

    @FXML
    private Pane profilePane;

    private ObservableList<Table> tablesList;

    private ObservableList<Table> tablesSavedList;

    @FXML
    private Accordion accordionList;

    @FXML
    private TitledPane tpPlayersConnected;

    @FXML
    private TitledPane tpPlayersInGame;

    @FXML
    private TitledPane tpSpectatorsInGame;

    private FileChooser profileChooser;

    private static final int DOUBLE_CLICK = 2;

    // Boolean pour savoir si le joueur est en partie ou pas
    private boolean inGame = false;

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

    @FXML
    public void openViewOwnProfil() {
        mController.showViewOwnWindow();
    }

    public void showOtherProfile(ListView<UserLight> list) {
        if (list.getSelectionModel().getSelectedItem() != null) {
            profilePane.setVisible(true);
            profilePane.setDisable(false);
            mController.showOtherProfile(list.getSelectionModel().getSelectedItem(), profilePane);
            gamePane.setVisible(false);
            listPane.setVisible(false);
        }
    }

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

    @FXML
    public void joinTableAsPlayer() {
        joinTableAction(EnumerationTypeOfUser.PLAYER);
    }

    @FXML
    public void joinTableAsSpectator() {
        joinTableAction(EnumerationTypeOfUser.SPECTATOR);
    }


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

    public void joinRefusedTable() {
        mController.showErrorPopup("Impossible de rejoindre la table");
    }

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

    public void setConnectedUsers(List<UserLight> users) {
        connectedUsers = FXCollections.observableArrayList(users);
        listViewConnectedUsers.setItems(connectedUsers);
    }

    @FXML
    public void exportProfil() {
        File file = profileChooser.showSaveDialog(tableViewCurrentTables.getScene().getWindow());
        if (file != null) {
            mController.getManagerMain().getInterDataToMain().exportProfileFile(file.getPath());
        }
    }

    public void setTables(List<Table> tables) {
        tablesList = FXCollections.observableArrayList(tables);
        tableViewCurrentTables.setItems(tablesList);
        // Sale hack pour JavaFX <3
        tableViewCurrentTables.getColumns().get(0).setVisible(false);
        tableViewCurrentTables.getColumns().get(0).setVisible(true);
    }

    @FXML
    public void clickQuit() {
        try {
            mController.getManagerMain().getInterDataToMain().exitAsked();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

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
