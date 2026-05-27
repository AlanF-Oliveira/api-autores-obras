package com.alan.api_autores_obras.controller;

import com.alan.api_autores_obras.dto.auth.AuthResponse;
import com.alan.api_autores_obras.dto.auth.CadastroRequest;
import com.alan.api_autores_obras.dto.auth.LoginRequest;
import com.alan.api_autores_obras.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Autenticação e registro de usuários")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    public ResponseEntity<AuthResponse> cadastrarUsuario(@RequestBody @Valid CadastroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.cadastrarUsuario(request));
    }

    @PostMapping("/entrar")
    @Operation(summary = "Login", description = "Autentica o usuário e retorna o token")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<AuthResponse> login (@RequestBody @Valid LoginRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }

}
