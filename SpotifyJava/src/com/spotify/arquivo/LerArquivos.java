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
	 public static Connection connect(){
	        Connection conn = null;  
	        try {  
	            // db parameters  
	            String url = "jdbc:sqlite:storage/users/teste.db"; 
	            // create a connection to the database  
	            conn = DriverManager.getConnection(url);  
	              
	            System.out.println("Connection to SQLite has been established.");  
	            return conn;
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	            return null;
	        }
	        
	    } 
	 
	public static void createNewDatabase(String fileName) {  
	    	   
	        String url = "jdbc:sqlite:storage/users/" + fileName;  
	   
	        try {  
	            Connection conn = DriverManager.getConnection(url);  
	            if (conn != null) {  
	                DatabaseMetaData meta = conn.getMetaData();  
	                System.out.println("The driver name is " + meta.getDriverName());  
	                System.out.println("A new database has been created.");  
	            }  
	   
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	        
	    }  
	public static void createNewTable() {  
	        // SQLite connection string  
	        String url = "jdbc:sqlite:storage/users/teste.db";  
	          
	        // SQL statement for creating a new table  
	        String sql = "CREATE TABLE IF NOT EXISTS employees (\n"  
	                + " id integer PRIMARY KEY,\n"  
	                + " name text NOT NULL,\n"  
	                + " capacity real\n"  
	                + ");";  
	          
	        try{  
	            Connection conn = DriverManager.getConnection(url);  
	            Statement stmt = conn.createStatement();  
	            stmt.execute(sql);  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }  
	   
	   
	  static public void insert(String name, double capacity) {  
	        String sql = "INSERT INTO employees(name, capacity) VALUES(?,?)";  
	   
	        try{  
	            Connection conn = connect();  
	            PreparedStatement pstmt = conn.prepareStatement(sql);  
	            pstmt.setString(1, name);  
	            pstmt.setDouble(2, capacity);  
	            pstmt.executeUpdate();  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }
	  
	  public static void selectAll(){  
	        String sql = "SELECT * FROM employees";  
	          
	        try {  
	            Connection conn = connect();  
	            Statement stmt  = conn.createStatement();  
	            ResultSet rs    = stmt.executeQuery(sql);  
	              
	            // loop through the result set  
	            while (rs.next()) {  
	                System.out.println(rs.getInt("id") +  "\t" +   
	                                   rs.getString("name") + "\t" +  
	                                   rs.getDouble("capacity"));  
	            }  
	        } catch (SQLException e) {  
	            System.out.println(e.getMessage());  
	        }  
	    }  
	   
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
	 
	
	
	
	public static void main(String[] args) {
		//createNewDatabase("teste.db");
		//connect();
		//createNewTable();
		//insert("Jonas",12);
		//insert("Davi",41);
		//insert("Gustavo",30);
		selectAll();
	}
}
