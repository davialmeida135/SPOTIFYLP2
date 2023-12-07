package com.spotify.data;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.sql.Connection;
import java.util.stream.Stream;

import com.spotify.dao.MusicaDAO;
import com.spotify.dao.PlaylistDAO;


public class Arquivos {
	
	 public static void lerPasta() {
			try {
				Stream<Path> filepath= Files.walk(Paths.get("storage"));
				filepath.forEach(System.out::println);
				filepath.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 public static void copyFile(File file,Connection conn) throws IOException {
		 
		 	String destino = "./storage/musicas";
		         
	        if (file == null) 
	            return; 
	        
	  
	            
	        if (file.exists()) { 
	            // using file filter 
	            if (filter.accept(file)) {
	            	String filename = file.getName().substring(0,file.getName().length()-4);
	                System.out.println(filename);
	                
	                
	                
	                Path destinoPath = Paths.get(destino+"/"+file.getName());
	                Path filePath = Paths.get(file.getPath());
	                Files.copy(filePath,destinoPath,StandardCopyOption.REPLACE_EXISTING);
	                MusicaDAO.novaMusica(filename, destino+"/"+file.getName(), conn);
	                
	                //PlaylistDAO.adicionarMusica(filename, 4, 0, conn);
	                
	            } 
	        } 
	 } 

	 
	 //Escaneia todos os arquivos de uma pasta
	 public static void copyAllFiles(File file,Connection conn) throws IOException {
	 
	 	String destino = "./storage/musicas";
  
  
        File[] files = file.listFiles(); 
        if (files == null) 
            return; 
        for (File f : files) { 
  
            if (f.isDirectory() && f.exists()) { 
                try { 
                	copyAllFiles(f,conn); 
                } 
                catch (Exception e) { 
                    e.printStackTrace(); 
                    continue;
                } 
            } 
            else if (!f.isDirectory() && f.exists()) { 
                // using file filter 
                if (filter.accept(f)) {
                	String filename = f.getName().substring(0,f.getName().length()-4);
	                System.out.println(filename);
                    
                    Path destinoPath = Paths.get(destino+"/"+f.getName());
                    Path filePath = Paths.get(f.getPath());
                    Files.copy(filePath,destinoPath,StandardCopyOption.REPLACE_EXISTING);
                    MusicaDAO.novaMusica(filename, destino+"/"+f.getName(), conn);
                    
                    //PlaylistDAO.adicionarMusica(filename, 4, 0, conn);
                    
                } 
            } 
	    } 
	 } 
	 
	 //Filtro de apenas arquivos mp3 e wav
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
