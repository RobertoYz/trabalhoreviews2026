package com.trabalhoreviews2026.bibliotecajogos.service;

import org.springframework.stereotype.Service;
import com.trabalhoreviews2026.bibliotecajogos.model.Jogo;
import com.trabalhoreviews2026.bibliotecajogos.repository.JogoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class JogoService {

    private final JogoRepository jogoRepository;

    public JogoService(JogoRepository jogoRepository) {
        this.jogoRepository = jogoRepository;
    }

    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }

    public Optional<Jogo> buscarPorId(Long id) {
        return jogoRepository.findById(id);
    }

    public Jogo salvar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public boolean deletar(Long id) {
        if (jogoRepository.existsById(id)) {
            jogoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}