package com.spotify.control;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;



public class MenuController {
	@FXML
    private ListView<String> ListaPlaylists;
	@FXML
    private ImageView playButton;

	
	@FXML
    void playClicked(ActionEvent event) {
		System.out.println("APERTA O PLAY");
		//ListaPlaylists.getItems().add("oi");
    }

    
}
