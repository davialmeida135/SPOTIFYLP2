package com.spotify.model;

import java.util.ArrayList;

public class Playlist {
	private int id;
	private String nome;
	private String descricao;
	private int userId;
	private ArrayList<Integer> musicas;
	private ArrayList<String> paths;
	
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public ArrayList<Integer> getMusicas() {
		return musicas;
	}
	public void setMusicas(ArrayList<Integer> musicas) {
		this.musicas = musicas;
	}
	
	public ArrayList<String> getPaths() {
		return paths;
	}
	public void setPaths(ArrayList<String> paths) {
		this.paths = paths;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
