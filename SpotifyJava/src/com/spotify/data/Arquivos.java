package com.spotify.data;

import java.io.*;

import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

import com.spotify.dao.MusicaDAO;



public class Arquivos {

    /**
     * Lê todos os arquivos e subdiretórios de uma pasta e os imprime no console.
     */
    public static void lerPasta() {
        try {
            Stream<Path> filepath = Files.walk(Paths.get("storage"));
            filepath.forEach(System.out::println);
            filepath.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Copia um arquivo de música para o diretório de músicas do sistema e adiciona a música ao banco de dados.
     *
     * @param file O arquivo de música a ser copiado (java.io.File).
     * @param conn Uma conexão aberta com o banco de dados.
     * @throws IOException Se houver um erro de leitura ou escrita de arquivos.
     * @throws SQLException Se houver um erro de conexão ou manipulação do banco de dados.
     */
    public static void copyFile(File file, Connection conn) throws IOException {

        String destino = "./storage/musicas";

        if (file == null) {
            return;
        }

        if (file.exists()) {

            // using file filter
            if (filter.accept(file)) {
                String filename = file.getName().substring(0, file.getName().length() - 4);
                System.out.println(filename);

                Path destinoPath = Paths.get(destino + "/" + file.getName());
                Path filePath = Paths.get(file.getPath());
                Files.copy(filePath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
                MusicaDAO.novaMusica(filename, destino + "/" + file.getName(), conn);
            }
        }
    }

    /**
     * Copia todos os arquivos de música de um diretório para o diretório de músicas do sistema e adiciona as músicas ao banco de dados.
     *
     * @param file O diretório a ser copiado (java.io.File).
     * @param conn Uma conexão aberta com o banco de dados.
     * @throws IOException Se houver um erro de leitura ou escrita de arquivos.
     * @throws SQLException Se houver um erro de conexão ou manipulação do banco de dados.
     */
    public static void copyAllFiles(File file, Connection conn) throws IOException {

        String destino = "./storage/musicas";

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {

            if (f.isDirectory() && f.exists()) {
                try {
                    copyAllFiles(f, conn);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            } else if (!f.isDirectory() && f.exists()) {

                // using file filter
                if (filter.accept(f)) {
                    String filename = f.getName().substring(0, f.getName().length() - 4);
                    System.out.println(filename);

                    Path destinoPath = Paths.get(destino + "/" + f.getName());
                    Path filePath = Paths.get(f.getPath());
                    Files.copy(filePath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
                    MusicaDAO.novaMusica(filename, destino + "/" + f.getName(), conn);
                }
            }
        }
    }
    /**
     * Busca todos os diretórios cadastrados no banco de dados e copia todos os arquivos de música de cada diretório para o diretório de músicas do sistema, adicionando as músicas ao banco de dados.
     *
     * @param conn Uma conexão aberta com o banco de dados.
     * 
     * **/
	 public static void inicializarDiretorios(Connection conn){
		String sql = "SELECT path FROM diretorios";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				copyAllFiles(new File(rs.getString("path")),conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
	 /**
	  * Filtro de apenas arquivos mp3 e wav
	  */
	 
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
