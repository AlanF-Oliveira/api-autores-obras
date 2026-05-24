package com.alan.api_autores_obras.entity;

import java.time.LocalDate;
import java.util.List;

public class ObraFixture {
    public static Obra build(){
        return Obra.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .descricao("Romance realista de Machado de Assis")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .autores(List.of())
                .build();
    }
}
