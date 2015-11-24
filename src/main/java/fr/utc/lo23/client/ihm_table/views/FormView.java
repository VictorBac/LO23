package fr.utc.lo23.client.ihm_table.views;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.controllers.FormController;
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
            loader.setLocation(getClass().getResource("tableForm.fxml"));
            root.getChildren().setAll((Node) loader.load());
            FormController formController = (FormController) loader.getController();
            formController.setInterface(ihmTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
