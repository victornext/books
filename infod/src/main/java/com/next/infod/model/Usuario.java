package com.next.infod.model;


import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;


@Entity
@Table
@Data
public class Usuario {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String login;

    @Column
    private String email;

    @Column
    private String senha;

    @Column(name = "roles", columnDefinition = "varchar[]")
    @Type(ListArrayType.class) //Faz a tradução de lista pra array
    private List<String> roles;


}
