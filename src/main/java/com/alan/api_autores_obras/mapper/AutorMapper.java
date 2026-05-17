package com.alan.api_autores_obras.mapper;

import com.alan.api_autores_obras.dto.autor.AutorRequest;
import com.alan.api_autores_obras.dto.autor.AutorResponse;
import com.alan.api_autores_obras.dto.autor.AutorResumeResponse;
import com.alan.api_autores_obras.entity.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(target = "obras", ignore = true)
    Autor toEntity(AutorRequest request);

    AutorResponse toResponse(Autor autor);

    AutorResumeResponse toResumoResponse(Autor autor);

    List<AutorResumeResponse> toResumoResponseList(List<Autor> autores);
}
