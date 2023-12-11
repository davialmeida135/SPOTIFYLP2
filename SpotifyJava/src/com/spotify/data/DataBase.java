package com.spotify.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.dao.PlaylistDAO;

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
	public static void criarTodasTabelas(Connection conn) {  
	        // SQLite connection string 
	          
	        // SQL statement for creating a new table  
	        String sql ="CREATE TABLE musicas (\r\n"
	        		+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
	        		+ "    titulo TEXT NOT NULL UNIQUE,\r\n"
	        		+ "    path TEXT NOT NULL,\r\n"
	        		+ "	   autor TEXT"
	        		+ ");\r\n"
	        		+ "CREATE TABLE playlists (\r\n"
	        		+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
	        		+ "    nome TEXT NOT NULL,\r\n"
	        		+ " proprietario_id INTEGER"
	        		+ ");\r\n"
	        		+ "CREATE TABLE musicas_e_playlists (\r\n"
	        		+ "    id_musica INTEGER NOT NULL,\r\n"
	        		+ "    id_playlist INTEGER NOT NULL,\r\n"
	        		+ "    FOREIGN KEY (id_musica) REFERENCES musicas (id),\r\n"
	        		+ "    FOREIGN KEY (id_playlist) REFERENCES playlists (id)\r\n"
	        		+ ");\r\n"
	        		+ "CREATE TABLE usuarios (\r\n"
	        		+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
	        		+ "    nome TEXT NOT NULL,\r\n"
	        		+ "    tipo TEXT NOT NULL,\r\n"
	        		+ "    username TEXT NOT NULL UNIQUE,\r\n"
	        		+ "    password TEXT NOT NULL\r\n"
	        		+ ");\r\n"
	        		+ "CREATE TABLE diretorios (\r\n"
	        		+ "    id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
	        		+ "    path TEXT NOT NULL ,\r\n"
	        		+ ");\r\n";
	          
	        try{   
	            Statement stmt = conn.createStatement();  
	            stmt.execute(sql);
	            PlaylistDAO.novaPlaylist("Musicas", 0, conn);
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }  
	   
	   
		public static void criarTabela(Connection conn) {  
	        // SQLite connection string 
	          
	        // SQL statement for creating a new table  
	        String sql ="CREATE TABLE musicas_e_playlists (\r\n"
	        		+ "    id_musica INTEGER NOT NULL,\r\n"
	        		+ "    id_playlist INTEGER NOT NULL,\r\n"
	        		+ "    FOREIGN KEY (id_musica) REFERENCES musicas (id),\r\n"
	        		+ "    FOREIGN KEY (id_playlist) REFERENCES playlists (id)\r\n"
	        		+ ");\r\n";
	          
	        try{   
	            Statement stmt = conn.createStatement();  
	            stmt.execute(sql);  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }  	

	   public static void insert_user(Connection conn,String nome, String tipo, String username, String password) {  
	        String sql = "INSERT INTO usuarios(nome, tipo,username,password) VALUES(?,?,?,?)";  
	   
	        try{
	            PreparedStatement pstmt = conn.prepareStatement(sql);  
	            pstmt.setString(1, nome);  
	            pstmt.setString(2, tipo);
	            pstmt.setString(3, username);
	            pstmt.setString(4, password);
	            pstmt.executeUpdate();
	            System.out.println("Usuário criado com sucesso!");
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
	            if(!rs.next()) {
			  		System.out.println("A playlist "+id_playlist+" não possui músicas registradas.");
			  		return;
			  	}
	            do{ 
	            	 System.out.println(rs.getString("id") +  "\t"+
	            			 rs.getString("titulo") +  "\t" +                     
                             rs.getString("path")+ "\t");
	            }while (rs.next());
	            
		  } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }   
		  
	  }
	  
	  //Todas as playlists de um usuário com o id fornecido
	  public static void playlists_do_usuario(Connection conn, int proprietario_id) {
		  String sql = "SELECT playlists.id, playlists.nome\r\n"
			  		+ "FROM playlists\r\n"
			  		+ "WHERE playlists.proprietario_id = "+proprietario_id+";";
			  
			  try {          
		            Statement stmt  = conn.createStatement();
		            ResultSet rs = stmt.executeQuery(sql);
		            
	            if(!rs.next()) {
			  		System.out.println("O usuário "+proprietario_id+" não possui playlists registradas.");
			  		return;
			  	}
	            do { 
	            	System.out.println(rs.getString("id") +  "\t"+
	            			 rs.getString("nome") +  "\t");
	            } while (rs.next());
		            
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
	  
	  public static void insert_diretorio(Connection conn,String path) {  
	        String sql = "INSERT INTO diretorios(path) VALUES(?)";  
	   
	        try{
	            PreparedStatement pstmt = conn.prepareStatement(sql);  
	            pstmt.setString(1, path);  

	            pstmt.executeUpdate();
	            System.out.println("Diretorio inserido com sucesso!");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage()); 
	        }  
	  }
	  
	  
	  
}
	  
	  

