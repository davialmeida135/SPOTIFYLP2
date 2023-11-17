package com.spotify.arquivo;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;
import java.util.HashMap;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.sql.DatabaseMetaData;
import java.sql.Statement; 
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
//Ler arquivos 
//diretorios.txt: decidir o que ele deve conter
//musicas.txt: Dicionario nome :: path usado pra verificar
//se uma musica existe
//playlist_<nome> : Nao sei ainda
//talvez uma array de path pras musicas
//usuarios: dicionario  com user::senha
public class LerArquivos {
	
	 public void lerPasta() {
			try {
				Stream<Path> filepath= Files.walk(Paths.get("storage"));
				filepath.forEach(System.out::println);
				filepath.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			{
	       // Printing the name of directories and files
	       // with entire path
	       
			}
		}
}
