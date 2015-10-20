package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainController {

	private TableToMainListener tableToMainListener = new TableToMainListener();

	@FXML
	public Pane IAmYourPaneLuke;

	public MainController(){
		//constructor
	}
	
	@FXML
	private void launchTable(javafx.event.ActionEvent event) {
		tableToMainListener.showTableCreationForm(IAmYourPaneLuke);
	}
	
}
