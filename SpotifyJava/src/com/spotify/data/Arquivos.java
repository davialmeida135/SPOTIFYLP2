package com.spotify.data;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.stream.Stream;


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
	 
	 public static void copyFile(File sourceFile, File destFile) throws IOException {
		    if(!destFile.exists()) {
		        destFile.createNewFile();
		    }

		    FileChannel source = null;
		    FileChannel destination = null;

		    try {
		        source = new FileInputStream(sourceFile).getChannel();
		        destination = new FileOutputStream(destFile).getChannel();
		        destination.transferFrom(source, 0, source.size());
		    }
		    finally {
		        if(source != null) {
		            source.close();
		        }
		        if(destination != null) {
		            destination.close();
		        }
		    }
		}
	 
	 //Escaneia todos os arquivos de uma pasta
	 public static void copyAllFiles(String path) throws IOException {
	 
	 	String destino = "./storage/musicas";
	     
        File file = new File(path);
        
        
  
        File[] files = file.listFiles(); 
        if (files == null) 
            return; 
        for (File f : files) { 
  
            if (f.isDirectory() && f.exists()) { 
                try { 
                	copyAllFiles(f.getPath()); 
                } 
                catch (Exception e) { 
                    e.printStackTrace(); 
                    continue;
                } 
            } 
            else if (!f.isDirectory() && f.exists()) { 
                // using file filter 
                if (filter.accept(f)) {
                    System.out.println(f.getName());
                    
                    Path destinoPath = Paths.get(destino+"/"+f.getName());
                    Path filePath = Paths.get(f.getPath());
                    Files.copy(filePath,destinoPath,StandardCopyOption.REPLACE_EXISTING);
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
