package com.spotify.control;

import java.io.IOException;

import com.spotify.model.Usuario;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;



public class MenuController {
	@FXML
    private ListView<String> ListaPlaylists;
	@FXML
    private ImageView playButton;
	@FXML
	private Button testeBotao;
    @FXML
    private ListView<String> listaMusicas;
	
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
		UserHolder holder = UserHolder.getInstance();
		Usuario loggedUser = holder.getUser();
		System.out.println("Inicializou");
		
		
		
		System.out.println(loggedUser.getNome());
		ListaPlaylists.getItems().add(Integer.toString(loggedUser.getId()));
	}
	
	public void loadPlaylists(int userId) {
		//System.out.println("NUMERO UM");
		ListaPlaylists.getItems().add("number one213");
	}
	
}
