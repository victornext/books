package com.next.infod.controller.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record BooksDTO(
        UUID id,
        @NotBlank(message = "Autor não pode estar vazio") String autor,

        @NotNull(message = "Data de nascimento não pode ser nula") LocalDate nascimento,

        @NotBlank(message = "Nacionalidade não pode estar vazia") String nationality
) {
}
