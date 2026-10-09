package com.sebastianherrera.laboratorio.repository;

import com.sebastianherrera.laboratorio.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
