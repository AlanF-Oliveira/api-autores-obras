package com.alan.api_autores_obras.dto.obra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ObraRequest {
    @NotBlank
    private String nome;
    @Size(max = 240)
    private String descricao;
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;
    List<Long> autorId;
}
