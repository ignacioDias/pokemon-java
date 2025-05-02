import java.util.Random;

public class SavagePokemon implements Fighter {
    Pokemon pokemon;
    public SavagePokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
    public boolean canFight() {
        return pokemon.getStats().life > 0;
    }

    public Pokemon getNextPokemon() throws IllegalStateException {
        if(pokemon == null || pokemon.getStats().life == 0)
            throw new IllegalStateException("pokemon isn't available");
        return pokemon;
    }

    public Attack getNextAttack() {
        Random rand = new Random();
        int attackPos = rand.nextInt(pokemon.attacks.length);
        return pokemon.attacks[attackPos];
    }

    public void killPokemon() {
        pokemon.setLife(0);
    }
}
