package com.spotify.control;
import java.io.File;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.spotify.model.Musica;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

//Gerenciar a fila e tocar as musicas
//Fazer funcao de adicionar musica a fila
//Fazer funcao de adicionar playlist a fila
public class MusicPlayer extends Application {
	
	private static Deque<Musica> fila = new LinkedList<Musica>();
	private static Stack<Musica> jaTocadas = new Stack<Musica>();
	
	private static int isPlaying=0;
	
	public static int getIsPlaying() {
		return isPlaying;
	}

	public static void setIsPlaying(int isPlaying) {
		MusicPlayer.isPlaying = isPlaying;
	}

	private static Duration pauseTime = Duration.ZERO;
	
	private static UserHolder holder = UserHolder.getInstance();
	
	public static Duration getPauseTime() {
		return pauseTime;
	}

	public static void setPauseTime(Duration pauseTime) {
		MusicPlayer.pauseTime = pauseTime;
	}

	private static MediaPlayer currentPlayer =new MediaPlayer(new Media(new File("./storage/Arquivo.mp3").toURI().toString()));
	
	
	private final static MusicPlayer INSTANCE = new MusicPlayer();
	
	public void tocar() {
		
		playNextMusicFile(fila);

	}
	
	public void pausar() {
		 if (currentPlayer != null && isPlaying ==1) {
		    pauseTime = currentPlayer.getCurrentTime();
		    currentPlayer.pause();
		    isPlaying=2;
		    System.out.println("Musica pausada em "+pauseTime.toMillis());
		 } 
	}
	
	public void proximaMusica() {
		if(!fila.isEmpty()) {
			jaTocadas.add(fila.remove());
		}
		pausar();
    	isPlaying=0;
    	tocar();
    	
	}
	
	public void musicaAnterior() {
		if(!jaTocadas.isEmpty()) {
			fila.addFirst(jaTocadas.pop());
		}
		pausar();
		isPlaying=0;
		tocar();
		return;
	}
	
	public void playNextMusicFile(Queue<Musica> fila) {
		  if (fila.isEmpty()) {
			  return ;
		  }
		  if(isPlaying==1) {
			  return ;
		  }
		 
		  Musica nextMusic = fila.element();
		  Media media = new Media(new File(nextMusic.getPath()).toURI().toString());
		  if (currentPlayer == null || isPlaying == 0) {
			  setCurrentPlayer(new MediaPlayer(media));
			  currentPlayer.setVolume(holder.getVolume());
			    //System.out.println("entrou aqui");
			    currentPlayer.setOnEndOfMedia(() -> {	    	
			    	//System.out.println("cabou");
				    currentPlayer.dispose();
				    isPlaying = 0;
				    pauseTime = Duration.ZERO;
				    jaTocadas.add(nextMusic);
				    fila.remove();
				    
				    playNextMusicFile(fila);
			    });
		  }
		  holder.getMusicaTocando().setText(nextMusic.getTitulo());

		  isPlaying = 1;
		
		  currentPlayer.seek(pauseTime);
		  currentPlayer.play();
		}
	
	@Override
	public void start(Stage stage) throws Exception {
	        // JavaFX should be initialized  
   	
	}

	public static MusicPlayer getInstance() {
		return INSTANCE;
	}

	public static Deque<Musica> getFila() {
		return fila;
	}

	public static void  setFila(Deque<Musica> fila) {
		MusicPlayer.fila = fila;
	}
	
	public static void limparFila() {
		Musica first = fila.element();
		fila.clear();
		fila.add(first);
		
	}
	public static ChangeListener<Duration> changeListener = new ChangeListener<Duration>() {
		@Override
		public void changed(ObservableValue<? extends Duration> arg0, Duration arg1, Duration arg2) {
			double tempoTotal = currentPlayer.getStopTime().toSeconds();
			double jump = 100000/tempoTotal;
			holder.getTimeSlider().setValue(currentPlayer.getCurrentTime().toSeconds()*jump);
			
		}
	};
	public void setCurrentPlayer(MediaPlayer player) {
	    if (currentPlayer != null) {
	    	currentPlayer.currentTimeProperty().removeListener(changeListener);
	    }
	    currentPlayer = player;
	    if (currentPlayer != null) {
	    	currentPlayer.currentTimeProperty().addListener(changeListener);
	    }
	}
	public static MediaPlayer getCurrentPlayer() {
			return currentPlayer;
		}
}
