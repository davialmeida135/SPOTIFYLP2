package com.spotify.view;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spotify.control.*;
import com.spotify.view.*;
import com.spotify.dao.*;
import com.spotify.data.*;
import com.spotify.model.Musica;

//No momento apenas testes da database
//Em breve testes da interface

public class Main {
	public static void main(String[] args) throws SQLException {
		  System.out.print("oi");
		  
		  
		  
		  LoginView.play_login(args);
		  
	}
		  
			/*try {
			Connection conn = DataBase.connect("database.db");
			Arquivos.copyAllFiles("./storage/teste",conn);
			conn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
			UsuarioDAO.novoUsuario("Joao vitor", "calangofange", "123", "VIP", conn);
		  UsuarioDAO.novoUsuario("Adminilson","admin","admin","VIP",conn);
		  ArrayList<Musica> musicas= PlaylistDAO.getMusicasPlaylist(1, conn);
		  System.out.println(musicas.get(0).getNome());
		  System.out.println(musicas.get(1).getNome());
		  System.out.println(musicas);
		  //
		  conn.close();
		  //createNewDatabase("database.db");
		  Connection conn = DataBase.connect("database.db");
		  Connection conn = DataBase.connect("database.db");
		  if(conn!=null) {	  
			try {
				DataBase.musicas_da_playlist(conn,1);
				System.out.println("============");
				DataBase.select_all_user(conn,"usuarios");
				System.out.println("============");
				DataBase.playlists_do_usuario(conn,1);
				System.out.println("============");
				DataBase.busca_usuario(conn,"joninas2003");
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
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
			 * 
			
	}*/
}
