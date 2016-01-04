package fr.utc.lo23.client.ihm_table.views;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.controllers.TableController;
import fr.utc.lo23.common.data.Table;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TableView extends Application{

	public void createTable(Pane root, Table table, IHMTable ihmTable) {
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fr/utc/lo23/client/ihm_table/fxml/TableView.fxml"));
			root.getChildren().setAll((Node) loader.load());
			TableController tableController = (TableController) loader.getController();
			tableController.setInterface(ihmTable);
			tableController.setTable(table);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}
