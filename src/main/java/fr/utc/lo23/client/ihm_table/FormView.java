package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Load the create table form fxml file
 */
public class FormView {
    public void createForm(Pane root,IHMTable ihmTable) {
        try {
            FXMLLoader loader = new FXMLLoader();
            root.getChildren().setAll((Node) loader.load(getClass().getResource("tableForm.fxml")));
            FormController formController = (FormController) loader.getController();
            formController.setInterface(ihmTable);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
