package com.example.playlist_api.service;

import com.example.playlist_api.model.Musica;
import com.example.playlist_api.model.Playlist;
import com.example.playlist_api.oJson.MusicaJson;
import com.example.playlist_api.oJson.PlaylistJson;
import com.example.playlist_api.repository.PlaylistRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlaylistService {
    private final PlaylistRepository repository;

    public PlaylistService(PlaylistRepository repository){
        this.repository = repository;
    }

    @Transactional
    public PlaylistJson criar (PlaylistJson oJson){
        if(oJson == null || oJson.nome()== null || oJson.nome().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome da lista é obrigatório");
        }
        if (repository.existsByNome(oJson.nome())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma lista com esse nome");
            
        }
        Playlist playlist = new Playlist();
        playlist.setNome(oJson.nome());
        playlist.setDescricao(oJson.descricao());
        if (oJson.musicas() != null) {
            oJson.musicas().forEach(m -> playlist.getMusicas().add(
                new Musica(m.titulo(), m.artista(), m.album(), m.ano(), m.genero())));
        }
        return toJson(repository.save(playlist));
    }

    @Transactional(readOnly = true)
    public List<PlaylistJson> listar() {
        return repository.findAll().stream().map(this::toJson).toList();
    }

    @Transactional(readOnly = true)
    public PlaylistJson buscar(String nome) {
        return toJson(buscarEntidade(nome));
    }

    @Transactional
    public void remover(String nome) {
        repository.delete(buscarEntidade(nome));
    }

    private Playlist buscarEntidade(String nome) {
        return repository.findByNome(nome).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista não encontrada: " + nome));
    }

    private PlaylistJson toJson(Playlist p) {
        List<MusicaJson> musicas = p.getMusicas().stream()
                .map(m -> new MusicaJson(m.getTitulo(), m.getArtista(), m.getAlbum(), m.getAno(), m.getGenero()))
                .toList();
        return new PlaylistJson(p.getNome(), p.getDescricao(), musicas);
    }

}
