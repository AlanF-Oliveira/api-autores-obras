package com.alan.api_autores_obras.dto.autor;

import com.alan.api_autores_obras.enums.Sexo;

import java.time.LocalDate;
import java.util.List;

public class AutorResponseFixture {
    public static AutorResponse build(){
        return AutorResponse.builder()
                .nome("Machado de Assis")
                .sexo(Sexo.MASCULINO)
                .email("machado@assis.com")
                .dataNascimento(LocalDate.of(1839, 6, 21))
                .paisDeOrigem("Brasil")
                .cpf("123.456.789-00")
                .obras(List.of())
                .build();

    }
}
