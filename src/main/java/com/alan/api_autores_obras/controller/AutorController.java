package com.alan.api_autores_obras.controller;

import com.alan.api_autores_obras.dto.autor.AutorRequest;
import com.alan.api_autores_obras.dto.autor.AutorResponse;
import com.alan.api_autores_obras.dto.autor.AutorResumeResponse;
import com.alan.api_autores_obras.dto.autor.AutorUpdateRequest;
import com.alan.api_autores_obras.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/autor")
public class AutorController {
    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponse> cadastrarAutor(@RequestBody @Valid AutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.cadastrarAutor(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResumeResponse> buscarAutorPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.buscarAutorPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AutorResumeResponse>> mostrarTodosOsAutores() {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.mostrarTodosOsAutores());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable Long id, @RequestBody @Valid AutorUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.atualizarAutor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutorPorId(@PathVariable Long id){
        autorService.deletarAutorPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
 }
