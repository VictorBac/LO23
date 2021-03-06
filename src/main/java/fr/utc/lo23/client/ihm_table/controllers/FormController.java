package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.views.TableView;
import fr.utc.lo23.common.data.Table;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controlleur du formulaire de création de table
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

    // Définition des formes régulières
    final Pattern texte = Pattern.compile("^[A-Za-z][a-z0-9 ]*$");
    final Pattern nombre = Pattern.compile("^[0-9]+$");

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
    TextField formBlinde;

    @FXML
    TextField formTempsMax;

    @FXML
    TextField formAnte;

    @FXML
    TextField formMoneyMax;

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
     * Vérifie la validité des données rentrées par l'utilisateur :
     * Le nom du formulaire doit commencer par une lettre, il peut contenir des lettres, des chiffres et des espaces
     * L'argent de départ max doit être non nul et un nombre positif
     * L'ante doit être non nul et un nombre positif
     * Le temps doit être non nul et un nombre positif
     * @param event
     */
    @FXML
    private void formSend(ActionEvent event){
        // Checking form values
        ArrayList<String> errors = new ArrayList<String>(); // Si une erreur est détectée, on l'ajoute dans cette liste
        errors.clear();
        // Réinitialisation des borders des champs
        clearStyle(formName);
        clearStyle(formBlinde);
        clearStyle(formTempsMax);
        clearStyle(formPlayerMin);
        clearStyle(formPlayerMax);
        clearStyle(formAnte);
        clearStyle(formMoneyMax);

        if (formName.getText().isEmpty() | !texte.matcher(formName.getText()).matches()){
            errors.add("Nom de la table");
            formName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }
        if (formBlinde.getText().isEmpty() | formBlinde.getText().equals("0") | !nombre.matcher(formBlinde.getText()).matches()){
            errors.add("Mise Max");
            formBlinde.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }
        if (formTempsMax.getText().isEmpty() | formTempsMax.getText().equals("0") | !nombre.matcher(formTempsMax.getText()).matches()){
            errors.add("Temps Max");
            formTempsMax.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        if (formAnte.getText().isEmpty() | formAnte.getText().equals("0") | !nombre.matcher(formAnte.getText()).matches()){
            errors.add("Ante");
            formAnte.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        if (formMoneyMax.getText().isEmpty() | formMoneyMax.getText().equals("0") | !nombre.matcher(formMoneyMax.getText()).matches()){
            errors.add("Money Max");
            formMoneyMax.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        Integer playerMin = Integer.valueOf(formPlayerMin.getValue().toString());
        Integer playerMax = Integer.valueOf(formPlayerMax.getValue().toString());

        if (playerMin > playerMax){
            errors.add("playerMin et playerMax");
            formPlayerMin.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            formPlayerMax.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        if (errors.isEmpty()){ // Toutes les valeurs sont correctement remplies
            // Récupération des valeurs rentrées par l'utilisateur
            String tableName = formName.getText();
            boolean spectator = formSpectatorYes.isSelected();
            boolean spectatorChat = formSpectatorChatYes.isSelected();
            boolean abandon = formAbandonYes.isSelected();
            Integer blinde = Integer.valueOf(formBlinde.getText());
            Integer tempsMax = Integer.valueOf(formTempsMax.getText());
            Integer ante = Integer.valueOf(formAnte.getText());
            Integer moneyMax = Integer.valueOf(formMoneyMax.getText());

            // Envoie de la table à Data
            ihmTable.getDataInterface().tableToCreate(new Table(tableName, ihmTable.getDataInterface().getUser(), spectator, spectatorChat, playerMax, playerMin, abandon, blinde, tempsMax, ante, moneyMax));
        }
    }

    /**
     * Réinitialise le style des champs de type Textfield
     * @param field
     */
    private void clearStyle(TextField field){
        field.setStyle("-fx-border-color: none ; -fx-border-width: none ;");
    }

    /**
     * Réinitialise le style des champs de type ComboBox
     * @param box
     */
    private void clearStyle(ComboBox box){
        box.setStyle("-fx-border-color: none ; -fx-border-width: none ;");
    }

    public void goToTable(Table t){
        TableView tableView = new TableView();
        tableView.createTable(getRoot(),t,ihmTable);
    }



}
