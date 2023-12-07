package com.spotify.control;
import com.spotify.model.Usuario;

import javafx.stage.Stage;

public final class UserHolder {
	  
	  private Usuario user;
	  private Stage stage;
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
	  
	  
	}