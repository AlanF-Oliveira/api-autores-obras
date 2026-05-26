package com.alan.api_autores_obras.controller;

import com.alan.api_autores_obras.dto.obra.*;
import com.alan.api_autores_obras.entity.Obra;
import com.alan.api_autores_obras.entity.ObraFixture;
import com.alan.api_autores_obras.exception.GlobalExceptionHandler;
import com.alan.api_autores_obras.exception.ResourceNotFoundException;
import com.alan.api_autores_obras.service.ObraService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ObraControllerTest {

    @InjectMocks
    ObraController obraController;

    @Mock
    ObraService obraService;

    MockMvc mockMvc;

    Obra obra;
    ObraRequest obraRequest;
    ObraResponse obraResponse;
    ObraResumeResponse obraResumeResponse;
    ObraUpdateRequest obraUpdateRequest;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
        obra = ObraFixture.build();
        obraRequest = ObraRequestFixture.build();
        obraResponse = ObraResponseFixture.build();
        obraResumeResponse = ObraResumeResponseFixture.build();
        obraUpdateRequest = ObraUpdateRequestFixture.build();
        mockMvc = MockMvcBuilders
                .standaloneSetup(obraController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .alwaysDo(print())
                .build();
    }

    @Test
    void deveCadastrarObra() throws Exception {
        when(obraService.cadastrarObra(any(ObraRequest.class))).thenReturn(obraResponse);
        mockMvc.perform(post("/obra")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(obraRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Dom Casmurro"));
        verify(obraService).cadastrarObra(any(ObraRequest.class));
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void deveBuscarObraPorId() throws Exception {
        when(obraService.buscarObraPorId(1L)).thenReturn(obraResponse);
        mockMvc.perform(get("/obra/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Dom Casmurro"));
        verify(obraService).buscarObraPorId(1L);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void naoDeveBuscarObraPorIdInexistente() throws Exception {
        when(obraService.buscarObraPorId(99L))
                .thenThrow(new ResourceNotFoundException("Obra não encontrada."));
        mockMvc.perform(get("/obra/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(obraService).buscarObraPorId(99L);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void deveMostrarTodasAsObras() throws Exception {
        when(obraService.mostrarTodasAsObras()).thenReturn(List.of(obraResumeResponse));
        mockMvc.perform(get("/obra")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Dom Casmurro"));
        verify(obraService).mostrarTodasAsObras();
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void deveAtualizarObra() throws Exception {
        when(obraService.atualizarObraPorId(eq(1L), any(ObraUpdateRequest.class))).thenReturn(obraResponse);
        mockMvc.perform(patch("/obra/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(obraUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Dom Casmurro"));
        verify(obraService).atualizarObraPorId(eq(1L), any(ObraUpdateRequest.class));
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void deveDeletarObraPorId() throws Exception {
        doNothing().when(obraService).deletarObraPorId(1L);
        mockMvc.perform(delete("/obra/1"))
                .andExpect(status().isNoContent());
        verify(obraService).deletarObraPorId(1L);
        verifyNoMoreInteractions(obraService);
    }
}