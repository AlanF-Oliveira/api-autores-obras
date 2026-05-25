package com.alan.api_autores_obras.service;

import com.alan.api_autores_obras.dto.autor.*;
import com.alan.api_autores_obras.entity.Autor;
import com.alan.api_autores_obras.entity.AutorFixture;
import com.alan.api_autores_obras.exception.ResourceNotFoundException;
import com.alan.api_autores_obras.mapper.AutorMapper;
import com.alan.api_autores_obras.repository.AutorRepository;
import com.alan.api_autores_obras.repository.ObraRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @InjectMocks
    AutorService autorService;

    @Mock
    ObraRepository obraRepository;

    @Mock
    AutorRepository autorRepository;

    @Mock
    AutorMapper mapper;

    Autor autor;
    AutorRequest autorRequest;
    AutorResponse autorResponse;
    AutorResumeResponse autorResumeResponse;
    AutorUpdateRequest autorUpdateRequest;

    @BeforeEach
    void setUp() {
        autor = AutorFixture.build();
        autorRequest = AutorRequestFixture.build();
        autorResponse = AutorResponseFixture.build();
        autorResumeResponse = AutorResumeResponseFixture.build();
        autorUpdateRequest = AutorUpdateRequestFixture.build();
    }

    @Test
    void deveValidarCpfAutorBrasileiro(){
        AutorRequest requestSemCpf = AutorRequestFixture.build();
        requestSemCpf.setCpf(null);
        ValidationException e = assertThrows(ValidationException.class,
                () -> autorService.cadastrarAutor(requestSemCpf));
        assertThat(e.getMessage(), is("CPF obrigatório para autores do Brasil"));
        verifyNoInteractions(autorRepository, mapper);
    }

    @Test
    void deveCadastrarAutor(){
        when(mapper.toEntity(autorRequest)).thenReturn(autor);
        when(obraRepository.findAllById(autorRequest.getObrasId())).thenReturn(List.of());
        when(autorRepository.save(autor)).thenReturn(autor);
        when(mapper.toResponse(autor)).thenReturn(autorResponse);
        AutorResponse response = autorService.cadastrarAutor(autorRequest);
        assertEquals(autorResponse, response);
        verify(mapper).toEntity(autorRequest);
        verify(obraRepository).findAllById(autorRequest.getObrasId());
        verify(autorRepository).save(autor);
        verify(mapper).toResponse(autor);
        verifyNoMoreInteractions(mapper, autorRepository, obraRepository);
    }

    @Test
    void deveBuscarAutorPorId(){
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(mapper.toResponse(autor)).thenReturn(autorResponse);
        AutorResponse responseById = autorService.buscarAutorPorId(1L);
        assertEquals(responseById, autorResponse);
        verify(autorRepository).findById(1L);
        verify(mapper).toResponse(autor);
        verifyNoMoreInteractions(autorRepository, mapper);
    }

    @Test
    void naoDeveBuscarAutorPorIdInexistente(){
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> autorService.buscarAutorPorId(1L));
        assertThat(e.getMessage(),is("Autor não encontrado"));
        verify(autorRepository).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void deveMostrarTodosOsAutores(){
        when(autorRepository.findAll()).thenReturn(List.of(autor));
        when(mapper.toResumoResponseList(List.of(autor))).thenReturn(List.of(autorResumeResponse));
        List<AutorResumeResponse> resumeResponse = autorService.mostrarTodosOsAutores();
        assertEquals(List.of(autorResumeResponse), resumeResponse);
        verify(autorRepository).findAll();
        verify(mapper).toResumoResponseList(List.of(autor));
        verifyNoMoreInteractions(mapper, autorRepository);
    }

    @Test
    void deveAtualizarAutor(){
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(obraRepository.findAllById(autorUpdateRequest.getObrasId())).thenReturn(List.of());
        when(autorRepository.save(autor)).thenReturn(autor);
        when(mapper.toResponse(autor)).thenReturn(autorResponse);
        AutorResponse autorResponseTest = autorService.atualizarAutor(1L, autorUpdateRequest);
        assertEquals(autorResponse, autorResponseTest);
        verify(autorRepository).findById(1L);
        verify(obraRepository).findAllById(autorUpdateRequest.getObrasId());
        verify(autorRepository).save(autor);
        verify(mapper).toResponse(autor);
        verifyNoMoreInteractions(autorRepository, mapper, obraRepository);
    }

    @Test
    void naoDeveAtualizarAutorPorIdInexistente(){
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> autorService.atualizarAutor(1L, autorUpdateRequest));
        assertThat(e.getMessage(),is("Autor não encontrado"));
        verify(autorRepository).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void deveDeletarAutorPorId(){
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(autorRepository.save(autor)).thenReturn(autor);
        autorService.deletarAutorPorId(1L);
        verify(autorRepository).findById(1L);
        verify(autorRepository).save(autor);
        verify(autorRepository).delete(autor);
        verifyNoMoreInteractions(autorRepository, mapper);
    }

    @Test
    void naoDeveDeletarAutorPorIdInexistente(){
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> autorService.deletarAutorPorId(1L));
        assertThat(e.getMessage(), is("Autor não encontrado"));
        verify(autorRepository).findById(1L);
    }

}
