package com.alan.api_autores_obras.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(length = 240)
    private String descricao;
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;
    @ManyToMany(mappedBy = "obras")
    List<Autor> autores;
}
