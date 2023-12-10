package com.spotify.control;

import java.io.File;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Queue;

import com.spotify.dao.MusicaDAO;
import com.spotify.dao.PlaylistDAO;
import com.spotify.data.Arquivos;
import com.spotify.data.DataBase;
import com.spotify.model.Musica;
import com.spotify.model.Usuario;
import com.spotify.view.LoginView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class MenuController {
    @FXML
    private AnchorPane anchorPane;
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
    private Label errorHolder;
    @FXML
    private ListView<String> listaDiretorios;
    @FXML
    private MenuButton importBotao;
    @FXML
    private Slider volumeSlider;
    @FXML
    private TextField addToPlaylistField;
    @FXML
    private TextField novaPlaylistField;
    @FXML
    private TextField removerPlaylistField;
    
    Connection conn = DataBase.connect("database.db");
    
    UserHolder holder=UserHolder.getInstance();
    Usuario loggedUser=holder.getUser();

    Stage stage = holder.getStage();
    
    MusicPlayer player = MusicPlayer.getInstance();
    
    private void configMenuButton(Connection conn) {
    	MenuItem botaoImportarMusica = new MenuItem("Música");
    	MenuItem botaoImportarDiretorio = new MenuItem("Diretório");
    	importBotao.getItems().clear();
    	importBotao.getItems().add(botaoImportarMusica);
    	importBotao.getItems().add(botaoImportarDiretorio);
    	
    	EventHandler<ActionEvent> importarMusica = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
        		
            	FileChooser fileChooser = new FileChooser();
            	fileChooser.setTitle("Escolha um arquivo .mp3 ou .wav");
            	File file = fileChooser.showOpenDialog(stage);
            	try {
					Arquivos.copyFile(file, conn);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
            	System.out.println(file.getName());
                
            }
        };
        EventHandler<ActionEvent> importarDiretorio = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
            	DirectoryChooser directoryChooser = new DirectoryChooser();
            	directoryChooser.setTitle("Escolha um diretório");
            	File file = directoryChooser.showDialog(stage);
            	System.out.println(file.getName());
                System.out.println("fon2");
                MusicaDAO.novoDiretorio(file, conn);
            }
        };
        botaoImportarMusica.setOnAction(importarMusica);
        botaoImportarDiretorio.setOnAction(importarDiretorio);
    }
    
    
    
	
	@FXML
	public void logOut(ActionEvent event) throws Exception {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		MusicPlayer.limparFila();
		player.pausar();
		LoginView login = new LoginView();
		conn.close();
		login.start(stage);
	}
	
	public void initialize() throws SQLException {
		
		System.out.println("Inicializou");
		
		nameHolder.setText(loggedUser.getNome());
		usernameHolder.setText(loggedUser.getUsuario());
		typeHolder.setText(loggedUser.getTipo());
		
		
		configMenuButton(conn);
		loadPlaylists(loggedUser.getId(),conn);
		ListaPlaylists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			 @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {			        
				 loadMusicas(conn);
			    }
		});
		
		System.out.println(loggedUser.getNome());
		
	}
	
	public void loadPlaylists(int userId,Connection conn) {
		//System.out.println("NUMERO UM");
		String sql = "SELECT playlists.nome FROM playlists WHERE proprietario_id = ?";
		listaMusicas.getItems().clear();
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
	
	public void loadMusicas(Connection conn) {
		String sql = "SELECT m.titulo AS musica\r\n"
				+ "FROM musicas AS m\r\n"
				+ "JOIN musicas_e_playlists AS mep ON m.id = mep.id_musica\r\n"
				+ "JOIN playlists AS p ON mep.id_playlist = p.id\r\n"
				+ "WHERE p.nome = ? AND p.proprietario_id = ?";
		
		
		String nomePlaylist = ListaPlaylists.getSelectionModel().getSelectedItem();
		int id = loggedUser.getId();
		if(nomePlaylist.equals("Musicas"))
			id=0;
		
		PreparedStatement stmt;
		listaMusicas.getItems().clear();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,ListaPlaylists.getSelectionModel().getSelectedItem());
			stmt.setInt(2, id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {	
				System.out.println(rs.getString("musica"));
				listaMusicas.getItems().add(rs.getString("musica"));	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

    @FXML
    void playAction(ActionEvent event) {
    	
    	player.tocar();
    	
    }
	@FXML
    void previousAction(ActionEvent event) {
		player.musicaAnterior();
    }
    @FXML
    void pauseAction(ActionEvent event) {
    	
    	player.pausar();
    }

    @FXML
    void nextAction(ActionEvent event) {
    	player.proximaMusica();
    }

    @FXML
    void criarPlaylist(ActionEvent event) {
    	
    }

    @FXML
    void removerPlaylist(ActionEvent event) {

    }

    @FXML
    void importarArquivo(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File file = fileChooser.showOpenDialog(stage);
    	System.out.println(file.getName());
    }

    @FXML
    void adicionarParaPlaylist(ActionEvent event) {
    	String nomePlaylist=addToPlaylistField.getText();
    	if(nomePlaylist.equals("Musicas")) {
    		errorHolder.setTextFill(Color.color(1, 0, 0));
    		errorHolder.setText("Não é possível adicionar músicas à esta playlist");
    		return;
    	}
    	int idPlaylist = PlaylistDAO.getPlaylistId(nomePlaylist, loggedUser.getId(), conn);
    	if(idPlaylist == -1) {
    		errorHolder.setTextFill(Color.color(1, 0, 0));
    		errorHolder.setText("Playlist não encontrada");
    	}
    	else {
    		PlaylistDAO.adicionarMusica(listaMusicas.getSelectionModel().getSelectedItem(), idPlaylist, loggedUser.getId(), conn);
    		errorHolder.setTextFill(Color.color(0, 1, 0));
    		errorHolder.setText("Música adicionada com sucesso à playlist!");
    	}
    }

    @FXML
    void removerDaPlaylist(ActionEvent event) {
    	String nomePlaylist=ListaPlaylists.getSelectionModel().getSelectedItem();
    	if(nomePlaylist.equals("Musicas")) {
    		errorHolder.setTextFill(Color.color(1, 0, 0));
    		errorHolder.setText("Não é possível remover músicas desta playlist");
    		return;
    	}
    	int idPlaylist = PlaylistDAO.getPlaylistId(nomePlaylist, loggedUser.getId(), conn);
    	if(idPlaylist == -1) {
    		errorHolder.setTextFill(Color.color(1, 0, 0));
    		errorHolder.setText("Playlist não encontrada");
    	}
    	else {
    		PlaylistDAO.removerMusica(listaMusicas.getSelectionModel().getSelectedItem(), idPlaylist, conn);
    		errorHolder.setTextFill(Color.color(0, 1, 0));
    		errorHolder.setText("Música removida com sucesso da playlist!");
    	}
    }

    
    @FXML
    void adicionarMusicaFila(ActionEvent event) {
    	String nome = listaMusicas.getSelectionModel().getSelectedItem();
    	Musica musica = MusicaDAO.getMusica(nome, conn);
    	MusicPlayer.getFila().add(musica);
    	
    }

    @FXML
    void adicionarPlaylistFila(ActionEvent event) {
    	String playlist = ListaPlaylists.getSelectionModel().getSelectedItem();
    	int id = PlaylistDAO.getPlaylistId(playlist, loggedUser.getId(), conn);
    	try {
    		Queue<Musica> musicasPlaylist = PlaylistDAO.getMusicasPlaylist(id, conn);
    		MusicPlayer.getFila().addAll(musicasPlaylist);
    	} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void limparFila(ActionEvent event) {
    	MusicPlayer.limparFila();
    }
	
    @FXML
    void refresh(ActionEvent event) {
    	String playlist = ListaPlaylists.getSelectionModel().getSelectedItem();
    	if(playlist.equals("Musicas")) {
        	loadMusicas(conn);
        }
        else {
        	loadMusicas(conn);
        }
    }

}
