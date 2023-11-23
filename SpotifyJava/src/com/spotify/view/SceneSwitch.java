package com.spotify.view;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneSwitch {
	public SceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException{
		
		AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
		currentAnchorPane.getChildren().removeAll();
		currentAnchorPane.getChildren().setAll(nextAnchorPane);
	}
	
	public static void SceneSwitchMenu(Stage stage) {
		
	}
}
