package com.example.playlist_api.model;

import jakarta.persistence.*;

@Entity
public class Musica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;
    private String artista;
    private String album;
    private Integer ano;
    private String genero;

    public Musica() {}

    public Musica(String titulo, String artista, String album, Integer ano, String genero) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.ano = ano;
        this.genero = genero;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }
    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

}
