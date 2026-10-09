package com.sebastianherrera.laboratorio.mappers;

import com.sebastianherrera.laboratorio.dto.CategoriaDTO;
import com.sebastianherrera.laboratorio.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {

    CategoriaDTO toDto(Categoria categoria);

    List<CategoriaDTO> toDtoList(List<Categoria> categorias);

    @Mapping(target = "productos", ignore = true)
    Categoria toEntity(CategoriaDTO categoriaDTO);
}
