package com.example.pokemon.Repository;

import com.example.pokemon.Entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    public Pokemon findByName(String name);
}
