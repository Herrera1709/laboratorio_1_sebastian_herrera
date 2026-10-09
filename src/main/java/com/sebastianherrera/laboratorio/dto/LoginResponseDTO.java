package com.sebastianherrera.laboratorio.dto;

public record LoginResponseDTO(
        String token,
        String username,
        String rol
) {
}
