package com.sebastianherrera.laboratorio.service;

import com.sebastianherrera.laboratorio.dto.UsuarioDTO;
import com.sebastianherrera.laboratorio.model.Rol;
import com.sebastianherrera.laboratorio.model.Usuario;
import com.sebastianherrera.laboratorio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private UsuarioDTO toDto(Usuario usuario) {
        String rol = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .findFirst()
                .orElse("");
        return new UsuarioDTO(usuario.getId(), usuario.getUsername(), rol);
    }
}
