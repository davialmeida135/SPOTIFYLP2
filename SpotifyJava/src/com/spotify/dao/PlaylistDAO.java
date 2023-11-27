package com.spotify.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.spotify.data.DataBase;
import com.spotify.model.Musica;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar playlists
public class PlaylistDAO {
	
	public static void novaPlaylist(String nome,String descricao,int userId,Connection conn) {
        String sql = "INSERT INTO playlists(nome, descricao,proprietario_id) VALUES(?,?,?)";  

	}
	
	public static void removerPlaylist(String nome, int userId,Connection conn) {
		String sql = "DELETE FROM playlists WHERE id = ?";
		int playlistId = getPlaylistId(nome,userId, conn);
	}
	
	//Remove todas as playlists de um usuário
	public static void removerPlaylistsUsuario(int userId,Connection conn) {
		String sql = "DELETE FROM playlists WHERE proprietario_id = ?";
		
		
	}
	
	//Colocar musica na playlist
	public static void adicionarMusica(String nome,int playlistId,int userId,Connection conn) {
		 String sql = "INSERT INTO musicas_e_playlists(id_musica, id_playlist)"
		 			+ "VALUES(?,?)";
		 
		 int musicaId = MusicaDAO.getMusicaId(nome, userId, conn);
		
	}
	
	//Tirar musica da playlist
	public static void removerMusica(int musicId,int playlistId,Connection conn) {
		String sql = "REMOVE FROM musicas_e_playlists "
	 			+ "WHERE id_musica=? AND id_playlist=?";
		
	}
	
	//Retorna o id de uma playlist baseado no nome e no id do dono
	public static int getPlaylistId(String nome,int userId,Connection conn) {
		String sql = "SELECT playlists.id,playlists FROM playlists "
				+ "WHERE playlist.nome = ? AND playlist.proprietario_id = ?";
		
		return -1;
	}
	
	//Retorna um array com todas as musicas de uma playlist
	public static ArrayList<Musica> getMusicasPlaylist(int playlistId){
		ArrayList<Musica> musicas = new ArrayList<Musica>();
		return musicas;
	}
	
	//Retorna um array com o id de todas as musicas de uma playlist
	public static ArrayList<Integer> getMusicasId(int playlistId){
		ArrayList<Integer> musicasId = new ArrayList<Integer>();
		return musicasId;	
	}
}
