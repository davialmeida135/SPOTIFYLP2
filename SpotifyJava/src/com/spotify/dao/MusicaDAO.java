package com.spotify.dao;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.data.Arquivos;
import com.spotify.data.DataBase;
import com.spotify.model.Musica;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar músicas
public class MusicaDAO {
	
	//Adiciona uma musica à database
	public static void novaMusica(String nome, String path,Connection conn) {
	    String sql = "INSERT INTO musicas (titulo, path) VALUES (?, ?)";
	    String sql2 = "INSERT INTO musicas_e_playlists(id_musica, id_playlist)"
                + "VALUES(?,?)";
	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        pstmt.setString(2, path);
	        pstmt.executeUpdate();
	        System.out.println("Música criada com sucesso!");

	        int musicaId = MusicaDAO.getMusicaId(nome, conn);
	        int playlistId = PlaylistDAO.getPlaylistId("Musicas", 0, conn);
	        pstmt = conn.prepareStatement(sql2);
	        pstmt.setInt(1, musicaId);
	        pstmt.setInt(2, playlistId);
	        pstmt.executeUpdate();
	        System.out.println("Música adicionada ao banco de dados!");
	        
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	//Adiciona diretório à database
	public static void novoDiretorio(File file, Connection conn) { 
		String sql = "INSERT INTO diretorios (path) VALUES (?)";
		try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, file.getPath());
	        pstmt.executeUpdate();

	        System.out.println("Diretório adicionado com sucesso!");
	        Arquivos.copyAllFiles(file, conn);
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removerMusica(String nome, Connection conn) {
	    String sql = "DELETE FROM musicas WHERE titulo = ?";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        pstmt.executeUpdate();

	        System.out.println("Música removida com sucesso!");
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public static int getMusicaId(String nome, Connection conn) {
	    String sql = "SELECT musicas.id FROM musicas WHERE musicas.titulo = ?";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        //pstmt.setInt(2, userId);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("id");
	        } else {
	            System.out.println("Música não encontrada.");
	            return -1;
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return -1;
	    }

	}
	
	public static Musica getMusica(String nome, Connection conn) {
		Musica musica = new Musica();
	    String sql = "SELECT musicas.id,musicas.titulo,musicas.path FROM musicas WHERE musicas.titulo = ?";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        //pstmt.setInt(2, userId);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            musica.setId(rs.getInt("id"));
	            musica.setTitulo(rs.getString("titulo"));
	            musica.setPath(rs.getString("path"));
	            return musica;
	        } else {
	            System.out.println("Música não encontrada.");
	            return null;
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return null;
	    }

	}
	
	public static Boolean estaNaPlaylist(){
		return null;
		
	}
	
	//Filtro de mp3 e wav
	 static FileFilter filter = new FileFilter() { 
	        @Override 
	          public boolean accept(File file) 
	        { 
	            if (file.getName().endsWith(".mp3") 
	                || file.getName().endsWith(".wav")) { 
	                return true; 
	            } 
	            return false; 
	        } 
	    }; 
}
