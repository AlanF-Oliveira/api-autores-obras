package com.alan.api_autores_obras.mapper;

import com.alan.api_autores_obras.dto.obra.ObraRequest;
import com.alan.api_autores_obras.dto.obra.ObraResponse;
import com.alan.api_autores_obras.dto.obra.ObraResumeResponse;
import com.alan.api_autores_obras.entity.Obra;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObraMapper {
    Obra toEntity (ObraRequest request);

    ObraResponse toResponse (Obra obra);

    ObraResumeResponse toResumoResponse (Obra obra);

    List<ObraResumeResponse> toResumoResponseList(List<Obra> obras);
}
