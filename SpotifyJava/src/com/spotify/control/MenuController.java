package com.spotify.control;

import java.io.IOException;
import java.sql.Connection;

import com.spotify.model.Usuario;
import com.spotify.view.LoginView;
import com.spotify.view.MenuView;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class MenuController {
	@FXML
    private ListView<String> ListaPlaylists;
    @FXML
    private ListView<String> listaMusicas;
    @FXML
    private Label typeHolder;
    @FXML
    private Label usernameHolder;
    @FXML
    private Label nameHolder;

    UserHolder holder=UserHolder.getInstance();
    Usuario loggedUser=holder.getUser();
    
	@FXML
    void playClicked(ActionEvent event) {
		System.out.println("APERTA O PLAY");
		//ListaPlaylists.getItems().add("oi");
    }
	
	@FXML
	public void logOut(ActionEvent event) throws Exception {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		LoginView login = new LoginView();
		login.start(stage);
	}
	
	public void initialize() {
		
		System.out.println("Inicializou");
		
		nameHolder.setText(loggedUser.getNome());
		usernameHolder.setText(loggedUser.getUsuario());
		typeHolder.setText(loggedUser.getTipo());
		
		
		
		
		System.out.println(loggedUser.getNome());
		ListaPlaylists.getItems().add(Integer.toString(loggedUser.getId()));
	}
	
	public void loadPlaylists(int userId,Connection conn) {
		//System.out.println("NUMERO UM");
		ListaPlaylists.getItems().add("number one213");
		ListaPlaylists.getItems().add(loggedUser.getNome());
	}
	
}
