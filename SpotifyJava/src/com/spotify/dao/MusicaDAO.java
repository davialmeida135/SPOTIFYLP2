package com.spotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.data.DataBase;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar músicas
public class MusicaDAO {
	
	public static void novaMusica(String nome, String path, Connection conn) {
	    String sql = "INSERT INTO musicas (nome, path) VALUES (?, ?)";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        pstmt.setString(2, path);
	        pstmt.executeUpdate();

	        System.out.println("Música criada com sucesso!");
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public static void novoDiretorio(String path, Connection conn) {

	}
	
	public static void removerMusica(String nome, Connection conn) {
	    String sql = "DELETE FROM musicas WHERE nome = ?";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        pstmt.executeUpdate();

	        System.out.println("Música removida com sucesso!");
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public static int getMusicaId(String nome, int userId, Connection conn) {
	    String sql = "SELECT musicas.id FROM musicas WHERE musicas.nome = ? AND musicas.proprietario_id = ?";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        pstmt.setInt(2, userId);

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
	
	public static String getMusicaPath(String nome, int userId,Connection conn) {
		String path = null;
		
		return path;
	}

}
