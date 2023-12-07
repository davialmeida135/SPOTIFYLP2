package com.spotify.model;

import java.util.ArrayList;

public class Musica {
	private int id;
	private String titulo;
	private String path;
	private int userId;
	
	
	public Musica(String titulo, String path) {
		super();
	
		this.titulo = titulo;
		this.path = path;
	}
	
	public Musica() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
