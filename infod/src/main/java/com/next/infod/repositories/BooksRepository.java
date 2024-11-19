package com.next.infod.repositories;

import com.next.infod.model.BooksModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BooksRepository extends JpaRepository<BooksModel, UUID> {
}
