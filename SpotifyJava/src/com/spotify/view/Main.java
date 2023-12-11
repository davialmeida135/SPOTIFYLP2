package com.spotify.view;

import java.sql.Connection;
import java.sql.SQLException;

import com.spotify.dao.PlaylistDAO;
import com.spotify.data.*;


//No momento apenas testes da database
//Em breve testes da interface

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
