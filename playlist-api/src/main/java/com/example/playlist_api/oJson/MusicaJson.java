package com.example.playlist_api.oJson;

import jakarta.validation.constraints.NotBlank;

public record MusicaJson(
        @NotBlank String titulo,
        String artista,
        String album,
        Integer ano,
        String genero) {
}