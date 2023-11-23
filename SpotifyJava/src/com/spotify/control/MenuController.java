package com.spotify.control;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;



public class MenuController {
	@FXML
    private ListView<String> ListaPlaylists;
	@FXML
    private ImageView playButton;
	@FXML
	private Button testeBotao;

	
	@FXML
    void playClicked(ActionEvent event) {
		System.out.println("APERTA O PLAY");
		//ListaPlaylists.getItems().add("oi");
    }
	
	@FXML
	public void submit(ActionEvent event) throws Exception {
		ListaPlaylists.getItems().add("oi");
		
	}
	
	public void initialize() {
		System.out.println("Inicializou");
	}
	
	public void loadPlaylists(int userId) {
		//System.out.println("NUMERO UM");
		ListaPlaylists.getItems().add("number one213");
	}
	
	

    
}
