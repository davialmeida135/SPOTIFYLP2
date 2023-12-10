package com.spotify.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;


public class RegistrarView extends Application{
	public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./templates/Registrar.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Registrar");
        stage.show();
        
    }
	
	public static void play_registrar(String[] args) {
		 launch(args);
	}
	
}