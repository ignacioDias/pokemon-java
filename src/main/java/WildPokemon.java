import java.util.Random;

public class WildPokemon implements Fighter {
    Pokemon pokemon;
    Item equippedItem;

    public WildPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
    public boolean canFight() {
        return pokemon.currentLife > 0;
    }

    public Pokemon getNextPokemon() throws IllegalStateException {
        if(pokemon == null || pokemon.currentLife == 0)
            throw new IllegalStateException("pokemon isn't available");
        return pokemon;
    }

    public Attack getNextAttack() {
        Random rand = new Random();
        int attackPos = rand.nextInt(pokemon.getCantAttacks());
        return pokemon.attacks[attackPos];
    }

    public void killPokemon() {
        pokemon.kill();
    }
}
