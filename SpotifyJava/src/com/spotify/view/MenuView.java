package com.spotify.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.spotify.control.MenuController;

public class MenuView extends Application{
	private Parent root;
	public Scene generateScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("templates/MainMenu.fxml"));
        Scene scene = new Scene(loader.load());
        return scene;
	}
	
	public void start(Stage stage) throws Exception {
        
        stage.setScene(generateScene());
        stage.setTitle("Menu");
        stage.show();
        //FUNCAO DE INICIALIZACAO NO MENU CONTROLLER
              
    }
	public void start(Stage stage, int userId) throws Exception {
	        
	        start(stage);
	        //FUNCAO DE INICIALIZACAO NO MENU CONTROLLER
	        MenuController test = new MenuController();
	        test.loadPlaylists(userId);
	        System.out.println("CHEGUEI AQUIEIN");        
	    }
	
	public static void play_menu(int user_id,String[] args) {
		launch(args);
	}
}
