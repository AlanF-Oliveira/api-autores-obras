package com.alan.api_autores_obras.service;

import com.alan.api_autores_obras.dto.autor.*;
import com.alan.api_autores_obras.entity.Autor;
import com.alan.api_autores_obras.entity.AutorFixture;
import com.alan.api_autores_obras.mapper.AutorMapper;
import com.alan.api_autores_obras.repository.AutorRepository;
import com.alan.api_autores_obras.repository.ObraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
