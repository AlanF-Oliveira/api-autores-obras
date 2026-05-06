package com.alan.api_autores_obras.dto.autor;

import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AutorResumoResponse {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String paisDeOrigem;
}
