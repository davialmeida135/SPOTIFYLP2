package com.spotify.data;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;


public class LerArquivos {
	
	 public static void lerPasta() {
			try {
				Stream<Path> filepath= Files.walk(Paths.get("storage"));
				filepath.forEach(System.out::println);
				filepath.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			{
	     
	       
			}
		}
	 
	 //Escaneia todos os arquivos de uma pasta
	 public static void scanAllFile(String path) 
	    { 
	        File file = new File(path); 
	  
	        File[] files = file.listFiles(); 
	        if (files == null) 
	            return; 
	  
	        for (File f : files) { 
	  
	            if (f.isDirectory() && f.exists()) { 
	                try { 
	                    scanAllFile(f.getPath()); 
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
