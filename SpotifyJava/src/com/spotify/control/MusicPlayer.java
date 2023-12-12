package com.spotify.control;
import java.io.File;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.spotify.model.Musica;
import javafx.application.Application;
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
	
	private static Duration pauseTime = Duration.ZERO;
	
	private static UserHolder holder = UserHolder.getInstance();
	
	private static MediaPlayer currentPlayer =new MediaPlayer(new Media(new File("./storage/Arquivo.mp3").toURI().toString()));
	
	/**
	 * Importa instância do player de música
	 */
	private final static MusicPlayer INSTANCE = new MusicPlayer();
	public static int getIsPlaying() {
		return isPlaying;
	}

	public static void setIsPlaying(int isPlaying) {
		MusicPlayer.isPlaying = isPlaying;
	}

	
	public static Duration getPauseTime() {
		return pauseTime;
	}

	public static void setPauseTime(Duration pauseTime) {
		MusicPlayer.pauseTime = pauseTime;
	}


	/**
	 * Retorna a instância única da classe.
	 *
	 * @return Instância do MusicPlayer.
	 */
	public static MusicPlayer getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Retorna a fila de músicas.
	 *
	 * @return Fila de músicas.
	 */
	public static Deque<Musica> getFila() {
		return fila;
	}

	/**
	 * Define a fila de músicas.
	 *
	 * @param fila Nova fila de músicas.
	 */
	public static void  setFila(Deque<Musica> fila) {
		MusicPlayer.fila = fila;
	}
	
	/**
	 * Limpa a fila de músicas, mantendo apenas a primeira música.
	 */
	public static void limparFila() {
		Musica first = fila.element();
		fila.clear();
		fila.add(first);
		
	}
	
	/**
	 * Listener para atualizar a barra de progresso de acordo com o tempo de reprodução da música.
	 */
	public static ChangeListener<Duration> changeListener = new ChangeListener<Duration>() {
		@Override
		public void changed(ObservableValue<? extends Duration> arg0, Duration arg1, Duration arg2) {
			double tempoTotal = currentPlayer.getStopTime().toSeconds();
			double jump = 100000/tempoTotal;
			holder.getTimeSlider().setValue(currentPlayer.getCurrentTime().toSeconds()*jump);
			
		}
	};
	
	/**
	 * Define o player de mídia atual e configura o listener para atualização da barra de progresso.
	 *
	 * @param player Novo player de mídia.
	 */
	public void setCurrentPlayer(MediaPlayer player) {
	    if (currentPlayer != null) {
	    	currentPlayer.currentTimeProperty().removeListener(changeListener);
	    }
	    currentPlayer = player;
	    if (currentPlayer != null) {
	    	currentPlayer.currentTimeProperty().addListener(changeListener);
	    }
	}
	
	/**
	 * Retorna o player de mídia atual.
	 *
	 * @return Player de mídia atual.
	 */
	public static MediaPlayer getCurrentPlayer() {
			return currentPlayer;
		}
	
	/**
	 * Inicia a reprodução da música seguinte na fila.
	 *
	 * @return void
	 */
	public void tocar() {
		
		playNextMusicFile(fila);

	}
	
	/**
	 * Pausa a reprodução da música atual.
	 *
	 * @return void
	 */
	public void pausar() {
		 if (currentPlayer != null && isPlaying ==1) {
		    pauseTime = currentPlayer.getCurrentTime();
		    currentPlayer.pause();
		    isPlaying=2;
		    System.out.println("Musica pausada em "+pauseTime.toMillis());
		 } 
	}
	
	/**
	 * Avança para a próxima música na fila.
	 *
	 * @return void
	 */
	public void proximaMusica() {
		if(!fila.isEmpty()) {
			jaTocadas.add(fila.remove());
		}
		pausar();
    	isPlaying=0;
    	tocar();
    	
	}
	
	/**
	 * Volta para a música anterior.
	 *
	 * @return void
	 */
	public void musicaAnterior() {
		if(!jaTocadas.isEmpty()) {
			fila.addFirst(jaTocadas.pop());
		}
		pausar();
		isPlaying=0;
		tocar();
		return;
	}
	
	/**
	 * Método interno que toca a próxima música na fila.
	 *
	 * @param fila Fila de músicas.
	 * @return void
	 */
	private void playNextMusicFile(Queue<Musica> fila) {
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

}
