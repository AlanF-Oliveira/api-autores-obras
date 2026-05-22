package com.alan.api_autores_obras.service;

import com.alan.api_autores_obras.dto.auth.CadastroRequest;
import com.alan.api_autores_obras.entity.Usuario;
import com.alan.api_autores_obras.exception.ConflictException;
import com.alan.api_autores_obras.repository.UsuarioRepository;
import com.alan.api_autores_obras.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public Usuario cadastrarUsuario(CadastroRequest request){
        usuarioRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ConflictException("Email existente!"));
    }


}
