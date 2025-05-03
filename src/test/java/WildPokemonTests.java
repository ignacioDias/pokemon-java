import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WildPokemonTests {

    private Pokemon pokemon;
    private WildPokemon wild;

    @Before
    public void setUp() {
        Stats stats = new Stats(1, 1, 1, 1);
        Attack attack = new Attack("Kick", Type.FIGHT, 20, 100, "Kick", Effect.NONE, 0);
        List<Tuple<Integer, Specie>> evolutionsByLevel = new ArrayList<>();
        List<Tuple<EvolutionMethod, Specie>> evolutionsByOtherMethods = new ArrayList<>();
        List<Tuple<Integer, Attack>> movementsByLevel = List.of(new Tuple<>(1, attack));
        List<Tuple<LearnMethod, Attack>> movementsByOtherWays = new ArrayList<>();
        Type firstType = Type.FIRE;
        Type secondType = Type.WATER;
        List<Sex> availableSexes = List.of(Sex.MALE, Sex.FEMALE);

        Specie specie = new Specie(
                1,
                "example",
                evolutionsByLevel,
                evolutionsByOtherMethods,
                movementsByLevel,
                movementsByOtherWays,
                firstType,
                secondType,
                stats,
                availableSexes,
                stats
        );
        pokemon = new Pokemon(specie, 10);
        wild = new WildPokemon(pokemon);

    }

    @Test
    public void wildPokemonCanFightWhenAlive() {
        assertTrue(wild.canFight());
    }

    @Test
    public void wildPokemonCannotFightWhenDead() {
        wild.killPokemon();
        assertFalse(wild.canFight());
    }

    @Test(expected = IllegalStateException.class)
    public void throwsWhenNoPokemonAvailable() {
        WildPokemon noPokemon = new WildPokemon(null);
        noPokemon.getNextPokemon();
    }

    @Test
    public void throwsWhenPokemonIsDead() {
        wild.killPokemon();
        assertThrows(IllegalStateException.class, () -> {
            wild.getNextPokemon();  // Should throw
        });
    }

    @Test
    public void killPokemonDelegatesToPokemonDead() {
        wild.killPokemon();
        assertTrue(pokemon.isDead());
    }

    @Test
    public void getNextAttackReturnsOneOfTheAvailableAttacks() {
        Attack result = wild.getNextAttack();
        assertNotNull(result);
        boolean contains = false;
        for(Attack attack : pokemon.attacks) {
            if(attack.equals(result)) {
                contains = true;
                break;
            }
        }
        assertTrue(contains);
    }
    @Test
    public void testOnlyOnePokemon() {
        assertTrue(wild.canFight());
        wild.killPokemon();
        assertFalse(wild.canFight());
    }
}
