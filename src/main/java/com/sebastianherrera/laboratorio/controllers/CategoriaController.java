package com.sebastianherrera.laboratorio.controllers;

import com.sebastianherrera.laboratorio.dto.CategoriaDTO;
import com.sebastianherrera.laboratorio.mappers.CategoriaMapper;
import com.sebastianherrera.laboratorio.model.Categoria;
import com.sebastianherrera.laboratorio.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper) {
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<CategoriaDTO> findAll() {
        return categoriaMapper.toDtoList(categoriaService.findAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.of(categoriaService.findById(id).map(categoriaMapper::toDto));
    }

    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @PostMapping
    public ResponseEntity<CategoriaDTO> create(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria creada = categoriaService.create(categoriaMapper.toEntity(categoriaDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(categoriaMapper.toDto(creada));
    }

    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.of(
                categoriaService.update(id, categoriaMapper.toEntity(categoriaDTO)).map(categoriaMapper::toDto)
        );
    }

    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var resultado = categoriaService.delete(id);

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!resultado.get()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "La categoría tiene productos asociados"));
        }
        return ResponseEntity.noContent().build();
    }
}
