package com.alan.api_autores_obras.controller;

import com.alan.api_autores_obras.dto.obra.ObraRequest;
import com.alan.api_autores_obras.dto.obra.ObraResponse;
import com.alan.api_autores_obras.dto.obra.ObraResumeResponse;
import com.alan.api_autores_obras.dto.obra.ObraUpdateRequest;
import com.alan.api_autores_obras.service.ObraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/obra")
public class ObraController {
    private final ObraService obraService;

    @PostMapping
    public ResponseEntity<ObraResponse> cadastrarObra(@RequestBody @Valid ObraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.cadastrarObra(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObraResponse> buscarObraPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(obraService.buscarObraPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ObraResumeResponse>> mostrarTodasAsObras() {
        return ResponseEntity.status(HttpStatus.OK).body(obraService.mostrarTodasAsObras());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ObraResponse> atualizarObra(@PathVariable Long id, @RequestBody @Valid ObraUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(obraService.atualizarObraPorId(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarObraPorId(@PathVariable Long id){
        obraService.deletarObraPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
