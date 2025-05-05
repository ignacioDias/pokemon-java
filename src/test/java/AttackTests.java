import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AttackTests {
    Attack attack = new Attack("a", Type.FIGHT, 70, 100, "attack", Effect.NONE, 0);
    Specie specie;
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

        specie = new Specie(
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
    }
    @Test
    public void testSuccessfulAttack() {
        Pokemon pokemonRival = new Pokemon(specie, 10);
        Pokemon pokemonAttacker = new Pokemon(specie, 2);
        int oldLife = pokemonRival.currentLife;
        attack.attack(pokemonAttacker, pokemonRival);
        assertNotEquals(oldLife, pokemonRival.currentLife);
    }
    @Test
    public void testKillerAttack() {
        Pokemon pokemonRival = new Pokemon(specie, 1);
        Pokemon pokemonAttacker = new Pokemon(specie, 20);
        pokemonRival.setLife(1);
        attack.attack(pokemonAttacker, pokemonRival);
        assertTrue(pokemonRival.isDead());
    }
    @Test
    public void testAlwaysMissingAttack() {
        Pokemon pokemonRival = new Pokemon(specie, 10);
        Pokemon pokemonAttacker = new Pokemon(specie, 2);
        int oldLife = pokemonRival.getStats().life;
        Attack attackAlwaysMissing = new Attack("miss", Type.FIGHT, 100, 0, "Miss", Effect.NONE, 0);
        for(int i = 0; i < 1000000; i++)
            attackAlwaysMissing.attack(pokemonAttacker, pokemonRival);
        assertEquals(oldLife, pokemonRival.getStats().life);
    }
    @Test
    public void testNeverMissingAttack() {
        specie.baseStats = new Stats(1200000,12,12,12);
        Pokemon pokemonRival = new Pokemon(specie, 100);
        Pokemon pokemonAttacker = new Pokemon(specie, 1);
        int oldLife = pokemonRival.currentLife;
        Attack attackNeverMissing = new Attack("Don't miss", Type.FIGHT, 1, 100, "Miss", Effect.NONE, 0);
        for(int i = 0; i < 1000000; i++) {
            attackNeverMissing.attack(pokemonAttacker, pokemonRival);
            if(oldLife == pokemonRival.currentLife) {
                fail();
            }
            oldLife = pokemonRival.currentLife;
        }
        assertTrue(true);
    }
    @Test
    public void testNoEffectiveAttack() {
        Specie ghost = new Specie(specie);
        ghost.secondType = Type.GHOST;
        Pokemon pokemonRival = new Pokemon(ghost, 10);
        Pokemon pokemonAttacker = new Pokemon(specie, 2);
        int oldLife = pokemonRival.getStats().life;
        Attack notEffectiveAttack = new Attack("Punch", Type.FIGHT, 100, 100, "Not Effective At All With Ghosts D:", Effect.NONE, 0);
        for(int i = 0; i < 1000000; i++)
            notEffectiveAttack.attack(pokemonAttacker, pokemonRival);
        assertEquals(oldLife, pokemonRival.getStats().life);
    }
    @Test
    public void testEffectAttack() {
        Attack paralysis = new Attack("Paralysis", Type.ELECTRIC, 0, 100, "sparky", Effect.PARALYSIS, 100);
        Pokemon pokemonRival = new Pokemon(specie, 100);
        Pokemon pokemonAttacker = new Pokemon(specie, 1);
        int oldSpeed = pokemonRival.getStats().speed;
        paralysis.attack(pokemonAttacker, pokemonRival);
        assertEquals(Effect.PARALYSIS, pokemonRival.state);
        assertTrue(pokemonRival.getStats().speed < oldSpeed);
    }
}
