package com.spotify.view;

import java.sql.Connection;
import java.sql.SQLException;

import com.spotify.dao.PlaylistDAO;
import com.spotify.data.*;


/**
 * Classe Main.
 * Inicializa o programa com os métodos
 * inicializarDiretorios e play_login*/

public class Main {
	public static void main(String[] args) throws SQLException {
		  try {
				Connection conn = DataBase.connect("database.db");
				Arquivos.inicializarDiretorios(conn);
				//PlaylistDAO.novaPlaylist("Musicas", 0, conn);
				
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  LoginView.play_login(args);	  
	}
		  
}
