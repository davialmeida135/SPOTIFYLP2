package com.spotify.view;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

/**
 * Classe que carrega o Login.fxml
 * */
public class LoginView extends Application{
	public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./templates/Login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        
    }
	
	public static void play_login(String[] args) {
		 launch(args);
	}
	
}
