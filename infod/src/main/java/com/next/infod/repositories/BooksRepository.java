package com.next.infod.repositories;

import com.next.infod.model.BooksModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BooksRepository extends JpaRepository<BooksModel, UUID> {
    List<BooksModel> findByAutor(String autor);
    List<BooksModel> findByNationality(String nationality);
    List<BooksModel> findByAutorAndNationality(String autor, String nationality);
    Optional<BooksModel> findByAutorAndNascimentoAndNationality(
            String autor, LocalDate nascimento, String nationality
    );

}
