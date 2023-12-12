package com.spotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


import com.spotify.model.Musica;

/**
 * Esta classe utiliza os métodos fornecidos pela classe Database
 * para adicionar, remover, modificar e tocar playlists.
 */
public class PlaylistDAO {
	
	/**
	 * Cria uma nova playlist.
	 * @param nome Nome da playlist.
	 * @param userId ID do usuário proprietário da playlist.
	 * @param conn Conexão com o banco de dados.
	 * @throws SQLException Exceção lançada em caso de erro de SQL.
	 */
	public static void novaPlaylist(String nome, int userId, Connection conn)  {
		String sql = "INSERT INTO playlists(nome, proprietario_id) VALUES(?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nome);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			System.out.println("Playlist criada com sucesso!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Remove uma playlist.
	 * @param nome Nome da playlist.
	 * @param userId ID do usuário proprietário da playlist.
	 * @param conn Conexão com o banco de dados.
	 * @throws SQLException Exceção lançada em caso de erro de SQL.
	 */
	public static void removerPlaylist(String nome, int userId, Connection conn)  {
		String sql = "DELETE FROM playlists WHERE nome = ? AND proprietario_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nome);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			System.out.println("Playlist removida com sucesso!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Remove todas as músicas de uma playlist.
	 * @param idPlaylist ID da playlist.
	 * @param conn Conexão com o banco de dados.
	 * @throws SQLException Exceção lançada em caso de erro de SQL.
	 */
	public static void removerMusicasPlaylist(int idPlaylist, Connection conn)  {
		String sql = "DELETE FROM musicas_e_playlists WHERE id_playlist = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idPlaylist);
			pstmt.executeUpdate();
			System.out.println("Todas as músicas foram removidas da playlist!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adiciona uma música a uma playlist.
	 * @param nome Nome da música.
	 * @param playlistId ID da playlist.
	 * @param userId ID do usuário proprietário da playlist.
	 * @param conn Conexão com o banco de dados.
	 * @throws SQLException Exceção lançada em caso de erro de SQL.
	 */
	public static void adicionarMusica(String nome, int playlistId, int userId, Connection conn) {
		String sql = "INSERT INTO musicas_e_playlists(id_musica, id_playlist)"
				+ "VALUES(?,?)";
		int musicaId = MusicaDAO.getMusicaId(nome, conn);
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, musicaId);
			pstmt.setInt(2, playlistId);
			pstmt.executeUpdate();
			System.out.println("Música adicionada à playlist com sucesso!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Remove uma música de uma playlist.
	 * @param nome Nome da música.
	 * @param playlistId ID da playlist.
	 * @param conn Conexão com o banco de dados.
	 * @throws SQLException Exceção lançada em caso de erro de SQL.
	 */
	public static void removerMusica(String nome, int playlistId, Connection conn){
		String sql = "DELETE FROM musicas_e_playlists"
				+ " WHERE id_musica = ? AND id_playlist = ?";
		int musicaId = MusicaDAO.getMusicaId(nome, conn);
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, musicaId);
			pstmt.setInt(2, playlistId);
			pstmt.executeUpdate();
			System.out.println("Música removida da playlist com sucesso!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Retorna o ID de uma playlist com base no nome e no ID do dono.
	 * @param nome Nome da playlist.
	 * @param userId ID do usuário proprietário da playlist.
	 * @param conn Conexão com o banco de dados.
	 * @return O ID da playlist, ou -1 caso a playlist não seja encontrada.
	 * @throws SQLException Exceção lançada em caso de erro de SQL.
	 */
	public static int getPlaylistId(String nome, int userId, Connection conn){
		String sql = "SELECT playlists.id FROM playlists "
				+ "WHERE playlists.nome = ? AND playlists.proprietario_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nome);
			pstmt.setInt(2, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			} else {
				System.out.println("Playlist não encontrada.");
				return -1;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	/**
	 * Retorna uma fila contendo todas as músicas de uma playlist.
	 * @param playlistId ID da playlist.
	 * @param conn Conexão com o banco de dados.
	 * @return Uma fila de objetos Musica contendo as músicas da playlist.
	 * @throws SQLException Exceção lançada em caso de erro de SQL.
	 */
	public static Queue<Musica> getMusicasPlaylist(int playlistId, Connection conn) throws SQLException {
		Queue<Musica> musicas = new LinkedList<>();
		String sql = "SELECT m.titulo, m.path "
				+ "FROM playlists p "
				+ "JOIN musicas_e_playlists mep ON p.id = mep.id_playlist "
				+ "JOIN musicas m ON mep.id_musica = m.id "
				+ "WHERE p.id = '" + playlistId + "'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Musica novaMusica = new Musica();
			String title = rs.getString("titulo");
			String path = rs.getString("path");
			novaMusica.setTitulo(title);
			novaMusica.setPath(path);
			musicas.add(novaMusica);
		}
		return musicas;
	}
	
	/**
	 * Retorna um array com os IDs de todas as músicas de uma playlist.
	 * @param playlistId ID da playlist.
	 * @return Um array de inteiros contendo os IDs das músicas.
	 */
	public static ArrayList<Integer> getMusicasId(int playlistId) {
		ArrayList<Integer> musicasId = new ArrayList<>();
		// TODO Implementar a lógica para recuperar os IDs das músicas da playlist
		return musicasId;
	}
}
