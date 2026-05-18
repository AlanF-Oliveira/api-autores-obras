package com.alan.api_autores_obras.service;

import com.alan.api_autores_obras.dto.autor.AutorRequest;
import com.alan.api_autores_obras.dto.autor.AutorResponse;
import com.alan.api_autores_obras.dto.autor.AutorResumeResponse;
import com.alan.api_autores_obras.dto.autor.AutorUpdateRequest;
import com.alan.api_autores_obras.entity.Autor;
import com.alan.api_autores_obras.entity.Obra;
import com.alan.api_autores_obras.mapper.AutorMapper;
import com.alan.api_autores_obras.repository.AutorRepository;
import com.alan.api_autores_obras.repository.ObraRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;
    private final ObraRepository obraRepository;
    private final AutorMapper mapper;

    public AutorResponse criarAutor(AutorRequest request) {
        if (("Brasil".equalsIgnoreCase(request.getPaisDeOrigem()) || "Brazil".equalsIgnoreCase(request.getPaisDeOrigem())) && request.getCpf() == null) {
            throw new ValidationException("CPF obrigatório para autores do Brasil");
        }
        Autor entity = mapper.toEntity(request);
        List<Obra> obras = obraRepository.findAllById(request.getObrasId());
        entity.setObras(obras);
        Autor savedAutor = autorRepository.save(entity);
        return mapper.toResponse(savedAutor);
    }

    public AutorResumeResponse mostrarAutorPorId(Long id){
        Autor entity = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        return mapper.toResumoResponse(entity);
    }

    public List<AutorResumeResponse> mostrarTodosOsAutores(){
        List <Autor> autores = autorRepository.findAll();
        return mapper.toResumoResponseList(autores);
    }

    public AutorResponse atualizarAutor(Long id, AutorUpdateRequest request){
        Autor entity = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        if (("Brasil".equalsIgnoreCase(request.getPaisDeOrigem()) || "Brazil".equalsIgnoreCase(request.getPaisDeOrigem())) && request.getCpf() == null) {
            throw new ValidationException("CPF obrigatório para autores do Brasil");
        }
        if (request.getObrasId() != null) {
            List<Obra> obras = obraRepository.findAllById(request.getObrasId());
            entity.setObras(obras);
        }
        if(request.getNome() != null) entity.setNome(request.getNome());
        if(request.getEmail() != null)entity.setEmail(request.getEmail());
        if(request.getCpf() != null) entity.setCpf(request.getCpf());
        if(request.getSexo() != null)entity.setSexo(request.getSexo());
        if(request.getDataNascimento() != null) entity.setDataNascimento(request.getDataNascimento());
        if(request.getPaisDeOrigem() != null)entity.setPaisDeOrigem(request.getPaisDeOrigem());
        Autor savedAutor = autorRepository.save(entity);
        return mapper.toResponse(savedAutor);
    }

    public void deletarAutorPorId(Long id){
        Autor entity = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        entity.setObras(null);
        autorRepository.save(entity);
        autorRepository.delete(entity);
    }
}
