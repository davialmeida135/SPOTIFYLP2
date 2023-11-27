package com.spotify.dao;

import java.sql.Connection;

import com.spotify.data.DataBase;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar músicas
public class MusicaDAO {
	
	public static void novaMusica(String nome, String path,Connection conn) {
		
	}
	
	public static void novoDiretorio(String path,Connection conn) {
		
	}
	
	public static void removerMusica(String nome,Connection conn) {
		
	}
	
	public static int  getMusicaId(String nome, int userId,Connection conn) {
		
		return -1;
	}
	public static String getMusicaPath(String nome, int userId,Connection conn) {
		String path = null;
		
		return path;
	}

}
