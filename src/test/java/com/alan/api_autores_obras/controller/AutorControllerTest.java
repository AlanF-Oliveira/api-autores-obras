package com.alan.api_autores_obras.controller;

import com.alan.api_autores_obras.dto.autor.*;
import com.alan.api_autores_obras.entity.Autor;
import com.alan.api_autores_obras.entity.AutorFixture;
import com.alan.api_autores_obras.exception.GlobalExceptionHandler;
import com.alan.api_autores_obras.service.AutorService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

public class AutorControllerTest {
    @InjectMocks
    AutorController autorController;

    @Mock
    AutorService autorService;

    MockMvc mockMvc;

    Autor autor;
    AutorRequest autorRequest;
    AutorResponse autorResponse;
    AutorResumeResponse autorResumeResponse;
    AutorUpdateRequest autorUpdateRequest;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
        autor = AutorFixture.build();
        autorRequest = AutorRequestFixture.build();
        autorResponse = AutorResponseFixture.build();
        autorResumeResponse = AutorResumeResponseFixture.build();
        autorUpdateRequest = AutorUpdateRequestFixture.build();
        mockMvc = MockMvcBuilders
                .standaloneSetup(autorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .alwaysDo(print())
                .build();
    }

    @Test
    void deveCadastrarAutor() throws Exception {
        when(autorService.cadastrarAutor(any(AutorRequest.class))).thenReturn(autorResponse);
        mockMvc.perform(post("/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autorRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Machado de Assis"))
                .andExpect(jsonPath("$.email").value("machado@assis.com"));
        verify(autorService).cadastrarAutor(any(AutorRequest.class));
        verifyNoMoreInteractions(autorService);
    }

    @Test
    void deveBuscarAutorPorId() throws Exception {
        when(autorService.buscarAutorPorId(1L)).thenReturn(autorResponse);
        mockMvc.perform(get("/autor/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Machado de Assis"));
        verify(autorService).buscarAutorPorId(1L);
        verifyNoMoreInteractions(autorService);
    }

    @Test
    void deveMostrarTodosOsAutores() throws Exception {
        when(autorService.mostrarTodosOsAutores()).thenReturn(List.of(autorResumeResponse));
        mockMvc.perform(get("/autor")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Machado de Assis"));
        verify(autorService).mostrarTodosOsAutores();
        verifyNoMoreInteractions(autorService);
    }

    @Test
    void deveAtualizarAutor() throws Exception {
        when(autorService.atualizarAutor(eq(1L), any(AutorUpdateRequest.class))).thenReturn(autorResponse);
        mockMvc.perform(patch("/autor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autorUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Machado de Assis"));
        verify(autorService).atualizarAutor(eq(1L), any(AutorUpdateRequest.class));
        verifyNoMoreInteractions(autorService);
    }

    @Test
    void deveDeletarAutorPorId() throws Exception {
        doNothing().when(autorService).deletarAutorPorId(1L);
        mockMvc.perform(delete("/autor/1"))
                .andExpect(status().isNoContent());
        verify(autorService).deletarAutorPorId(1L);
        verifyNoMoreInteractions(autorService);
    }
}
