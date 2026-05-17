package com.alan.api_autores_obras.dto.obra;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ObraResumeResponse {
    private Long id;
    private String nome;
}
