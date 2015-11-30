package fr.utc.lo23.client.ihm_table.views;

import fr.utc.lo23.client.ihm_table.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class MainView extends Application{

	public static void main (String[] args){
		launch(args);
	}
	
	public static Stage WindowStage = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../fxml/MainView.fxml"));
		Parent root = loader.load();
		
		primaryStage.setTitle("Test Main");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.setWidth(1225);
		primaryStage.setHeight(798);

	   	MainController controller = loader.getController();

		primaryStage.show();
		WindowStage = primaryStage;
	
	}
	
}
