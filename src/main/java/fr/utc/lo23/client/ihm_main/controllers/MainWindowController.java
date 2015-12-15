package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by jbmartin on 18/11/15.
 */
public class MainWindowController extends BaseController {
    @FXML
    public ListView<UserLight> listViewConnectedUsers;

    @FXML
    private TableView<Table> tableViewCurrentTables;

    @FXML
    private TableColumn<Table, String> columnTableCreator, columnTableName, columnTablePlayers, columnTableSpectators;

    @FXML
    private TableColumn<Table, Integer> columnTableMise;

    @FXML
    private Pane gamePane;

    @FXML
    private AnchorPane listPane;

    private ObservableList<Table> tablesList;

    @FXML
    private Button buttonQuit;

    public void addUsers(List<UserLight> users) {
        for (UserLight user : users) {
            addUser(user);
        }
    }

    public void addUser(UserLight user){
        listViewConnectedUsers.getItems().add(user);
    }
    public void removeUser(UserLight user){
        listViewConnectedUsers.getItems().remove(user);
    }

    public void change(ActionEvent actionEvent) {
    }

    public void initialize(URL location, ResourceBundle resources) {

        columnTableCreator.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().getCreator().getPseudo());
            }
        });


        columnTableName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().getName());
            }
        });

        columnTableMise.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Table, Integer> param) {
                return new SimpleIntegerProperty(param.getValue().getCurrentGame().getMaxStartMoney()).asObject();
            }
        });

        columnTablePlayers.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().getNbPlayerMin() + " / " + param.getValue().getListPlayers().getListUserLights().size() + " / " + param.getValue().getNbPlayerMax());
            }
        });

        columnTableSpectators.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().isAcceptSpectator() ? "Oui" : "Non");
            }
        });

        tablesList = FXCollections.observableArrayList();
        tableViewCurrentTables.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void openViewOwnProfil(ActionEvent actionEvent) {
        mController.showViewOwnWindow();
    }

    @FXML
    public void createTable(ActionEvent actionEvent) {
        //mController.getManagerMain().getInterTableToMain().showTableCreationForm(mController.showCreateTableView().getMainPane());
        gamePane.setVisible(true);
        gamePane.setDisable(false);
        gamePane.getStylesheets().clear();
        mController.getManagerMain().getInterTableToMain().showTableCreationForm(gamePane);
        listPane.setVisible(false);
    }

    public void joinTable(ActionEvent actionEvent) {
        try {
            mController.getManagerMain().getInterDataToMain().joinTableWithMode(tableViewCurrentTables.getSelectionModel().getSelectedItem().getIdTable(),
                    EnumerationTypeOfUser.PLAYER);
        } catch (FullTableException e) {
            mController.showErrorPopup("Erreur", "Table pleine !");
        } catch (NetworkFailureException e) {
            mController.showErrorPopup("Erreur", "Erreur réseau !");
            e.printStackTrace();
        }
    }

    public void addTables(List<Table> currentTables) {
        tablesList = FXCollections.observableArrayList(currentTables);
        tableViewCurrentTables.setItems(tablesList);
    }

    /*public void testTablesView() {
        Table table1 = new Table("CCC", new UserLight("Dada"), true, false, 20, 10, false, 30, 5000);
        List<Table> list = new ArrayList<Table>();
        list.add(table1);
        mController.getManagerMain().getInterMainToData().currentTables(list);
    }*/


    @FXML
    void didClickQuitButton(ActionEvent event) {
        try {
            mController.getManagerMain().getInterDataToMain().exitAsked();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }
}
