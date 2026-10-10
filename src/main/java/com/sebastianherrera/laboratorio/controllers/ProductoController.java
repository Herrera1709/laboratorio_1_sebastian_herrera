package com.sebastianherrera.laboratorio.controllers;

import com.sebastianherrera.laboratorio.dto.ProductoDTO;
import com.sebastianherrera.laboratorio.mappers.ProductoMapper;
import com.sebastianherrera.laboratorio.model.Producto;
import com.sebastianherrera.laboratorio.service.ProductoService;
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

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;

    public ProductoController(ProductoService productoService, ProductoMapper productoMapper) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<ProductoDTO> findAll() {
        return productoMapper.toDtoList(productoService.findAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.of(productoService.findById(id).map(productoMapper::toDto));
    }

    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @PostMapping
    public ResponseEntity<ProductoDTO> create(@RequestBody ProductoDTO productoDTO) {
        Producto entidad = productoMapper.toEntity(productoDTO);
        return productoService.create(entidad, productoDTO.categoriaId())
                .map(creado -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(creado.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(productoMapper.toDto(creado));
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        Producto entidad = productoMapper.toEntity(productoDTO);
        return ResponseEntity.of(
                productoService.update(id, entidad, productoDTO.categoriaId()).map(productoMapper::toDto)
        );
    }

    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return productoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
