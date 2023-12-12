package com.spotify.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.dao.PlaylistDAO;

/**
 * Esta classe gerencia a conexão e manipulação do banco de dados SQLite.
 */
public class DataBase {

	/**
	 * Estabelece uma conexão com o banco de dados SQLite.
	 *
	 * @param fileName O nome do arquivo do banco de dados.
	 * @return Uma conexão com o banco de dados, ou null caso a conexão falhe.
	 */
	public static Connection connect(String fileName) {
		Connection conn = null;
		try {
			// Parâmetros do banco de dados
			String url = "jdbc:sqlite:storage/db/" + fileName;
			// Cria uma conexão com o banco de dados
			conn = DriverManager.getConnection(url);

			System.out.println("Conexão com SQLite estabelecida.");
			return conn;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return conn;
		}
	}

	/**
	 * Cria um novo banco de dados.
	 *
	 * @param fileName O nome do arquivo do banco de dados.
	 */
	public static void createNewDatabase(String fileName) {
		String url = "jdbc:sqlite:storage/db/" + fileName;

		try {
			Connection conn = DriverManager.getConnection(url);
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("O nome do driver é " + meta.getDriverName());
				System.out.println("Um novo banco de dados foi criado.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Cria todas as tabelas necessárias para o funcionamento do sistema.
	 *
	 * @param conn A conexão com o banco de dados.
	 */
	public static void criarTodasTabelas(Connection conn) {
		String sql = "CREATE TABLE musicas (\r\n"
				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
				+ "  titulo TEXT NOT NULL UNIQUE,\r\n"
				+ "  path TEXT NOT NULL,\r\n"
				+ "  autor TEXT\r\n"
				+ ");\r\n"
				+ "CREATE TABLE playlists (\r\n"
				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
				+ "  nome TEXT NOT NULL,\r\n"
				+ "  proprietario_id INTEGER\r\n"
				+ ");\r\n"
				+ "CREATE TABLE musicas_e_playlists (\r\n"
				+ "  id_musica INTEGER NOT NULL,\r\n"
				+ "  id_playlist INTEGER NOT NULL,\r\n"
				+ "  FOREIGN KEY (id_musica) REFERENCES musicas (id),\r\n"
				+ "  FOREIGN KEY (id_playlist) REFERENCES playlists (id)\r\n"
				+ ");\r\n"
				+ "CREATE TABLE usuarios (\r\n"
				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
				+ "  nome TEXT NOT NULL,\r\n"
				+ "  tipo TEXT NOT NULL,\r\n"
				+ "  username TEXT NOT NULL UNIQUE,\r\n"
				+ "  password TEXT NOT NULL\r\n"
				+ ");\r\n"
				+ "CREATE TABLE diretorios (\r\n"
				+ "  id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
				+ "  path TEXT NOT NULL ,\r\n"
				+ ");\r\n";

		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			PlaylistDAO.novaPlaylist("Musicas", 0, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Cria a tabela de relacionamentos entre músicas e playlists.
	 *
	 * @param conn A conexão com o banco de dados.
	 */
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

	  
	  
	  
}
	  
	  

