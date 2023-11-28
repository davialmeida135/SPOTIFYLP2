package com.spotify.dao;

import java.sql.Connection;

import com.spotify.data.DataBase;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar músicas
public class MusicaDAO {
	
	public static void novaMusica(String nome, String path,Connection conn) {
		String sql = "INSERT INTO musicas(nome, path)"
	 			+ "VALUES(?,?)";
	}
	
	
	//recebe o path de uma pasta e adiciona todas as musicas
	public static void novoDiretorio(String path,int userId,Connection conn) {
		
	}
	
	public static void removerMusica(String nome,int userId,Connection conn) {
		String sql = "SELECT musicas.id FROM musicas "
				+ "WHERE musicas.nome = ?";
		
		
	}
	
	public static int  getMusicaId(String nome, int userId,Connection conn) {
		String sql = "SELECT musicas.id FROM musicas "
				+ "WHERE musicas.nome = ?";
		return -1;
	}
	public static String getMusicaPath(String nome, int userId,Connection conn) {
		String path = null;
		String sql = "SELECT musicas.path FROM musicas "
				+ "WHERE musicas.nome = ?";
		
		
		return path;
	}

}
