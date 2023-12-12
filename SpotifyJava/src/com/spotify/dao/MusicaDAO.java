package com.spotify.dao;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.data.Arquivos;
import com.spotify.data.DataBase;
import com.spotify.model.Musica;

//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, modificar e tocar músicas
public class MusicaDAO {

    //Filtro de mp3 e wav
    static FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            if (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav")) {
                return true;
            }
            return false;
        }
    };

    /**
     * Adiciona uma nova música à biblioteca do sistema.
     *
     * @param nome O nome da música (String).
     * @param path O caminho do arquivo de música (String).
     * @param conn Uma conexão aberta com o banco de dados.
     */
    public static void novaMusica(String nome, String path, Connection conn) {
        String sql = "INSERT INTO musicas (titulo, path) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, path);
            pstmt.executeUpdate();
            System.out.println("Música criada com sucesso!");

            int musicaId = MusicaDAO.getMusicaId(nome, conn);
            int playlistId = PlaylistDAO.getPlaylistId("Musicas", 0, conn);
            pstmt = conn.prepareStatement("INSERT INTO musicas_e_playlists(id_musica, id_playlist)"
                    + "VALUES(?,?)");
            pstmt.setInt(1, musicaId);
            pstmt.setInt(2, playlistId);
            pstmt.executeUpdate();
            System.out.println("Música adicionada ao banco de dados!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adiciona todos os arquivos de música (.mp3 e .wav) de um diretório à biblioteca do sistema.
     *
     * @param file O diretório contendo as músicas (File).
     * @param conn Uma conexão aberta com o banco de dados.
     */
    public static void novoDiretorio(File file, Connection conn) {
        try {
            Arquivos.copyAllFiles(file, conn);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove uma música da biblioteca do sistema.
     *
     * @param nome O nome da música (String).
     * @param conn Uma conexão aberta com o banco de dados.
     */
    public static void removerMusica(String nome, Connection conn) {
        String sql = "DELETE FROM musicas WHERE titulo = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.executeUpdate();
            System.out.println("Música removida com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retorna o ID da música associado ao nome fornecido.
     *
     * @param nome O nome da música (String).
     * @param conn Uma conexão aberta com o banco de dados.
     * @return
     * O ID da música (int), ou -1 se a música não for encontrada.
     */
	
	public static int getMusicaId(String nome, Connection conn) {
	    String sql = "SELECT musicas.id FROM musicas WHERE musicas.titulo = ?";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, nome);
	        //pstmt.setInt(2, userId);

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
	
	/**
     * Retorna um objeto Musica com as informações da música associada ao nome fornecido.
     *
     * @param nome O nome da música (String).
     * @param conn Uma conexão aberta com o banco de dados.
     * @return
     * Um objeto Musica contendo as informações da música, ou null se a música não for encontrada.
     */
    public static Musica getMusica(String nome, Connection conn) {
        Musica musica = new Musica();
        String sql = "SELECT musicas.id,musicas.titulo,musicas.path FROM musicas WHERE musicas.titulo = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            //pstmt.setInt(2, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                musica.setId(rs.getInt("id"));
                musica.setTitulo(rs.getString("titulo"));
                musica.setPath(rs.getString("path"));
                return musica;
            } else {
                System.out.println("Música não encontrada.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

   
}
