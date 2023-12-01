package com.spotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.data.DataBase;
import com.spotify.model.Usuario;
//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, diferenciar e modificar usuários
public class UsuarioDAO {
	
	public static void novoUsuario(String nome, String usuario, String senha, String tipo,Connection conn) {
        String sql = "INSERT INTO usuarios(nome, tipo,username,password) VALUES(?,?,?,?)";  
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, nome);  
            pstmt.setString(2, tipo);
            pstmt.setString(3, usuario);
            pstmt.setString(4, senha);
            pstmt.executeUpdate();
   
            System.out.println("Usuário criado com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        } 
	}
	
	public static int getUserId(String usuario,Connection conn) {
		String sql = "SELECT usuarios.id\r\n"
		  		+ "FROM usuarios\r\n"
		  		+ "WHERE usuarios.username = ?;";
		  
		  try { 
			PreparedStatement stmt = conn.prepareStatement(sql);  
			stmt.setString(1, usuario);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) {
				//System.out.println("Usuário não encontrado.");
				return 404;
			}
			else{
				//System.out.println("Usuario encontrado!");
				return rs.getInt("id");
			        }
			}catch (SQLException e) {  
			    System.out.println(e.getMessage());  
			    }
		  return -1;
	}
	
	public static void removerUsuario(String usuario,Connection conn) {
		String sql = "DELETE FROM usuarios WHERE username = ?";
		try {
			//Connection conn = DataBase.connect("database.db");
			int userId = getUserId(usuario,conn);
			PreparedStatement stmt = conn.prepareStatement(sql);  
			stmt.setString(1, usuario);
			stmt.executeUpdate();
			
			PlaylistDAO.removerPlaylistsUsuario(userId,conn);
			
		}catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
	
	}
	
	public static void removerUsuario(int userId,Connection conn) {
		String sql = "DELETE FROM usuarios WHERE id = ?";
		try {
			//Connection conn = DataBase.connect("database.db");
			PreparedStatement stmt = conn.prepareStatement(sql);  
			stmt.setInt(1, userId);
			stmt.executeUpdate();
			PlaylistDAO.removerPlaylistsUsuario(userId,conn);
			
		}catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		
		
	
	}
	
	public static int autenticar(String usuario, String senha,Connection conn) {
		String sql = "SELECT usuarios.id, usuarios.username, usuarios.password\r\n"
		  		+ "FROM usuarios\r\n"
		  		+ "WHERE usuarios.username = ?;";
		  
		  try { 
			  
			  	//Connection conn = DataBase.connect("database.db");
			  	PreparedStatement stmt = conn.prepareStatement(sql);  
			  	stmt.setString(1, usuario);
	            ResultSet rs = stmt.executeQuery();
	            
            if(!rs.next()) {
		  		System.out.println("Usuário não encontrado.");
		  		return -1;
		  	}
            if(rs.getString("password").equals(senha)) {
            	System.out.println("Login bem sucedido!");
            	return rs.getInt("id");
            }
            else {	
            	System.out.println("Senha incorreta");
            	return -2;
            }   
	            
		  } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }   
		return -3;
	}
	
	public static Usuario getUsuario(int userId, Connection conn) {
		Usuario usuario = new Usuario();
		String sql = "SELECT usuarios.username,usuarios.nome,usuarios.tipo\r\n"
		  		+ "FROM usuarios\r\n"
		  		+ "WHERE usuarios.id = ?;";
		
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) {
		  		System.out.println("Usuário não encontrado.");
		  		return null;
		  	}
			usuario.setId(userId);
			usuario.setNome(rs.getString("nome"));
			usuario.setTipo(rs.getString("tipo"));
			usuario.setUsuario(rs.getString("username"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	  	
		return usuario;

	}

}
