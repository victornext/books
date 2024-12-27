package com.next.infod.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String clientId;

    private String clientSecret;

    private String redirectURI;

    private String scope;


}
