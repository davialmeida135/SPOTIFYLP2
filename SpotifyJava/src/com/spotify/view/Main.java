package com.spotify.view;

import java.sql.Connection;
import java.sql.SQLException;

import com.spotify.data.*;


//No momento apenas testes da database
//Em breve testes da interface

public class Main {
	public static void main(String[] args) throws SQLException {
		  try {
				Connection conn = DataBase.connect("database.db");
				Arquivos.inicializarDiretorios(conn);
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  LoginView.play_login(args);	  
	}
		  
}
