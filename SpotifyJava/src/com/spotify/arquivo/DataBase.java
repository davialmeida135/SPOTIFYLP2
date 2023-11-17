package com.spotify.arquivo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	 public static Connection connect(String fileName){ //Inicia conexao com uma database
	        Connection conn = null;  
	        try {  
	            // db parameters  
	            String url = "jdbc:sqlite:storage/db/"+fileName; 
	            // create a connection to the database  
	            conn = DriverManager.getConnection(url);  
	              
	            System.out.println("Connection to SQLite has been established.");  
	            return conn;
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	            return conn;
	        }
	        
	    } 
	 
	public static void createNewDatabase(String fileName) {  
	    	   
	        String url = "jdbc:sqlite:storage/db/" + fileName;
	   
	        try {  
	            Connection conn = DriverManager.getConnection(url);  
	            if (conn != null) {  
	                DatabaseMetaData meta = conn.getMetaData();  
	                System.out.println("The driver name is " + meta.getDriverName());  
	                System.out.println("A new database has been created.");  
	            }  
	   
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	        
	    }  
	public static void createNewTable(Connection conn) {  
	        // SQLite connection string 
	          
	        // SQL statement for creating a new table  
	        String sql ="CREATE TABLE usuarios (\r\n"
	        		+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
	        		+ "    nome TEXT NOT NULL,\r\n"
	        		+ "    tipo TEXT NOT NULL,\r\n"
	        		+ "    username TEXT NOT NULL,\r\n"
	        		+ "    password TEXT NOT NULL\r\n"
	        		+ ");";
	          
	        try{   
	            Statement stmt = conn.createStatement();  
	            stmt.execute(sql);  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }  
	   
	   
	  static public void insert_user(Connection conn,String nome, String tipo, String username, String password) {  
	        String sql = "INSERT INTO usuarios(nome, tipo,username,password) VALUES(?,?,?,?)";  
	   
	        try{  
	   
	            PreparedStatement pstmt = conn.prepareStatement(sql);  
	            pstmt.setString(1, nome);  
	            pstmt.setString(2, tipo);
	            pstmt.setString(3, username);
	            pstmt.setString(4, password);
	            pstmt.executeUpdate();  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	  }
	  
	  static public void insert_musica(Connection conn,String titulo, String path) {  
	        String sql = "INSERT INTO musicas(titulo, path) VALUES(?,?)";  
	   
	        try{  
	   
	            PreparedStatement pstmt = conn.prepareStatement(sql);  
	            pstmt.setString(1, titulo);  
	            pstmt.setString(2, path);  
	            pstmt.executeUpdate();  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	  }
	  
	  static public void insert_playlist(Connection conn,String nome, String descricao,String proprietario) {  
	        String sql = "INSERT INTO playlists(nome, descricao, proprietario) VALUES(?,?,?)";  
	   
	        try{
	   
	            PreparedStatement pstmt = conn.prepareStatement(sql);  
	            pstmt.setString(1, nome);  
	            pstmt.setString(2, descricao);
	            pstmt.setString(3, proprietario);
	            pstmt.executeUpdate();  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }
	  
	  static public void insert_musica_playlist(Connection conn,int id_musica, int id_playlist) {  
	        String sql = "INSERT INTO musicas_e_playlists(id_musica, id_playlist) VALUES(?,?)";  
	   
	        try{  
	   
	            PreparedStatement pstmt = conn.prepareStatement(sql);  
	            pstmt.setInt(1, id_musica);  
	            pstmt.setInt(2, id_playlist);  
	            pstmt.executeUpdate();  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }
	  //Todas as musicas de uma playlist de um id
	  static public void musicas_da_playlist(Connection conn, int id_playlist) {
		  String sql = "SELECT musicas.id, musicas.titulo, musicas.path\r\n"
		  		+ "FROM musicas_e_playlists\r\n"
		  		+ "INNER JOIN musicas ON musicas_e_playlists.id_musica = musicas.id\r\n"
		  		+ "WHERE musicas_e_playlists.id_playlist = "+id_playlist+";";
		  
		  try {          
	            Statement stmt  = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) { 
	            	 System.out.println(rs.getString("id") +  "\t"+
	            			 rs.getString("titulo") +  "\t" +                     
                             rs.getString("path")+ "\t");
	            }
	            
		  } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }   
		  
	  }
	  
	  public static void playlists_do_usuario(Connection conn, int proprietario_id) {
		  String sql = "SELECT playlists.id, playlists.nome\r\n"
			  		+ "FROM playlists\r\n"
			  		+ "WHERE playlists.proprietario_id = "+proprietario_id+";";
			  
			  try {          
		            Statement stmt  = conn.createStatement();
		            ResultSet rs = stmt.executeQuery(sql);
		            while (rs.next()) { 
		            	 System.out.println(rs.getString("id") +  "\t"+
		            			 rs.getString("nome") +  "\t");
		            }
		            
			  } catch (SQLException e) {  
		            System.out.println(e.getMessage());  
		        }   
	  }
	  public static void select_all_user(Connection conn,String table){  
	        String sql = "SELECT * FROM "+table;  
	          
	        try {          
	            Statement stmt  = conn.createStatement();  
	            ResultSet rs    = stmt.executeQuery(sql);

	            while (rs.next()) {  
	                System.out.println(rs.getInt("id") +  "\t" +   
	                                   rs.getString("nome") + "\t" +  
	                                   rs.getString("tipo")+ "\t" +
	                                   rs.getString("username")+ "\t" +
	                                   rs.getString("password")+ "\t");  
	            }
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }  
	  
	  
	  //Main esta aqui pra testar a database por enquanto 
	  public static void main(String[] args) {
		  //createNewDatabase("database.db");
		  Connection conn = connect("database.db");
		  if(conn!=null) {	  
			try {
				musicas_da_playlist(conn,1);
				System.out.println("============");
				select_all_user(conn,"usuarios");
				System.out.println("============");
				playlists_do_usuario(conn,1);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
			/*
			//connect();
			//createNewTable();
			 * insert_user(conn,"Jonas","VIP","joninhas2003","23102003");
				insert_user(conn,"Davi","Comum","davizaoaoao","10102003");
				insert_user(conn,"Joao","VIP","calangofange","808080");
				insert_playlist(conn,"Rock", "Eu sou do rock","Davi");
				insert_playlist(conn,"Pagode", "Alegriaaaa","Joao");
				insert_playlist(conn,"Samba", "Carnaval","Jonas");
				//
			 * 
			 * */
			
		}
}
