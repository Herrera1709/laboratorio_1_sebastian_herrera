package com.sebastianherrera.laboratorio.controllers;

import com.sebastianherrera.laboratorio.dto.UsuarioDTO;
import com.sebastianherrera.laboratorio.service.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @GetMapping
    public List<UsuarioDTO> findAll() {
        return usuarioService.findAll();
    }
}
