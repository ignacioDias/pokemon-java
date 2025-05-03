import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Trainer implements Fighter {

    String name;
    List<Pokemon> pokemon;
    int currentPokemonIndex;
    Scanner sc = new Scanner(System.in);
    List<Item> bag;

    public Trainer(String name) {
        this.currentPokemonIndex = -1;
        this.name = name;
        this.pokemon = new ArrayList<>();
    }
    public Trainer(String name, List<Pokemon> pokemon) {
        this.currentPokemonIndex = -1;
        this.name = name;
        this.pokemon = pokemon;
    }

    public boolean canFight() {
        for(Pokemon pokemon : pokemon) {
            if(!pokemon.isDead())
                return true;
        }
        return false;
    }
    public Pokemon getNextPokemon() throws IllegalStateException {
        if(!this.canFight())
            throw new IllegalStateException("No more pokemon to fight");
        if(this.pokemon.size() == 1 && this.currentPokemonIndex != -1) {
            throw new IllegalStateException("Only One pokemon available");
        }
        Random rand = new Random();
        int oldIndex;
        do {
            oldIndex = currentPokemonIndex;
            currentPokemonIndex = rand.nextInt(pokemon.size());
        } while(pokemon.get(currentPokemonIndex) == null || pokemon.get(currentPokemonIndex).isDead() || oldIndex == currentPokemonIndex);
        return pokemon.get(currentPokemonIndex);
    }

    public void addPokemon(Pokemon pokemon) throws IllegalArgumentException {
        if(pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null");
        }
        if(this.pokemon.size() > 5) {
            throw new IllegalArgumentException("Too many pokemon");
        }
        this.pokemon.add(pokemon);
    }

    public Attack getNextAttack() throws IllegalStateException {
        Random rand = new Random();
        if(this.currentPokemonIndex < 0 || this.currentPokemonIndex >= pokemon.size()) {
            throw new IllegalStateException("No more pokemon to fight");
        }
        Pokemon currentPokemon = pokemon.get(currentPokemonIndex);
        int cantAttacksOfCurrentPokemon = currentPokemon.getCantAttacks();
        int attack = rand.nextInt(cantAttacksOfCurrentPokemon);
        return currentPokemon.attacks[attack];
    }

    public void killPokemon() {
        if(currentPokemonIndex < 0 || currentPokemonIndex > pokemon.size() - 1 || pokemon.get(currentPokemonIndex).currentLife == 0 || pokemon.get(currentPokemonIndex) == null) {
            throw new IllegalStateException("Index out of bounds");
        }
        pokemon.get(currentPokemonIndex).kill();
        currentPokemonIndex = -1;
    }

    public String getName() {
        return name;
    }
}