package com.spotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spotify.data.DataBase;
import com.spotify.model.Usuario;

//Classe que utilizará os métodos fornecidos pela classe Database
//Para adicionar, remover, diferenciar e modificar usuários
public class UsuarioDAO {

    /**
     * Insere um novo usuário no banco de dados.
     *
     * @param nome O nome completo do usuário (String).
     * @param usuario O nome de usuário usado para login (String).
     * @param senha A senha do usuário (String).
     * @param tipo O tipo de usuário (String, e.g., "administrador", "comum").
     * @param conn Uma conexão aberta com o banco de dados.
     */
    public static void novoUsuario(String nome, String usuario, String senha, String tipo, Connection conn) {
        String sql = "INSERT INTO usuarios(nome, tipo,username,password) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, tipo);
            pstmt.setString(3, usuario);
            pstmt.setString(4, senha);
            pstmt.executeUpdate();

            System.out.println("Usuário criado com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retorna o ID do usuário associado ao nome de usuário fornecido.
     *
     * @param usuario O nome de usuário do usuário (String).
     * @param conn Uma conexão aberta com o banco de dados.
     * @return O ID do usuário (int), ou -1 se o usuário não for encontrado.
     */
    public static int getUsuarioId(String usuario, Connection conn) {
        String sql = "SELECT usuarios.id\r\n"
                + "FROM usuarios\r\n"
                + "WHERE usuarios.username = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return -1;
            }

            return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Remove o usuário associado ao nome de usuário fornecido do banco de dados.
     *
     * @param usuario O nome de usuário do usuário (String).
     * @param conn Uma conexão aberta com o banco de dados.
     */
    public static void removerUsuario(String usuario, Connection conn) {
        String sql = "DELETE FROM usuarios WHERE username = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove o usuário associado ao ID fornecido do banco de dados.
     *
     * @param userId O ID do usuário (int).
     * @param conn Uma conexão aberta com o banco de dados.
     */
    public static void removerUsuario(int userId, Connection conn) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	
	
    public static int autenticar(String usuario, String senha, Connection conn) {
        String sql = "SELECT usuarios.id, usuarios.username, usuarios.password\r\n"
                + "FROM usuarios\r\n"
                + "WHERE usuarios.username = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Usuário não encontrado.");
                return -1;
            }

            if (rs.getString("password").equals(senha)) {
                System.out.println("Login bem sucedido!");
                return rs.getInt("id");
            } else {
                System.out.println("Senha incorreta");
                return -2;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }

    /**
     * Retorna um objeto Usuario com as informações do usuário associado ao ID fornecido.
     *
     * @param userId O ID do usuário (int).
     * @param conn Uma conexão aberta com o banco de dados.
     * @return Um objeto Usuario contendo as informações do usuário, ou null se o usuário não for encontrado.
     */
    public static Usuario getUsuario(int userId, Connection conn) {
        Usuario usuario = new Usuario();
        String sql = "SELECT usuarios.username,usuarios.nome,usuarios.tipo\r\n"
                + "FROM usuarios\r\n"
                + "WHERE usuarios.id = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            usuario.setId(userId);
            usuario.setNome(rs.getString("nome"));
            usuario.setTipo(rs.getString("tipo"));
            usuario.setUsuario(rs.getString("username"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}