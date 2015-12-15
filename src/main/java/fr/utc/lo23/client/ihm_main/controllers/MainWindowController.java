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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

    private ObservableList<UserLight> connectedUsers;

    @FXML
    public ListView<UserLight> listViewConnectedUsers;

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

    private ObservableList<Table> tablesList;

    private ObservableList<Table> tablesSavedList;

    @FXML
    private Button buttonQuit;


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

        tablesSavedList = FXCollections.observableArrayList();
        //tablesSavedList = FXCollections.observableArrayList(mController.getManagerMain().getInterDataToMain().getSavedGamesList().getListTable());
        listViewSavedTables.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewConnectedUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (listViewConnectedUsers.getSelectionModel().getSelectedItem() != null)
                    mController.showAutreProfilWindow(listViewConnectedUsers.getSelectionModel().getSelectedItem());
            }
        });
        listViewSavedTables.setCellFactory(new Callback<ListView<Table>, ListCell<Table>>() {
            @Override
            public ListCell<Table> call(ListView<Table> param) {
                ListCell<Table> cell = new ListCell<Table>(){
                    @Override
                    protected void updateItem(Table t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " de " + t.getCreator().getPseudo());
                        }
                    }
                };
                return cell;
            }
        });
        listViewSavedTables.setItems(tablesSavedList);

        listViewConnectedUsers.setCellFactory(new Callback<ListView<UserLight>, ListCell<UserLight>>() {
            @Override
            public ListCell<UserLight> call(ListView<UserLight> param) {
                ListCell<UserLight> cell = new ListCell<UserLight>(){
                    @Override
                    protected void updateItem(UserLight t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getPseudo());
                        }
                    }
                };
                return cell;
            }
        });
        listViewConnectedUsers.setItems(connectedUsers);
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
        if (tableViewCurrentTables.getSelectionModel().getSelectedItem() != null) {
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
        else
        {
            mController.showErrorPopup("Erreur", "Vous devez sélectionner une table avant de pouvoir en rejoindre une");
        }
    }

    public void joinAcceptedTable(Table t, EnumerationTypeOfUser e) {
        gamePane.setVisible(true);
        gamePane.setDisable(false);
        gamePane.getStylesheets().clear();
        mController.getManagerMain().getInterTableToMain().joinTable(gamePane, t);
        listPane.setVisible(false);
    }

    public void joinRefusedTable(Table t) {
        mController.showErrorPopup("Erreur", "Impossible de rejoindre la table");
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

    public void setConnectedUsers(List<UserLight> users)
    {
        connectedUsers = FXCollections.observableArrayList(users);
        listViewConnectedUsers.setItems(connectedUsers);
    }

    public void ExportProfil(ActionEvent actionEvent) {
    }

    public void setTables(List<Table> tables) {
        tablesList = FXCollections.observableArrayList(tables);
        tableViewCurrentTables.setItems(tablesList);
    }

    public void ClickQuit(ActionEvent actionEvent) {
        try {
            mController.getManagerMain().getInterDataToMain().exitAsked();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }
}
