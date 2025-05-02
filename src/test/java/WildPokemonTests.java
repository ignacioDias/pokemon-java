import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WildPokemonTests {

    private Pokemon pokemon;
    private WildPokemon wild;

    @Before
    public void setUp() {
        Stats stats = new Stats(1, 1, 1, 1);
        List<Tuple<Integer, Specie>> evolutionsByLevel = new ArrayList<>();
        List<Tuple<EvolutionMethod, Specie>> evolutionsByOtherMethods = new ArrayList<>();
        List<Tuple<Integer, Attack>> movementsByLevel = new ArrayList<>();
        List<Tuple<LearnMethod, Attack>> movementsByOtherWays = new ArrayList<>();
        Type firstType = Type.FIRE;
        Type secondType = Type.NONE;
        List<Sex> availableSexes = List.of(Sex.MALE);

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
        when(pokemon.getStats()).thenReturn(new Stats(0, 10, 10, 10));
        assertFalse(wild.canFight());
    }

    @Test(expected = IllegalStateException.class)
    public void throwsWhenNoPokemonAvailable() {
        WildPokemon noPokemon = new WildPokemon(null);
        noPokemon.getNextPokemon();
    }

    @Test(expected = IllegalStateException.class)
    public void throwsWhenPokemonIsDead() {
        when(pokemon.getStats()).thenReturn(new Stats(0, 10, 10, 10));
        wild.getNextPokemon();  // Should throw
    }

    @Test
    public void killPokemonDelegatesToPokemonDead() {
        wild.killPokemon();
        verify(pokemon).isDead();
    }

    @Test
    public void getNextAttackReturnsOneOfTheAvailableAttacks() {
        Attack result = wild.getNextAttack();
        assertNotNull(result);
        assertTrue(List.of(pokemon.attacks).contains(result));
    }
    @Test
    public void testOnlyOnePokemon() {
        assertTrue(wild.canFight());
        wild.killPokemon();
        assertFalse(wild.canFight());
    }
}
