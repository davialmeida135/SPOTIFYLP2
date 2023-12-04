package com.spotify.control;

import java.io.File;
import java.util.Queue;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//Gerenciar a fila e tocar as musicas
//Fazer funcao de adicionar musica a fila
//Fazer funcao de adicionar playlist a fila
public class MusicPlayer extends Application {
	private Queue<String> fila;
	
	private final static MusicPlayer INSTANCE = new MusicPlayer();
	
	public void tocar() {
		String q = "C:\\Users\\Davi\\Desktop\\pasta secreta\\soundboard\\K.mp3";
		Media hit = new Media(new File(q).toURI().toString());
		MediaPlayer player = new MediaPlayer(hit);
		player.play();
	}
	
	    @Override
	public void start(Stage stage) throws Exception {
	        // JavaFX should be initialized  
	    	tocar();
	    	
	}
	public static void iniciar(String[] args) {
		launch(args);
	}

	public static MusicPlayer getInstance() {
		return INSTANCE;
	}
	
	
}
