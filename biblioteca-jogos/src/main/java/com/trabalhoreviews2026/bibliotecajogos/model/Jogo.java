package com.trabalhoreviews2026.bibliotecajogos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "jogos")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do jogo é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Tipo do jogo é obrigatório")
    @Column(nullable = false)
    private String tipo;

    @Min(value = 0, message = "Nota mínima é 0")
    @Max(value = 10, message = "Nota máxima é 10")
    @Column(nullable = false)
    private Integer nota;

    @Column(length = 1000)
    private String review;

    public Jogo() {}

    public Jogo(String nome, String tipo, Integer nota, String review) {
        this.nome = nome;
        this.tipo = tipo;
        this.nota = nota;
        this.review = review;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
}