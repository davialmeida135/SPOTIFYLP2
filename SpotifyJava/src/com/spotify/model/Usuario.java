package com.spotify.model;

/**
 * Esta classe representa um usuário do sistema Spotify.
 *
 */
public class Usuario {

    /**
     * O nome do usuário.
     */
    private String nome;

    /**
     * O ID único do usuário.
     */
    private int id;

    /**
     * O nome de usuário usado para login.
     */
    private String usuario;

    /**
     * A senha do usuário.
     *
     * **Atenção:** A senha deve ser armazenada de forma segura.
     */
    private String senha;

    /**
     * O tipo de usuário.
     */
    private String tipo;

    /**
     * Retorna o nome do usuário.
     *
     * @return O nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     *
     * @param nome O nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o ID do usuário.
     *
     * @return O ID do usuário.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do usuário.
     *
     * @param id O ID do usuário.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome de usuário do usuário.
     *
     * @return O nome de usuário do usuário.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Define o nome de usuário do usuário.
     *
     * @param usuario O nome de usuário do usuário.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna a senha do usuário.
     *
     * @return A senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     *
     * **Atenção:** A senha deve ser armazenada de forma segura.
     *
     * @param senha A senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna o tipo de usuário.
     *
     * @return O tipo de usuário.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo de usuário.
     *
     * @param tipo O tipo de usuário.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}