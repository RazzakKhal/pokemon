package com.example.pokemon.Repository;

import com.example.pokemon.Entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    public Pokemon findByName(String name);

    public void deleteByName(String name);


    // récupérer les pokémon via l'id d'un veterinaire
    @Query("SELECT p FROM Pokemon p WHERE p.veterinaire.id = :id")
    List<Pokemon> findAllPokemonByVeterinaire(@Param("id") Long id);



}
