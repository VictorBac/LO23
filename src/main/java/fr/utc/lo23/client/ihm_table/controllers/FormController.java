package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.views.TableView;
import fr.utc.lo23.common.data.Table;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Controller of the create table form
 */
public class FormController {

    private IHMTable ihmTable;
    private Pane root;

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void setInterface(IHMTable ihmTable){
        this.ihmTable = ihmTable;
        ihmTable.setFormController(this);
    }

    public FormController() {

    }

    @FXML
    TextField formName;

    @FXML
    ComboBox formPlayerMin;

    @FXML
    ComboBox formPlayerMax;

    @FXML
    RadioButton formSpectatorYes;
    @FXML
    RadioButton formSpectatorNo;

    @FXML
    RadioButton formSpectatorChatYes;
    @FXML
    RadioButton formSpectatorChatNo;

    @FXML
    RadioButton formAbandonYes;
    @FXML
    RadioButton formAbandonNo;

    @FXML
    TextField formMiseMax;

    @FXML
    TextField formTempsMax;

    @FXML
    Button formSend;

    @FXML
    public void initialize() {
        formSend.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                formSend(event);
            }
        });
    }
    /**
     * Checks form values.
     * @param event
     */
    @FXML
    private void formSend(ActionEvent event){
        // Checking form values
        ArrayList<String> errors = new ArrayList<String>(); // Si une erreur est détectée, on l'ajoute dans cette liste
        if (formName.getText().isEmpty()){
            errors.add("Nom de la table");
            formName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }
        if (formMiseMax.getText().isEmpty()){
            errors.add("Mise Max");
        }
        if (formTempsMax.getText().isEmpty()){
            errors.add("Temps Max");
        }

        if (errors.isEmpty()){ // Toutes les valeurs sont correctement remplies
            // Getting values
            String tableName = formName.getText();
            Integer playerMin = Integer.valueOf(formPlayerMin.getValue().toString());
            Integer playerMax = Integer.valueOf(formPlayerMax.getValue().toString());
            boolean spectator = formSpectatorYes.isSelected();
            boolean spectatorChat = formSpectatorChatYes.isSelected();
            boolean abandon = formAbandonYes.isSelected();
            Integer miseMax = Integer.valueOf(formMiseMax.getText());
            Integer tempsMax = Integer.valueOf(formTempsMax.getText());

            // Sending Table to Data
            ihmTable.getDataInterface().tableToCreate(new Table(tableName, null, spectator, spectatorChat, playerMax, playerMin, abandon, miseMax, tempsMax));
        }
    }

    public void goToTable(Table t){
        TableView tableView = new TableView();
        tableView.createTable(getRoot(),t,ihmTable);
    }



}
