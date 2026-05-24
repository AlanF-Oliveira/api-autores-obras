package com.alan.api_autores_obras.entity;

import java.util.List;

public class AutorFixture {
    public static Autor build() {
        return Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .email("machado@assis.com")
                .paisDeOrigem("Brasil")
                .cpf("123.456.789-00")
                .obras(List.of())
                .build();
    }
}
