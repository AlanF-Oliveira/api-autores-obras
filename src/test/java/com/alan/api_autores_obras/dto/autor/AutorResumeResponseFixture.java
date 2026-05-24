package com.alan.api_autores_obras.dto.autor;

import java.time.LocalDate;

public class AutorResumeResponseFixture {
    public static AutorResumeResponse build(){
        return AutorResumeResponse.builder()
                .id(1L)
                .nome("Machado de Assis")
                .dataNascimento(LocalDate.of(1839, 6, 21))
                .paisDeOrigem("Brasil")
                .build();
    }
}
