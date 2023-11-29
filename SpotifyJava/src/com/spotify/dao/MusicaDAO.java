package com.spotify.dao;

import java.io.File;
import java.io.FileFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.data.DataBase;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar músicas
public class MusicaDAO {
	
	//Adiciona uma musica à database
	public static void novaMusica(String nome, String path,int userId ,Connection conn) {
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
	
	//Adiciona todas as musicas de uma pasta à database
	public static void novoDiretorio(String path,int userId, Connection conn) 
		{ 
	        File file = new File(path); 
	  
	        File[] files = file.listFiles(); 
	        if (files == null) 
	            return; 
	  
	        for (File f : files) { 
	  
	            if (f.isDirectory() && f.exists()) { 
	                try { 
	                	novoDiretorio(f.getPath(),userId,conn); 
	                } 
	                catch (Exception e) { 
	                    e.printStackTrace(); 
	                    continue; 
	                } 
	            } 
	            else if (!f.isDirectory() && f.exists()) { 
	                // using file filter 
	                if (filter.accept(f)) {
	                	novaMusica(f.getName(), f.getPath(), -1,conn) ;
	                } 
	            } 
	        } 
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
		String sql = "SELECT musicas.path FROM musicas "
				+ "WHERE musicas.nome = ?";
		
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nome);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
	            return rs.getString("path");
	        } else {
	            System.out.println("Música não encontrada.");
	            return "";
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		return path;
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
