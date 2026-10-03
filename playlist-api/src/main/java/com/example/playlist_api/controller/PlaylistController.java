package com.example.playlist_api.controller;

import com.example.playlist_api.oJson.PlaylistJson;
import com.example.playlist_api.service.PlaylistService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

        private final PlaylistService service;
        public PlaylistController(PlaylistService service){
            this.service = service;
        }

        @PostMapping
        public ResponseEntity<PlaylistJson> criar(@Valid @RequestBody PlaylistJson oJson) {
        PlaylistJson criada = service.criar(oJson);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{nome}")
                .buildAndExpand(criada.nome())
                .encode()
                .toUri();
        return ResponseEntity.created(location).body(criada);
    }

        @GetMapping
        public List<PlaylistJson> listar() {
        return service.listar();
    }

        @GetMapping("/{listName}")
        public PlaylistJson buscar(@PathVariable String listName){
            return service.buscar(listName);
        }

        @DeleteMapping("/{listName}")
        public ResponseEntity<Void> remover(@PathVariable String listName) {
        service.remover(listName);
        return ResponseEntity.noContent().build();
    }
}
