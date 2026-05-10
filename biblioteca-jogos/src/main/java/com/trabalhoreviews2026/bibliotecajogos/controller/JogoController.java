package com.trabalhoreviews2026.bibliotecajogos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trabalhoreviews2026.bibliotecajogos.model.Jogo;
import com.trabalhoreviews2026.bibliotecajogos.service.JogoService;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogos")
@CrossOrigin(origins = "*")
public class JogoController {

    private final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @GetMapping
    public ResponseEntity<List<Jogo>> listarJogos() {
        return ResponseEntity.ok(jogoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarJogo(@PathVariable Long id) {
        Optional<Jogo> jogo = jogoService.buscarPorId(id);
        return jogo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Jogo> criarJogo(@Valid @RequestBody Jogo jogo) {
        Jogo novoJogo = jogoService.salvar(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoJogo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogo> atualizarJogo(@PathVariable Long id, @Valid @RequestBody Jogo jogoAtualizado) {
        Optional<Jogo> jogoExistente = jogoService.buscarPorId(id);

        if (jogoExistente.isPresent()) {
            jogoAtualizado.setId(id);
            Jogo jogoSalvo = jogoService.salvar(jogoAtualizado);
            return ResponseEntity.ok(jogoSalvo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
        if (jogoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}