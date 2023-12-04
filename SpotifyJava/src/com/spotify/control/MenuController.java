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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML
    private ListView<String> listaDiretorios;

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
		ListaPlaylists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			        if(newValue.equals("Musicas")) {
			        	loadMusicas(newValue, 0, conn);
			        }
			        else {
			        	loadMusicas(newValue,loggedUser.getId(),conn);
			        }
			    }
			
		});
		
		
		System.out.println(loggedUser.getNome());
		String playlistSelecionada = null;
		/*while(true) {
			String novaSelecao = ListaPlaylists.getSelectionModel().getSelectedItem();
			if(!novaSelecao.equals(playlistSelecionada)) {
				loadMusicas(novaSelecao,0, conn);
			}
			
			
		}*/
		
		
	}
	
	public void loadPlaylists(int userId,Connection conn) {
		//System.out.println("NUMERO UM");
		String sql = "SELECT playlists.nome FROM playlists WHERE proprietario_id = ?";
		ListaPlaylists.getItems().add("Musicas");
		if(loggedUser.getTipo().equals("VIP")) {
			System.out.println("É um vip, procurando playlists");
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
	
	public void loadMusicas(String nomePlaylist, int userId, Connection conn) {
		String sql = "SELECT m.titulo AS musica\r\n"
				+ "FROM musicas AS m\r\n"
				+ "JOIN musicas_e_playlists AS mep ON m.id = mep.id_musica\r\n"
				+ "JOIN playlists AS p ON mep.id_playlist = p.id\r\n"
				+ "WHERE p.nome = ? AND p.proprietario_id = ?";
		
		PreparedStatement stmt;
		listaMusicas.getItems().clear();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,nomePlaylist);
			stmt.setInt(2, userId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				listaMusicas.getItems().add(rs.getString("musica"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	@FXML
    void previousAction(ActionEvent event) {

    }

    @FXML
    void playAction(ActionEvent event) {
    	String[] t = null;
		  // Create and start the application
    	MusicPlayer player = MusicPlayer.getInstance();
    	player.tocar();
    	
    }

    @FXML
    void pauseAction(ActionEvent event) {

    }

    @FXML
    void nextAction(ActionEvent event) {

    }

    @FXML
    void addPlaylist(ActionEvent event) {

    }

    @FXML
    void removePlaylist(ActionEvent event) {

    }

    @FXML
    void importFile(ActionEvent event) {

    }

    @FXML
    void importFolder(ActionEvent event) {

    }
    @FXML
    void removeFolder(ActionEvent event) {

    }
    @FXML
    void addToPlaylist(ActionEvent event) {

    }

    @FXML
    void removeFromPlaylist(ActionEvent event) {

    }
	
}
