package com.sebastianherrera.laboratorio.service;

import com.sebastianherrera.laboratorio.model.Categoria;
import com.sebastianherrera.laboratorio.repository.CategoriaRepository;
import com.sebastianherrera.laboratorio.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria create(Categoria categoria) {
        categoria.setId(null);
        categoria.getProductos().clear();
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Optional<Categoria> update(Long id, Categoria datos) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(datos.getNombre());
            categoria.setDescripcion(datos.getDescripcion());
            return categoriaRepository.save(categoria);
        });
    }

    /**
     * @return empty = no existe, false = tiene productos, true = eliminada
     */
    @Transactional
    public Optional<Boolean> delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            return Optional.empty();
        }
        if (productoRepository.existsByCategoriaId(id)) {
            return Optional.of(false);
        }
        categoriaRepository.deleteById(id);
        return Optional.of(true);
    }
}
