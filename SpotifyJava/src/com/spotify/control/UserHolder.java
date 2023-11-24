package com.spotify.control;
import com.spotify.model.Usuario;

public final class UserHolder {
	  
	  private Usuario user;
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
	}