import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trainer implements Fighter {

    List<Pokemon> pokemon;
    int currentPokemonIndex = -1;
    Scanner sc = new Scanner(System.in);

    public Trainer() {
        this.pokemon = new ArrayList<>();
    }
    public boolean canFight() {
        return !pokemon.isEmpty();
    }
    public Pokemon getNextPokemon() throws IndexOutOfBoundsException {
        System.out.println("Ingrese el pokemon a lanzar, empezando del 0");
        currentPokemonIndex = sc.nextInt();
        if(currentPokemonIndex < 0 || currentPokemonIndex > pokemon.size() - 1 ||
          pokemon.get(currentPokemonIndex) == null || pokemon.get(currentPokemonIndex).stats.life == 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return pokemon.get(currentPokemonIndex);
    }

    public void addPokemon(Pokemon pokemon) throws IllegalArgumentException {
        if(this.pokemon.size() > 5) {
            throw new IllegalArgumentException("Too many pokemon");
        }
        this.pokemon.add(pokemon);
    }

    public Attack getNextAttack() throws IndexOutOfBoundsException {
        System.out.println("Ingrese el ataque a usar, empezando del 0");
        Pokemon currentPokemon = pokemon.get(currentPokemonIndex);
        int attack = sc.nextInt();
        if(attack < 0 || attack > 3 || currentPokemon.attacks[attack] == null) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return currentPokemon.attacks[attack];
    }

    public void killPokemon() {
        if(currentPokemonIndex < 0 || currentPokemonIndex > pokemon.size() - 1 || pokemon.get(currentPokemonIndex).stats.life == 0 || pokemon.get(currentPokemonIndex) == null) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        pokemon.get(currentPokemonIndex).stats.life = 0;
    }

}
