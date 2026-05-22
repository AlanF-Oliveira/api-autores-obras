package com.alan.api_autores_obras.mapper;

import com.alan.api_autores_obras.dto.auth.CadastroRequest;
import com.alan.api_autores_obras.entity.Usuario;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    Usuario toEntity(CadastroRequest request);

}
