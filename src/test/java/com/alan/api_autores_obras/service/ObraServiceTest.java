package com.alan.api_autores_obras.service;

import com.alan.api_autores_obras.dto.obra.*;
import com.alan.api_autores_obras.entity.Obra;
import com.alan.api_autores_obras.entity.ObraFixture;
import com.alan.api_autores_obras.exception.ResourceNotFoundException;
import com.alan.api_autores_obras.mapper.ObraMapper;
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

public class ObraServiceTest {
    @InjectMocks
    ObraService obraService;

    @Mock
    ObraRepository obraRepository;

    @Mock
    AutorRepository autorRepository;

    @Mock
    ObraMapper mapper;

    Obra obra;
    ObraRequest obraRequest;
    ObraResponse obraResponse;
    ObraResumeResponse obraResumeResponse;
    ObraUpdateRequest obraUpdateRequest;

    @BeforeEach
    void setup() {
        obra = ObraFixture.build();
        obraRequest = ObraRequestFixture.build();
        obraResponse = ObraResponseFixture.build();
        obraResumeResponse = ObraResumeResponseFixture.build();
        obraUpdateRequest = ObraUpdateRequestFixture.build();
    }

    @Test
    void naoDeveCadastrarObraSemDataPublicacaoEExposicao() {
        ObraRequest requestSemData = ObraRequestFixture.build();
        requestSemData.setDataPublicacao(null);
        requestSemData.setDataExposicao(null);
        ValidationException e = assertThrows(ValidationException.class,
                () -> obraService.cadastrarObra(requestSemData));
        assertThat(e.getMessage(), is("Data de publicação ou data de exposição é obrigatória."));
        verifyNoInteractions(obraRepository, mapper, autorRepository);
    }

    @Test
    void deveCadastrarObra() {
        when(mapper.toEntity(obraRequest)).thenReturn(obra);
        when(autorRepository.findAllById(obraRequest.getAutorId())).thenReturn(List.of());
        when(obraRepository.save(obra)).thenReturn(obra);
        when(autorRepository.saveAll(List.of())).thenReturn(List.of());
        when(mapper.toResponse(obra)).thenReturn(obraResponse);
        ObraResponse response = obraService.cadastrarObra(obraRequest);
        assertEquals(obraResponse, response);
        verify(mapper).toEntity(obraRequest);
        verify(autorRepository).findAllById(obraRequest.getAutorId());
        verify(obraRepository).save(obra);
        verify(autorRepository).saveAll(List.of());
        verify(mapper).toResponse(obra);
        verifyNoMoreInteractions(mapper, obraRepository, autorRepository);
    }

    @Test
    void deveBuscarObraPorId() {
        when(obraRepository.findById(1L)).thenReturn(Optional.of(obra));
        when(mapper.toResponse(obra)).thenReturn(obraResponse);
        ObraResponse response = obraService.buscarObraPorId(1L);
        assertEquals(obraResponse, response);
        verify(obraRepository).findById(1L);
        verify(mapper).toResponse(obra);
        verifyNoMoreInteractions(obraRepository, mapper);
    }

    @Test
    void naoDeveBuscarObraPorIdInexistente() {
        when(obraRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> obraService.buscarObraPorId(1L));
        assertThat(e.getMessage(), is("Obra não encontrada."));
        verify(obraRepository).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void deveMostrarTodasAsObras() {
        when(obraRepository.findAll()).thenReturn(List.of(obra));
        when(mapper.toResumoResponseList(List.of(obra))).thenReturn(List.of(obraResumeResponse));
        List<ObraResumeResponse> response = obraService.mostrarTodasAsObras();
        assertEquals(List.of(obraResumeResponse), response);
        verify(obraRepository).findAll();
        verify(mapper).toResumoResponseList(List.of(obra));
        verifyNoMoreInteractions(obraRepository, mapper);
    }

    @Test
    void deveAtualizarObraPorId() {
        when(obraRepository.findById(1L)).thenReturn(Optional.of(obra));
        when(autorRepository.findAllById(obraUpdateRequest.getAutorId())).thenReturn(List.of());
        when(autorRepository.saveAll(List.of())).thenReturn(List.of());
        when(obraRepository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(obraResponse);
        ObraResponse response = obraService.atualizarObraPorId(1L, obraUpdateRequest);
        assertEquals(obraResponse, response);
        verify(obraRepository).findById(1L);
        verify(autorRepository).findAllById(obraUpdateRequest.getAutorId());
        verify(autorRepository).saveAll(List.of());
        verify(obraRepository).save(obra);
        verify(mapper).toResponse(obra);
        verifyNoMoreInteractions(obraRepository, mapper, autorRepository);
    }

    @Test
    void naoDeveAtualizarObraPorIdInexistente() {
        when(obraRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> obraService.atualizarObraPorId(1L, obraUpdateRequest));
        assertThat(e.getMessage(), is("Obra não encontrada."));
        verify(obraRepository).findById(1L);
        verifyNoInteractions(mapper, autorRepository);
    }

    @Test
    void deveDeletarObraPorId() {
        when(obraRepository.findById(1L)).thenReturn(Optional.of(obra));
        when(obraRepository.save(obra)).thenReturn(obra);
        obraService.deletarObraPorId(1L);
        verify(obraRepository).findById(1L);
        verify(obraRepository).save(obra);
        verify(obraRepository).delete(obra);
        verifyNoMoreInteractions(obraRepository, mapper);
    }

    @Test
    void naoDeveDeletarObraPorIdInexistente() {
        when(obraRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> obraService.deletarObraPorId(1L));
        assertThat(e.getMessage(), is("Obra não encontrada."));
        verify(obraRepository).findById(1L);
        verifyNoMoreInteractions(obraRepository, mapper);
    }
}
