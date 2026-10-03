package com.example.playlist_api.repository;

import com.example.playlist.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByNome(String nome);
    boolean existsByNome(String nome);
}
