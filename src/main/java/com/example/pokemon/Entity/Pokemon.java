package com.example.pokemon.Entity;

import jakarta.persistence.*;

@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinaire_id")
    private Veterinaire veterinaire;
    public Pokemon() {
    }

    public Pokemon(String name, String type, Veterinaire veterinaire) {
        this.name = name;
        this.type = type;
        this.veterinaire = veterinaire;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Veterinaire getVeterinaire() {
        return veterinaire;
    }

    public void setVeterinaire(Veterinaire veterinaire) {
        this.veterinaire = veterinaire;
    }
}
