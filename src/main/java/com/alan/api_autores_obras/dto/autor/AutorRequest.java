package com.alan.api_autores_obras.dto.autor;

import com.alan.api_autores_obras.enums.Sexo;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AutorRequest {
    private String nome;
    private Sexo sexo;
    private String email;
    private LocalDate dataNascimento;
    private String paisDeOrigem;
    private String cpf;
    private List<Long> obrasId;
}
