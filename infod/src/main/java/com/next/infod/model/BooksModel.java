package com.next.infod.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_book")
@Data
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class)
public class BooksModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String autor;

    private LocalDate nascimento;

    @Column(nullable = false, length = 50)
    private String nationality;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime registerData;

    @LastModifiedDate
    @Column(name =  "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    public BooksModel() {}
}
