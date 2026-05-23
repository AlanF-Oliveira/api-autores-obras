package com.alan.api_autores_obras.controller;

import com.alan.api_autores_obras.dto.obra.ObraRequest;
import com.alan.api_autores_obras.dto.obra.ObraResponse;
import com.alan.api_autores_obras.dto.obra.ObraResumeResponse;
import com.alan.api_autores_obras.dto.obra.ObraUpdateRequest;
import com.alan.api_autores_obras.security.config.SecurityConfig;
import com.alan.api_autores_obras.service.ObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/obra")
@Tag(name = "Obra", description = "Gerenciamento de obras")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class ObraController {
    private final ObraService obraService;

    @PostMapping
    @Operation(summary = "Cadastrar obra", description = "Cadastra uma nova obra")
    @ApiResponse(responseCode = "201", description = "Obra cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<ObraResponse> cadastrarObra(@RequestBody @Valid ObraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.cadastrarObra(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar obra por ID", description = "Busca uma obra pelo ID")
    @ApiResponse(responseCode = "200", description = "Obra encontrada")
    @ApiResponse(responseCode = "404", description = "Obra não encontrada")
    public ResponseEntity<ObraResponse> buscarObraPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(obraService.buscarObraPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar obras", description = "Lista todas as obras")
    @ApiResponse(responseCode = "200", description = "Lista encontrada com sucesso")
    public ResponseEntity<List<ObraResumeResponse>> mostrarTodasAsObras() {
        return ResponseEntity.status(HttpStatus.OK).body(obraService.mostrarTodasAsObras());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar obra", description = "Atualiza uma obra")
    @ApiResponse(responseCode = "200", description = "Obra atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Obra não encontrada")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<ObraResponse> atualizarObra(@PathVariable Long id, @RequestBody @Valid ObraUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(obraService.atualizarObraPorId(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar obra", description = "Deleta uma obra pelo ID")
    @ApiResponse(responseCode = "204", description = "Obra deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Obra não encontrada")
    public ResponseEntity<Void> deletarObraPorId(@PathVariable Long id){
        obraService.deletarObraPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
