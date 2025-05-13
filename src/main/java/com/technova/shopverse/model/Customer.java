package com.technova.shopverse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client") // Nombre de la tabla en la base de datos

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 100, nullable = false) // Nombre de la columna en la base de datos
    private String name;
    private String email;

    @Transient // Este campo no se guarda en la base de datos
    private String temporaryToken; // Token temporal para autenticación
}