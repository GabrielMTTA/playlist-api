package com.example.playlist_api.oJson;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record PlaylistJson(
        @NotBlank String nome,
        @JsonAlias("descrição") String descricao,
        @Valid @JsonAlias("músicas") List<MusicaJson> musicas) {

}
