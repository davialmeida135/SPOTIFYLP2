package com.spotify.dao;

import java.sql.Connection;

import com.spotify.data.DataBase;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar playlists
public class PlaylistDAO {
	
	public static void novaPlaylist(String nome,String descricao,int userId) {
        String sql = "INSERT INTO playlists(nome, descricao,proprietario_id) VALUES(?,?,?)";  

	}
	
	public static void removerPlaylist(String nome, int userId) {
		
	}
	
	public static void removerPlaylistsUsuario(int userId,Connection conn) {
		String sql = "DELETE FROM usuarios WHERE proprietario_id = ?";
		
		
	}
	
	//Colocar musica na playlist
	public static void adicionarMusica(String nome,int playlistId,Connection conn) {
	
		
	}
	
	//Tirar musica da playlist
	public static void removerMusica(String nome,int playlistId) {
		
	}
	public static int getPlaylistId(String nome,int userId) {
		
		
		return -1;
	}
}
