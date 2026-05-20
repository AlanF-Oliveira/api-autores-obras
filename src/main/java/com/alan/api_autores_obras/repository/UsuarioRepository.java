package com.alan.api_autores_obras.repository;

import com.alan.api_autores_obras.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByEmail(String email);

}
