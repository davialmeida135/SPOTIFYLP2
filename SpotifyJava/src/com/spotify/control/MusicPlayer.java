package com.spotify.control;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import com.spotify.model.Musica;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

//Gerenciar a fila e tocar as musicas
//Fazer funcao de adicionar musica a fila
//Fazer funcao de adicionar playlist a fila
public class MusicPlayer extends Application {
	private static Queue<Musica> fila = new LinkedList<Musica>();
	private static int isPlaying=0;
	private static Duration pauseTime = Duration.ZERO;
	
	
	public MediaPlayer currentPlayer =null;
	
	public MusicPlayer() {
		/*String q = "./storage/musicas/DreamTeamGanharDragoesNegros.mp3";
		String w = "./storage/musicas/hapi_-_vou_quebrar_tudo.mp3";
		Musica qm = new Musica("muriz",q);
		Musica wm = new Musica("hapi",w);
		fila.add(qm);
		fila.add(wm);*/
		
	}
	
	private final static MusicPlayer INSTANCE = new MusicPlayer();
	
	public void tocar() {
		
		playNextMusicFile(fila);

	}
	
	public void pausar() {
		 if (currentPlayer != null && isPlaying ==1) {
			    pauseTime = currentPlayer.getCurrentTime();
			    currentPlayer.pause();
			    System.out.println("Musica pausada em "+pauseTime.toMillis());
		 } 
	}
	
	public void proximaMusica() {
		
	}
	
	public void musicaAnterior() {
		
	}
	
	public void playNextMusicFile(Queue<Musica> fila) {
		  if (fila.isEmpty()) {
			  return ;
		  }
		 
		  Musica nextMusic = fila.element();
		  Media media = new Media(new File(nextMusic.getPath()).toURI().toString());

		  
		  
		  if (currentPlayer == null || isPlaying == 0) {
			    currentPlayer = new MediaPlayer(media);
			    System.out.println("entrou aqui");
			    currentPlayer.setOnEndOfMedia(() -> {
			    	System.out.println("cabou");
				    currentPlayer.dispose();
				    isPlaying = 0;
				    pauseTime = Duration.ZERO;
				    fila.remove();
				    playNextMusicFile(fila);
			    });
		  }

		  isPlaying = 1;
		  System.out.println("Resumindo a musica em "+pauseTime.toMillis());
		  System.out.println(currentPlayer.getMedia());
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

	public static Queue<Musica> getFila() {
		return fila;
	}

	public static void  setFila(Queue<Musica> fila) {
		MusicPlayer.fila = fila;
	}
	
	public static void limparFila() {
		fila.clear();
		
	}
	
	
}
