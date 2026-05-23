package com.alan.api_autores_obras.controller;

import com.alan.api_autores_obras.dto.autor.AutorRequest;
import com.alan.api_autores_obras.dto.autor.AutorResponse;
import com.alan.api_autores_obras.dto.autor.AutorResumeResponse;
import com.alan.api_autores_obras.dto.autor.AutorUpdateRequest;
import com.alan.api_autores_obras.security.config.SecurityConfig;
import com.alan.api_autores_obras.service.AutorService;
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
@RequestMapping("/autor")
@Tag(name = "Autor", description = "Gerenciamento de autores")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class AutorController {
    private final AutorService autorService;

    @PostMapping
    @Operation(summary = "Cadastrar autor", description = "Cadastra um novo autor")
    @ApiResponse(responseCode = "201", description = "Autor cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<AutorResponse> cadastrarAutor(@RequestBody @Valid AutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.cadastrarAutor(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID", description = "Busca os dados de um autor pelo ID")
    @ApiResponse(responseCode = "200", description = "Autor encontrado")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    public ResponseEntity<AutorResponse> buscarAutorPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.buscarAutorPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar autores", description = "Lista resumida de todos os autores")
    @ApiResponse(responseCode = "200", description = "Lista encontrada com sucesso")
    public ResponseEntity<List<AutorResumeResponse>> mostrarTodosOsAutores() {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.mostrarTodosOsAutores());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar autor", description = "Atualiza os dados de um autor")
    @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<AutorResponse> atualizarAutor(@PathVariable Long id, @RequestBody @Valid AutorUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(autorService.atualizarAutor(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar autor", description = "Deleta um autor pelo ID")
    @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    public ResponseEntity<Void> deletarAutorPorId(@PathVariable Long id){
        autorService.deletarAutorPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
 }
