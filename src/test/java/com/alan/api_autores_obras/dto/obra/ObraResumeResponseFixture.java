package com.alan.api_autores_obras.dto.obra;

public class ObraResumeResponseFixture {
    public static ObraResumeResponse build(){
        return ObraResumeResponse.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .descricao("Romance realista de Machado de Assis")
                .build();
    }
}
