package com.alan.api_autores_obras.dto.obra;

import java.time.LocalDate;
import java.util.List;

public class ObraRequestFixture {
    public static ObraRequest build(){
        return ObraRequest.builder()
                .nome("Dom Casmurro")
                .descricao("Romance realista de Machado de Assis")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .dataExposicao(LocalDate.of(2026, 6, 5))
                .autorId(List.of())
                .build();
    }

}
