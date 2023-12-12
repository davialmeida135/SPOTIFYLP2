package com.spotify.model;

import java.util.ArrayList;

/**
 * Esta classe representa uma faixa musical no aplicativo Spotify.
 */
public class Musica {

    /**
     * Identificador exclusivo para a faixa musical.
     */
    private int id;

    /**
     * titulo da faixa musical.
     */
    private String titulo;

    /**
     * Caminho para o arquivo de Musica no sistema.
     */
    private String path;

    /**
     * ID do usuário que possui a faixa musical.
     */
    private int userId;

    /**
     * Construtor para a classe Musica.
     * @param titulo titulo da faixa musical.
     * @param path Caminho para o arquivo de Musica no sistema.
     */
    public Musica(String titulo, String path) {
        super();
        this.titulo = titulo;
        this.path = path;
    }

    /**
     * Construtor vazio para a classe Musica.
     */
    public Musica() {
        // TODO: Adicionar implementação para este construtor
    }

    /**
     * Obtém o ID da faixa musical.
     * @return ID da faixa musical.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da faixa musical.
     * @param id ID da faixa musical.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o titulo da faixa musical.
     * @return titulo da faixa musical.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o titulo da faixa musical.
     * @param titulo titulo da faixa musical.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtém o caminho para o arquivo de Musica no sistema.
     * @return Caminho para o arquivo de Musica no sistema.
     */
    public String getPath() {
        return path;
    }

    /**
     * Define o caminho para o arquivo de Musica no sistema.
     * @param path Caminho para o arquivo de Musica no sistema.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Obtém o ID do usuário proprietário da faixa musical.
     * @return ID do usuário proprietário da faixa musical.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Define o ID do usuário proprietário da faixa musical.
     * @param userId ID do usuário proprietário da faixa musical.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}