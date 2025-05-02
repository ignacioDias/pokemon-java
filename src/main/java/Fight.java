import java.util.Random;

public class Fight {

    private final Fighter fighter1;
    private final Fighter fighter2;
    private final Random rand = new Random();
    private static final int PARALYSIS_PROBABILITY = 30;
    private static final int FREEZE_PROBABILITY = 20;

    public Fight(Fighter fighter1, Fighter fighter2) {
        if (fighter1 == null || fighter2 == null) {
            throw new IllegalArgumentException("Fighters cannot be null");
        }
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public void fight() {
        while (fighter1.canFight() && fighter2.canFight()) {
            Pokemon pokemon1 = new Pokemon(fighter1.getNextPokemon());
            Pokemon pokemon2 = new Pokemon(fighter2.getNextPokemon());
            while (pokemon1.getStats().life > 0 && pokemon2.getStats().life > 0) {
                Attack attack1 = fighter1.getNextAttack();
                Attack attack2 = fighter2.getNextAttack();

                boolean pokemon1IsFaster = isFirstAttacker(pokemon1, pokemon2);
                if (pokemon1IsFaster) {
                    performTurn(pokemon1, pokemon2, attack1, attack2);
                } else {
                    performTurn(pokemon2, pokemon1, attack2, attack1);
                }
            }
            if(pokemon1.getStats().life <= 0)
                fighter1.killPokemon();
            if(pokemon2.getStats().life <= 0)
                fighter2.killPokemon();
        }
        System.out.println((fighter1.canFight() ? fighter1 : fighter2) + " wins");
    }

    private boolean isFirstAttacker(Pokemon p1, Pokemon p2) {
        if (p1.getStats().speed > p2.getStats().speed) return true;
        if (p1.getStats().speed < p2.getStats().speed) return false;
        return rand.nextBoolean(); // random when equal speed
    }

    private void performTurn(Pokemon first, Pokemon second, Attack firstAttack, Attack secondAttack) {
        checkClearStatus(first);
        if(canAttack(first))
            firstAttack.attack(first, second);
        if (second.getStats().life <= 0) {
            return; // only attack if still alive
        }
        checkClearStatus(second);
        if(canAttack(second))
            secondAttack.attack(second, first);
    }

    private boolean canAttack(Pokemon pokemon) {
        boolean isSleepingOrFrozen = pokemon.state == Effect.SLEEP || pokemon.state == Effect.FREEZE;
        boolean isParalyzed = isPokemonParalyzed(pokemon);
        return !isSleepingOrFrozen && !isParalyzed;
    }

    private boolean isPokemonParalyzed(Pokemon pokemon) {
        return pokemon.state == Effect.PARALYSIS && AuxiliarMethods.calculateProbability(PARALYSIS_PROBABILITY);
    }

    private void checkClearStatus(Pokemon pokemon) {
        if (shouldClearStatus(pokemon)) {
            System.out.println(pokemon.name + " has been cleared of" + pokemon.state.toString());
            pokemon.clearStatus();
        } else {
            System.out.println(pokemon.name + " is still " + pokemon.state.toString());
        }
    }

    private boolean shouldClearStatus(Pokemon pokemon) {
        return AuxiliarMethods.calculateProbability(FREEZE_PROBABILITY) &&
                (pokemon.state == Effect.FREEZE || pokemon.state == Effect.SLEEP);
    }

}
