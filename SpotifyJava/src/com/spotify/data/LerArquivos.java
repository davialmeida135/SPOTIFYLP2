package com.spotify.data;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

//POSSIVELMENTE NÃO SERÁ USADO, DEVIDO A IMPLEMENTACAO DO SQLITE

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
