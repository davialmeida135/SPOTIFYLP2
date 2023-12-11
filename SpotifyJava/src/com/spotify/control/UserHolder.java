package com.spotify.control;
import com.spotify.model.Usuario;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public final class UserHolder {
	  
	  private Usuario user;
	  private Stage stage;
	  private Slider timeSlider;
	  private Label musicaTocando;
	  private final static UserHolder INSTANCE = new UserHolder();
	  
	  private UserHolder() {}
	  
	  public static UserHolder getInstance() {
	    return INSTANCE;
	  }
	  
	  public void setUser(Usuario u) {
	    this.user = u;
	  }
	  
	  public Usuario getUser() {
		  return this.user;
	  }
	
	 public Stage getStage() {
		 return stage;
	 }
	
	 public void setStage(Stage stage) {
	 	this.stage = stage;
	 }

	public Slider getTimeSlider() {
		return timeSlider;
	}

	public void setTimeSlider(Slider timeSlider) {
		this.timeSlider = timeSlider;
	}

	public Label getMusicaTocando() {
		return musicaTocando;
	}

	public void setMusicaTocando(Label musicaTocando) {
		this.musicaTocando = musicaTocando;
	}
	  
	  
	}