package com.example.pokemon.Repository;

import com.example.pokemon.Entity.Veterinaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinaireRepository extends JpaRepository<Veterinaire,Long> {
    Veterinaire findByName(String name);
}
