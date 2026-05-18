package com.alan.api_autores_obras.service;

import com.alan.api_autores_obras.dto.obra.ObraRequest;
import com.alan.api_autores_obras.dto.obra.ObraResponse;
import com.alan.api_autores_obras.dto.obra.ObraResumeResponse;
import com.alan.api_autores_obras.dto.obra.ObraUpdateRequest;
import com.alan.api_autores_obras.entity.Autor;
import com.alan.api_autores_obras.entity.Obra;
import com.alan.api_autores_obras.mapper.ObraMapper;
import com.alan.api_autores_obras.repository.AutorRepository;
import com.alan.api_autores_obras.repository.ObraRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ObraService {
    private final ObraRepository obraRepository;
    private final AutorRepository autorRepository;
    private final ObraMapper mapper;

    private void validarDataPublicacaoOuExposicao(LocalDate dataPublicacao, LocalDate dataExposicao) {
        if (dataPublicacao == null && dataExposicao == null) {
            throw new ValidationException("Data de publicação ou data de exposição é obrigatória.");
        }
    }

    public ObraResponse cadastrarObra(ObraRequest request) {
        validarDataPublicacaoOuExposicao(request.getDataPublicacao(), request.getDataExposicao());
        Obra entity = mapper.toEntity(request);
        List<Autor> autores = autorRepository.findAllById(request.getAutorId());
        entity.setAutores(autores);
        obraRepository.save(entity);
        return mapper.toResponse(entity);
    }

    public ObraResumeResponse buscarObraPorId(Long id) {
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada."));
        return mapper.toResumoResponse(entity);
    }

    public List<ObraResumeResponse> mostrarTodasAsObras() {
        List<Obra> obras = obraRepository.findAll();
        return mapper.toResumoResponseList(obras);
    }

    public ObraResponse atualizarObraPorId(Long id, ObraUpdateRequest request) {
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada."));
        if (request.getAutorId() != null) {
            List<Autor> autores = autorRepository.findAllById(request.getAutorId());
            entity.setAutores(autores);
        }
        if (request.getNome() != null) entity.setNome(request.getNome());
        if (request.getDescricao() != null) entity.setDescricao(request.getDescricao());
        if (request.getDataExposicao() != null) entity.setDataExposicao(request.getDataExposicao());
        if (request.getDataPublicacao() != null) entity.setDataPublicacao(request.getDataPublicacao());
        obraRepository.save(entity);
        return mapper.toResponse(entity);
    }

    public void deletarObraPorId(Long id){
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada."));
        entity.setAutores(null);
        obraRepository.save(entity);
        obraRepository.delete(entity);
    }

}

