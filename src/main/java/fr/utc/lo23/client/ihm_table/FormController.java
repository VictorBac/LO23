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
        ArrayList<String> errors = new ArrayList<String>(); // If an error is detected, we add it in this list
        if (formName.getText()==null){
            errors.add("Nom de la table");
            System.out.println("Nom de table vide !");
        }
    }

}
