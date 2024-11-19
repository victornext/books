package com.next.infod.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_book")
@Data
@ToString(exclude = "autor")
public class BooksModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String autor;
    private LocalDateTime Nascimento;
    private String nationality;


    public BooksModel(){}


}
