package com.next.infod.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_book", schema = "public")
@Getter
@Setter
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

    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY)
    @JsonManagedReference // Indica que a lista de livros será serializada
    private List<Livro> livros;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime registerData;

    @LastModifiedDate
    @Column(name =  "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public BooksModel() {}
}

