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

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    @FXML
    public Label musicaTocando;
    @FXML
    private Slider timeSlider;
    
    // Conexão com o banco de dados
    Connection conn = DataBase.connect("database.db");

    // Usuário logado
    UserHolder holder = UserHolder.getInstance();
    Usuario loggedUser = holder.getUser();

    // Stage da aplicação
    Stage stage = holder.getStage();

    // Instância do reprodutor de música
    MusicPlayer player = MusicPlayer.getInstance();
    
    /**
     * Configura o menu de importação de arquivos e diretórios.
     *
     * @param conn Conexão com o banco de dados.
     */
    private void configMenuButton(Connection conn) {
        MenuItem botaoImportarMusica = new MenuItem("Música");
        MenuItem botaoImportarDiretorio = new MenuItem("Diretório");

        // Limpa o menu de opções existentes
        importBotao.getItems().clear();

        // Adiciona as opções de importação de música e diretório
        importBotao.getItems().add(botaoImportarMusica);
        importBotao.getItems().add(botaoImportarDiretorio);

        // Define os eventos de clique para as opções
        botaoImportarMusica.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Abre o FileChooser para selecionar um arquivo de música
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Escolha um arquivo .mp3 ou .wav");
                File file = fileChooser.showOpenDialog(stage);

                // Verifica se o usuário selecionou um arquivo
                if (file != null) {
                    try {
                        // Copia o arquivo de música para a pasta de armazenamento
                        Arquivos.copyFile(file, conn);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        botaoImportarDiretorio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Abre o DirectoryChooser para selecionar um diretório
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Escolha um diretório");
                File file = directoryChooser.showDialog(stage);

                // Verifica se o usuário selecionou um diretório
                if (file != null) {
                    // Adiciona o diretório ao banco de dados
                    MusicaDAO.novoDiretorio(file, conn);
                }
            }
        }); 
        
        };
	
    /**
     * Inicializa o controlador da tela de menu.
     * @throws SQLException Exceção relacionada ao banco de dados.
     */
    public void initialize() throws SQLException {

        System.out.println("Inicializou");

        // Armazena o timeSlider e o label da música tocando na instância do UserHolder
        holder.setTimeSlider(timeSlider);
        holder.setMusicaTocando(musicaTocando);

        // Preenche os labels de nome, usuário e tipo com as informações do usuário logado
        nameHolder.setText(loggedUser.getNome());
        usernameHolder.setText(loggedUser.getUsuario());
        typeHolder.setText(loggedUser.getTipo());

        // Configura o menu de importação de arquivos e diretórios
        configMenuButton(conn);

        // Carrega as playlists do usuário logado
        loadPlaylists(loggedUser.getId(), conn);

        // Adiciona um listener ao item selecionado da lista de playlists
        ListaPlaylists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadMusicas(conn);
            }
        });

        // Define o volume inicial do player
        volumeSlider.setValue(50);
        holder.setVolume(volumeSlider.getValue());

        // Adiciona um listener ao slider de volume
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                if (MusicPlayer.getCurrentPlayer() != null) {
                    MusicPlayer.getCurrentPlayer().setVolume(volumeSlider.getValue() / 100);
                    holder.setVolume(volumeSlider.getValue());
                }
            }
        });

        // Adiciona um listener ao slider de tempo
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                if (MusicPlayer.getCurrentPlayer() != null) {
                    double tempoTotal = MusicPlayer.getCurrentPlayer().getStopTime().toSeconds();
                    double jump = 100000 / tempoTotal;
                    if (Math.round((timeSlider.getValue() / jump)) != Math.round(MusicPlayer.getCurrentPlayer().getCurrentTime().toSeconds())) {
                        System.out.println("foi movido");
                        Duration seek = new Duration((timeSlider.getValue() / jump) * 1000);
                        MusicPlayer.getCurrentPlayer().seek(seek);
                        MusicPlayer.setPauseTime(seek);
                    }
                }
            }
        });
    }

    /**
     * Realiza o logout do usuário e retorna à tela de login.
     * @param event O evento de clique do botão de logout.
     * @throws Exception Exceção genérica.
     */
    @FXML
    public void logOut(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Limpa a fila e para o player
        MusicPlayer.limparFila();
        player.proximaMusica();
        MusicPlayer.getCurrentPlayer().stop();
        MusicPlayer.setIsPlaying(0);

        // Reinicia o player com um arquivo vazio
        player.setCurrentPlayer(new MediaPlayer(new Media(new File("./storage/Arquivo.mp3").toURI().toString())));

        // Abre a tela de login
        LoginView login = new LoginView();
        conn.close();
        login.start(stage);
    }
	
    /**
     * Carrega as playlists do usuário logado.
     * @param userId ID do usuário logado.
     * @param conn Conexão com o banco de dados.
     */
    public void loadPlaylists(int userId, Connection conn) {
        String sql = "SELECT playlists.nome FROM playlists WHERE proprietario_id = ?";

        // Limpa a lista de playlists
        ListaPlaylists.getItems().clear();

        // Adiciona a playlist "Musicas" à lista
        ListaPlaylists.getItems().add("Musicas");

        // Verifica se o usuário logado é VIP
        if (loggedUser.getTipo().equals("VIP")) {
            try {
                // Prepara a consulta SQL
                PreparedStatement stmt = conn.prepareStatement(sql);

                // Define o parâmetro da consulta
                stmt.setInt(1, userId);

                // Executa a consulta SQL
                ResultSet rs = stmt.executeQuery();

                // Adiciona as playlists do usuário à lista
                while (rs.next()) {
                    ListaPlaylists.getItems().add(rs.getString("nome"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
    /**
     * Carrega as músicas da playlist selecionada.
     * @param conn Conexão com o banco de dados.
     */
    public void loadMusicas(Connection conn) {
        String sql = "SELECT m.titulo AS musica\n"
                + "FROM musicas AS m\n"
                + "JOIN musicas_e_playlists AS mep ON m.id = mep.id_musica\n"
                + "JOIN playlists AS p ON mep.id_playlist = p.id\n"
                + "WHERE p.nome = ? AND p.proprietario_id = ?";

        // Recupera a playlist selecionada e o ID do usuário logado
        String nomePlaylist = ListaPlaylists.getSelectionModel().getSelectedItem();
        int id = loggedUser.getId();

        if (nomePlaylist == null) {
            return;
        }

        // Se a playlist selecionada for "Musicas", utiliza o ID 0
        if (nomePlaylist.equals("Musicas")) {
            id = 0;
        }

        // Limpa a lista de músicas
        listaMusicas.getItems().clear();

        try {
            // Prepara a consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Define os parâmetros da consulta
            stmt.setString(1, nomePlaylist);
            stmt.setInt(2, id);

            // Executa a consulta SQL
            ResultSet rs = stmt.executeQuery();

            // Adiciona as músicas da playlist à lista
            while (rs.next()) {
                System.out.println(rs.getString("musica"));
                listaMusicas.getItems().add(rs.getString("musica"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia a reprodução da música.
     * @param event Evento de clique do botão "Play".
     */
    @FXML
    public void playAction(ActionEvent event) {
        player.tocar();
    }
    
    /**
     * Volta para a música anterior.
     * @param event Evento de clique do botão "Anterior".
     */
    @FXML
    public void previousAction(ActionEvent event) {
        player.musicaAnterior();
    }
    
    /**
     * Pausa a reprodução da música.
     * @param event Evento de clique do botão "Pause".
     */
    @FXML
    public void pauseAction(ActionEvent event) {
        player.pausar();
    }

    /**
     * Avança para a próxima música.
     * @param event Evento de clique do botão "Próximo".
     */
    @FXML
    public void nextAction(ActionEvent event) {
        player.proximaMusica();
    }

    /**
     * Cria uma nova playlist.
     * @param event Evento de clique do botão "Criar Playlist".
     */
    @FXML
    public void criarPlaylist(ActionEvent event) {
        String nomeNovaPlaylist = novaPlaylistField.getText();

        // Verifica se o usuário é VIP
        if (!loggedUser.getTipo().equals("VIP")) {
            errorHolder.setTextFill(Color.color(1, 0, 0));
            errorHolder.setText("Apenas VIPs podem criar playlists.");
            return;
        }

        // Verifica se o nome da playlist é inválido
        if (nomeNovaPlaylist.equals("Musicas")) {
            errorHolder.setTextFill(Color.color(1, 0, 0));
            errorHolder.setText("Não é possível criar uma playlist com este nome");
            return;
        }

        // Verifica se a playlist já existe
        int busca = PlaylistDAO.getPlaylistId(nomeNovaPlaylist, loggedUser.getId(), conn);
        if (busca == -1) {
            // Cria a nova playlist
            PlaylistDAO.novaPlaylist(nomeNovaPlaylist, loggedUser.getId(), conn);

            // Exibe mensagem de sucesso
            errorHolder.setTextFill(Color.color(0, 1, 0));
            errorHolder.setText("Nova playlist criada com sucesso!");
            return;
        }

        // Exibe mensagem de erro
        errorHolder.setTextFill(Color.color(1, 0, 0));
        errorHolder.setText("Não é possível criar uma playlist");
    }

    /**
     * Remove uma playlist existente.
     * @param event Evento de clique do botão "Remover Playlist".
     */
    @FXML
    public void removerPlaylist(ActionEvent event) {
        String nomeRemoverPlaylist = removerPlaylistField.getText();

        // Verifica se o nome da playlist é inválido
        if (nomeRemoverPlaylist.equals("Musicas")) {
            errorHolder.setTextFill(Color.color(1, 0, 0));
            errorHolder.setText("Não é possível remover esta playlist");
            return;
        }

        // Verifica se a playlist existe
        int busca = PlaylistDAO.getPlaylistId(nomeRemoverPlaylist, loggedUser.getId(), conn);
        if (busca >= 0) {
            // Remove a playlist
            PlaylistDAO.removerPlaylist(nomeRemoverPlaylist, loggedUser.getId(), conn);

            // Exibe mensagem de sucesso
            errorHolder.setTextFill(Color.color(0, 1, 0));
            errorHolder.setText("Playlist removida com sucesso!");
            return;
        }

        // Exibe mensagem de erro
        errorHolder.setTextFill(Color.color(1, 0, 0));
        errorHolder.setText("Playlist não encontrada");
    }

    /**
     * Abre um FileChooser para escolher um arquivo de música.
     * @param event Evento de clique do botão "Importar Música".
     */
    /*@FXML
    public void importarArquivo(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            System.out.println(file.getName());
        }
    }*/

    /**
     * Adiciona a música selecionada à playlist especificada.
     * @param event Evento de clique do botão "Adicionar à Playlist".
     */
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

    /**
     * Remove a música selecionada da playlist selecionada.
     * @param event Evento de clique do botão "Remover da Playlist".
     */
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

    /**
     * Adiciona a música selecionada à fila de reprodução.
     * @param event Evento de clique do botão "Adicionar à Fila".
     */
    @FXML
    void adicionarMusicaFila(ActionEvent event) {
    	String nome = listaMusicas.getSelectionModel().getSelectedItem();
    	Musica musica = MusicaDAO.getMusica(nome, conn);
    	MusicPlayer.getFila().add(musica);
    	
    }
    
    /**
     * Adiciona as músicas de uma playlist à fila de reprodução.
     * @param event Evento de clique do botão "Adicionar Playlist à Fila".
     */
    @FXML
    void adicionarPlaylistFila(ActionEvent event) {
    	String playlist = ListaPlaylists.getSelectionModel().getSelectedItem();
    	int id = PlaylistDAO.getPlaylistId(playlist, loggedUser.getId(), conn);
    	if(playlist.equals("Musicas")) {
    			id = PlaylistDAO.getPlaylistId("Musicas", 0, conn);
    	}
    	try {
    		
    		Queue<Musica> musicasPlaylist = PlaylistDAO.getMusicasPlaylist(id, conn);
    		MusicPlayer.getFila().addAll(musicasPlaylist);
    	} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    /**
     * Limpa a fila de reprodução.
     * @param event Evento de clique do botão "Limpar Fila".
     */
    @FXML
    void limparFila(ActionEvent event) {
    	MusicPlayer.limparFila();
    }
    
    /**
     * Atualiza a lista de playlists e a lista de músicas.
     * @param event Evento de clique do botão "Refresh".
     */
    @FXML
    void refresh(ActionEvent event) {
        loadMusicas(conn);
        loadPlaylists(loggedUser.getId(),conn);     
    }

}
