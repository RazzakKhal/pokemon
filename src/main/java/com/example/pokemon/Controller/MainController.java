package com.example.pokemon.Controller;

import com.example.pokemon.Entity.Pokemon;
import com.example.pokemon.Entity.Veterinaire;
import com.example.pokemon.Repository.PokemonRepository;
import com.example.pokemon.Repository.VeterinaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class MainController {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    VeterinaireRepository veterinaireRepository;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<?> addPokemon(@RequestBody Pokemon pokemon){
        // si le pokemon n'est pas deja présent en BDD
        if(pokemonRepository.findByName(pokemon.getName()) == null){
            pokemonRepository.save(pokemon);
            return new ResponseEntity<>("Pokemon enregistré avec succès", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Pokemon déjà existant", HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Pokemon> getAllPokemon(){
        List<Pokemon> myPokemons = pokemonRepository.findAll();
        return myPokemons;

    }

    @RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
    public HashMap getPokemon(@PathVariable String name){
        HashMap response = new HashMap<>();
        // si il existe en base de données on le renvoi, sinon on renvoi un message d'erreur
        if(pokemonRepository.findByName(name) != null){
            Pokemon pokemon = pokemonRepository.findByName(name);
            response.put("pokemon", pokemon);
            return response;
        }


        response.put("Erreur", "Ce pokemon n'existe pas");
       return response;
    }

    @RequestMapping(value = "/update/{name}", method = RequestMethod.PUT)
    public HashMap updatePokemon(@PathVariable String name, @RequestBody Pokemon poke){
        HashMap response = new HashMap<>();
        // si il existe un pokémon qui porte deja le nouveau nom qu'on souhaite donné alors erreur
        if(pokemonRepository.findByName(poke.getName()) != null){
            response.put("Erreur", "Un pokémon portant ce nom existe déjà");
            return response;
        }


            // si il existe je le modifie, sinon je retourne une erreur
        if(pokemonRepository.findByName(name) != null){
            Pokemon pokemon = pokemonRepository.findByName(name);
            pokemon.setName(poke.getName());
            pokemonRepository.save(pokemon);
            response.put("pokemon", pokemon);
            return response;
        }
        response.put("Erreur", "Ce pokemon n'existe pas");
        return response;

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePokemon(@PathVariable Long id){

        pokemonRepository.deleteById(id);
        return new ResponseEntity<>("si ce pokémon existaitn il a été supprimé",HttpStatus.ACCEPTED);
    }


    // supprimer un pokemon par son nom

    @RequestMapping(value = "delete/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePokemon(@PathVariable String name){
        if(pokemonRepository.findByName(name) != null){
            pokemonRepository.deleteByName(name);
            return new ResponseEntity<>("le pokémon a été supprimé",HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("un pokémon n'existant pas ne peut pas être supprimé",HttpStatus.BAD_REQUEST);
        }

    }

    // supprimer un pokemon par son nom

    @RequestMapping(value = "deleteveto/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVeterinaire(@PathVariable String name){
        if(veterinaireRepository.findByName(name) != null){
            // pour eviter tout conflit si ce veterinaire est en charge de pokémon je supprime leur veto associé
            Veterinaire myVeto = veterinaireRepository.findByName(name);
          List<Pokemon> pokemons = pokemonRepository.findAllPokemonByVeterinaire(myVeto.getId());
          for(Pokemon poke : pokemons){
              // je leur met un vétérinaire sans propriété pour les délié de mon veterinaire
              poke.setVeterinaire(null);
              pokemonRepository.save(poke);
          }

            // je peux maintenant supprimer mon veterinaire
            veterinaireRepository.deleteById(myVeto.getId());
            return new ResponseEntity<>("le veterinaire a été supprimé",HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("un veterinaire n'existant pas ne peut pas être supprimé",HttpStatus.BAD_REQUEST);
        }

    }

}
