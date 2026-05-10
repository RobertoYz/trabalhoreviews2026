package com.trabalhoreviews2026.bibliotecajogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trabalhoreviews2026.bibliotecajogos.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
}