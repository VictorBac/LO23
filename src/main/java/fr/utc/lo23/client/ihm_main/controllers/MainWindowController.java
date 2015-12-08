package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private static ListView<UserLight> listViewConnectedUsers;

    @FXML
    private TableView<Table> tableViewCurrentTables;

    @FXML
    private TableColumn<Table, String> columnTableCreator, columnTableName, columnTablePlayers, columnTableSpectators;

    @FXML
    private TableColumn<Table, Integer> columnTableMise;

    private ObservableList<Table> tablesList;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*listViewConnectedUsers.setCellFactory(column-> {
            return new ListCell<UserLight>(){
                @Override
                protected void updateItem(UserLight user, boolean empty){
                    if(user==null || empty)
                        setText(null);
                    else
                        setText(user.getPseudo());
                }
            };
        });*/

        columnTableCreator.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().getCreator().getPseudo());
            }
        });

        columnTableName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().getName());
            }
        });

        columnTableMise.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Table, Integer> param) {
                return new SimpleIntegerProperty(param.getValue().getMaxMise()).asObject();
            }
        });

        columnTablePlayers.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().getNbPlayerMin() + " / " + param.getValue().getListPlayers().getListUserLights().size() + " / " + param.getValue().getNbPlayerMax());
            }
        });

        columnTableSpectators.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Table, String> param) {
                return new SimpleStringProperty(param.getValue().isAcceptSpectator() ? "Oui" : "Non");
            }
        });

        tablesList = FXCollections.observableArrayList();
    }

    public void openViewOwnProfil(ActionEvent actionEvent) {
        mController.showViewOwnWindow();
    }

    public void createTable(ActionEvent actionEvent) {
        mController.getManagerMain().getInterTableToMain().showTableCreationForm(mController.showCreateTableView().getMainPane());
    }

    public void joinTable(ActionEvent actionEvent) {
        mController.getManagerMain().getInterDataToMain().joinTableWithMode(tableViewCurrentTables.getSelectionModel().getSelectedItem().getIdTable(),
                EnumerationTypeOfUser.PLAYER);
    }

    public void addTables(List<Table> currentTables) {
        tablesList = FXCollections.observableArrayList(currentTables);
        tableViewCurrentTables.setItems(tablesList);
    }

    public void testTablesView() {
        Table table1 = new Table("CCC", new UserLight("Dada"), true, false, 20, 10, false, 30, 5000);
        List<Table> list = new ArrayList<Table>();
        list.add(table1);
        mController.getManagerMain().getInterMainToData().currentTables(list);
    }
}
