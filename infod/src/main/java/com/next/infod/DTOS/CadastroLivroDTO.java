package com.next.infod.DTOS;

import com.next.infod.Enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
                              @NotBlank String isbn,
                              @NotBlank String titulo,
                              @NotNull LocalDate DataPublicacao,
                              @NotBlank GeneroLivro genero,
                              @NotNull BigDecimal preco,
                               UUID idAutor)
{ }
