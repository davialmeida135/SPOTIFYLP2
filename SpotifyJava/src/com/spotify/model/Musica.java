package com.spotify.model;

import java.util.ArrayList;

public class Musica {
	private int id;
	private String nome;
	private String path;
	private int userId;
	
	
	public Musica(String nome, String path) {
		super();
	
		this.nome = nome;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
