package fr.utc.lo23.client.ihm_table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Controller of the create table form
 */
public class FormController {

    private IHMTable ihmTable;

    public void setInterface(IHMTable ihmTable){ this.ihmTable = ihmTable; }

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
    TextField formBlinde;

    @FXML
    TextField formAnte;

    @FXML
    Button formSend;

    @FXML
    public void initialize() {
        formSend.setOnAction((actionEvent) -> formSend(actionEvent));
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
        }
        if (formMiseMax.getText().isEmpty()){
            errors.add("Mise Max");
        }
        if (formTempsMax.getText().isEmpty()){
            errors.add("Temps Max");
        }
        if (formBlinde.getText().isEmpty()){
            errors.add("Blinde");
        }
        if (formAnte.getText().isEmpty()){
            errors.add("Ante");
        }

        if (errors.isEmpty()){ // Toutes les valeurs sont correctement remplies

        }
    }

}
