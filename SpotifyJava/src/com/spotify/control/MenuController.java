package com.spotify.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.spotify.data.DataBase;
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
	public void logOut(ActionEvent event) throws Exception {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		LoginView login = new LoginView();
		login.start(stage);
	}
	
	public void initialize() throws SQLException {
		
		//System.out.println("Inicializou");
		
		nameHolder.setText(loggedUser.getNome());
		usernameHolder.setText(loggedUser.getUsuario());
		typeHolder.setText(loggedUser.getTipo());
		
		Connection conn = DataBase.connect("database.db");
		loadPlaylists(loggedUser.getId(),conn);
		conn.close();
		
		System.out.println(loggedUser.getNome());
		
	}
	
	public void loadPlaylists(int userId,Connection conn) {
		//System.out.println("NUMERO UM");
		String sql = "SELECT playlists.nome FROM playlists WHERE proprietario_id = ?";
		ListaPlaylists.getItems().add("Musicas");
		if(loggedUser.getTipo().equals("VIP")) {
			System.out.println("é um vip, procurando playlists");
			try {
			PreparedStatement stmt = conn.prepareStatement(sql); 
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				ListaPlaylists.getItems().add(rs.getString("nome"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}
	
}
