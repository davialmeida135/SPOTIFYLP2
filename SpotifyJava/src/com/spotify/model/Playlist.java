package com.spotify.model;

import java.util.ArrayList;

/**
 * Representa uma playlist.
 *
 */
public class Playlist {

    /**
     * O ID da playlist.
     */
    private int id;

    /**
     * O nome da playlist.
     */
    private String nome;

    /**
     * A descrição da playlist.
     */
    private String descricao;

    /**
     * O ID do usuário proprietário da playlist.
     */
    private int userId;

    /**
     * Uma lista de IDs das músicas da playlist.
     */
    private ArrayList<Integer> musicas;

    /**
     * Uma lista de caminhos para as músicas da playlist.
     */
    private ArrayList<String> paths;

    /**
     * Retorna o ID da playlist.
     *
     * @return O ID da playlist.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da playlist.
     *
     * @param id O novo ID da playlist.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome da playlist.
     *
     * @return O nome da playlist.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da playlist.
     *
     * @param nome O novo nome da playlist.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição da playlist.
     *
     * @return A descrição da playlist.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da playlist.
     *
     * @param descricao A nova descrição da playlist.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a lista de IDs das músicas da playlist.
     *
     * @return A lista de IDs das músicas.
     */
    public ArrayList<Integer> getMusicas() {
        return musicas;
    }

    /**
     * Define a lista de IDs das músicas da playlist.
     *
     * @param musicas A nova lista de IDs das músicas.
     */
    public void setMusicas(ArrayList<Integer> musicas) {
        this.musicas = musicas;
    }

    /**
     * Retorna a lista de caminhos para as músicas da playlist.
     *
     * @return A lista de caminhos.
     */
    public ArrayList<String> getPaths() {
        return paths;
    }

    /**
     * Define a lista de caminhos para as músicas da playlist.
     *
     * @param paths A nova lista de caminhos.
     */
    public void setPaths(ArrayList<String> paths) {
        this.paths = paths;
    }

    /**
     * Retorna o ID do usuário proprietário da playlist.
     *
     * @return O ID do usuário.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Define o ID do usuário proprietário da playlist.
     *
     * @param userId O novo ID do usuário.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}