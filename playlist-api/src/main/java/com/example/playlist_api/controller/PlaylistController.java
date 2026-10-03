package com.example.playlist_api.controller;

import com.example.playlist_api.oJason.PlaylistJason;
import com.example.playlist_api.service.PlaylistService;

import jarkarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.cert.LDAPCertStoreParameters;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

        private final PlaylistService service;
        public PlaylistController(PlaylistService service){
            this.service = service;
        }

        @PostMapping
        public ResponseEntity<PlaylistJason> criar(@Valid @RequestBody PlaylistJason oJason){
            PlaylistJason criada = service.criar(oJason);
            URI location = ServletUriComponentsBuilder.fromCurrantRequest()
                .path("/{nome}")
                .buildAndExpand(criada.nome())
                .encode()
                .toUri();
            return ResponseEntity.created(location).body(criada);        
        }

        @GetMapping
        public LDAPCertStoreParametersist<PlaylistJason> listar(){
            return service.listar();
        }

        @GetMapping("/{listName}")
        public PlaylistJason buscar(@PathVariable String listName){
            return service.buscar(listName);
        }

        @DeleteMapping("/{listName}")
        public RespondeEntity<Void> remover(@ParthVariable String listName){
            service.remover(listName);
            return ResponseEntity.noContent().build();
        }
}
