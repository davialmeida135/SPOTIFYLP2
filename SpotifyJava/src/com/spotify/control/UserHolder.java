package com.spotify.control;
import com.spotify.model.Usuario;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * Classe que armazena objetos de modo a permitir
 * a comunicação entre os controladores.
 */
public final class UserHolder {
	  
	  private Usuario user;
	  private Stage stage;
	  private Slider timeSlider;
	  private Label musicaTocando;
	  private double volume;
	  private final static UserHolder INSTANCE = new UserHolder();
	  
	  /**
	   * Construtor privado para garantir que a classe UserHolder seja um singleton.
	   */
	  private UserHolder() {}
	  
	  /**
	   * Retorna a única instância da classe UserHolder.
	   *
	   * @return A instância do UserHolder.
	   */
	  public static UserHolder getInstance() {
	      return INSTANCE;
	  }
	  
	  /**
	   * Define o usuário atualmente conectado.
	   *
	   * @param u O usuário a ser definido.
	   */
	  public void setUser(Usuario u) {
	      this.user = u;
	  }
	  
	  /**
	   * Retorna o usuário atualmente conectado.
	   *
	   * @return O usuário atualmente conectado.
	   */
	  public Usuario getUser() {
	      return this.user;
	  }
	
	  /**
	   * Retorna o palco principal da aplicação.
	   *
	   * @return O palco principal da aplicação.
	   */
	  public Stage getStage() {
	      return stage;
	  }
	
	  /**
	   * Define o palco principal da aplicação.
	   *
	   * @param stage O palco a ser definido.
	   */
	  public void setStage(Stage stage) {
	      this.stage = stage;
	  }

	  /**
	   * Retorna o controle deslizante usado para controlar a posição de reprodução.
	   *
	   * @return O controle deslizante usado para controlar a posição de reprodução.
	   */
	  public Slider getTimeSlider() {
	      return timeSlider;
	  }

	  /**
	   * Define o controle deslizante usado para controlar a posição de reprodução.
	   *
	   * @param timeSlider O controle deslizante a ser definido.
	   */
	  public void setTimeSlider(Slider timeSlider) {
	      this.timeSlider = timeSlider;
	  }

	  /**
	   * Retorna o rótulo que exibe o título da música atualmente tocando.
	   *
	   * @return O rótulo que exibe o título da música atualmente tocando.
	   */
	  public Label getMusicaTocando() {
	      return musicaTocando;
	  }

	  /**
	   * Define o rótulo que exibe o título da música atualmente tocando.
	   *
	   * @param musicaTocando O rótulo a ser definido.
	   */
	  public void setMusicaTocando(Label musicaTocando) {
	      this.musicaTocando = musicaTocando;
	  }

	  /**
	   * Retorna o volume atual do reprodutor de música.
	   *
	   * @return O volume atual do reprodutor de música.
	   */
	  public double getVolume() {
	      return volume;
	  }

	  /**
	   * Define o volume atual do reprodutor de música.
	   *
	   * @param volume O volume a ser definido.
	   */
	  public void setVolume(double volume) {
	      this.volume = volume;
	  }
	  
	  
	}