package com.alan.api_autores_obras.dto.obra;

import java.time.LocalDate;
import java.util.List;

public class ObraResponseFixture {
    public static ObraResponse build(){
        return ObraResponse.builder()
                .nome("Dom Casmurro")
                .descricao("Romance realista de Machado de Assis")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .dataExposicao(LocalDate.of(2026, 6, 5))
                .autores(List.of())
                .build();

    }
}
