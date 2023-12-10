package com.spotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.spotify.data.DataBase;
import com.spotify.model.Musica;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar playlists
public class PlaylistDAO {
	
	public static void novaPlaylist(String nome,int userId,Connection conn) {
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
	
    public static void removerPlaylist(String nome, int userId, Connection conn) {
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
    
	//Remove todas as musicas de uma playlist
    public static void removerMusicasPlaylist(int id_playlist, Connection conn) {
        String sql = "DELETE FROM musicas_e_playlists WHERE id_playlist = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_playlist);
            pstmt.executeUpdate();

            System.out.println("Todas as musicas foram removidas da playlist!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	//Colocar musica na playlist
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
	
	//Tirar musica da playlist
    public static void removerMusica(String nome, int playlistId, Connection conn) {
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
	
	//Retorna o id de uma playlist baseado no nome e no id do dono
    public static int getPlaylistId(String nome, int userId, Connection conn) {
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
	
    
	//Retorna uma fila com todas as musicas de uma playlist
	public static Queue<Musica> getMusicasPlaylist(int playlistId,Connection conn) throws SQLException{
		Queue<Musica> musicas = new LinkedList<Musica>();
		//Musica novaMusica = new Musica();
		String sql = "SELECT m.titulo, m.path "
                + "FROM playlists p "
                + "JOIN musicas_e_playlists mep ON p.id = mep.id_playlist "
                + "JOIN musicas m ON mep.id_musica = m.id "
                + "WHERE p.id = '" + playlistId + "'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		int i = 0;
        while (rs.next()) {
        	Musica novaMusica = new Musica();
            String title = rs.getString("titulo");
            String path = rs.getString("path");
            novaMusica.setTitulo(title);
        	novaMusica.setPath(path);
        	
        	musicas.add(novaMusica);
        	//System.out.println(novaMusica.getNome()+novaMusica.getPath());
        	

            System.out.println("Title: " + title);
            System.out.println("Path: " + path);
            System.out.println("------------------------");
        }
		return musicas;
	}
	
	//Retorna um array com o id de todas as musicas de uma playlist
	public static ArrayList<Integer> getMusicasId(int playlistId){
		ArrayList<Integer> musicasId = new ArrayList<Integer>();
		return musicasId;	
	}
}
