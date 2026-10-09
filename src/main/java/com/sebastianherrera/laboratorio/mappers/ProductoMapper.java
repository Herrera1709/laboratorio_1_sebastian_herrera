package com.sebastianherrera.laboratorio.mappers;

import com.sebastianherrera.laboratorio.dto.ProductoDTO;
import com.sebastianherrera.laboratorio.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoMapper {

    @Mapping(source = "categoria.id", target = "categoriaId")
    ProductoDTO toDto(Producto producto);

    List<ProductoDTO> toDtoList(List<Producto> productos);

    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoDTO productoDTO);
}
