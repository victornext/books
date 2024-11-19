package com.next.infod.DTOS;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record BooksDTO(@NotBlank String autor, LocalDateTime Nascimento, String nationality) {
}
