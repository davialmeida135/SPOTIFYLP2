package com.spotify.control;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import com.spotify.model.Musica;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//Gerenciar a fila e tocar as musicas
//Fazer funcao de adicionar musica a fila
//Fazer funcao de adicionar playlist a fila
public class MusicPlayer extends Application {
	private static Queue<Musica> fila = new LinkedList<Musica>();
	private static int isPlaying=0;
	
	private static MediaPlayer currentPlayer;
	
	public MusicPlayer() {
		String q = "./storage/musicas/DreamTeamGanharDragoesNegros.mp3";
		String w = "./storage/musicas/hapi_-_vou_quebrar_tudo.mp3";
		Musica qm = new Musica("muriz",q);
		Musica wm = new Musica("hapi",w);
		fila.add(qm);
		fila.add(wm);
	}
	
	private final static MusicPlayer INSTANCE = new MusicPlayer();
	
	public void tocar() {
		
		playNextMusicFile(fila);

	}
	
	public void pausar() {
		currentPlayer.pause();
		isPlaying=0;
	}
	
	public void playNextMusicFile(Queue<Musica> fila) {
		  if (fila.isEmpty()||isPlaying==1) {
		    return ;
		  }
		 
		
		  isPlaying =1;
		  Media media = new Media(new File(fila.element().getPath()).toURI().toString());
		  MediaPlayer player = new MediaPlayer(media);
		  currentPlayer = player;
		  player.setOnEndOfMedia(() -> {
		    player.dispose();
		    isPlaying =0;
		    fila.remove();
		    playNextMusicFile(fila);
		    
		  });
		  player.play();
		}
	
	@Override
	public void start(Stage stage) throws Exception {
	        // JavaFX should be initialized  
   	
	}

	public static MusicPlayer getInstance() {
		return INSTANCE;
	}

	public Queue<Musica> getFila() {
		return fila;
	}

	public void setFila(Queue<Musica> fila) {
		MusicPlayer.fila = fila;
	}
	
	public static void limparFila() {
		fila.clear();
		
	}
	
	
}
