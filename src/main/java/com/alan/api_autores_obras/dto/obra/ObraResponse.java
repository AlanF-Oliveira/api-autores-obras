package com.alan.api_autores_obras.dto.obra;

import com.alan.api_autores_obras.dto.autor.AutorResumoResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ObraResponse {
    private String nome;
    private String descricao;
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;
    List<AutorResumoResponse> autores;
}
