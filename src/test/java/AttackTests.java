import org.junit.Test;
import org.junit.Before;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AttackTests {
    Attack attack = new Attack("a", Type.FIGHT, 70, 100, "attack", Effect.NONE, 0);
    Specie specie = new Specie(mock(Specie.class));
    @Before
    public void setUp() {
        specie.availableSexes = List.of(Sex.MALE, Sex.FEMALE);
        specie.firstType = Type.FIGHT;
        specie.secondType = null;
        specie.movementsByLevel = List.of(new Tuple<>(1, new Attack("Punch", Type.FIGHT, 15, 100, "Generic Attack", Effect.NONE, 0)));
        specie.baseStats = new Stats(12,12,12,12);
        specie.evsGivenAfterDefeat = new Stats(1,0,0,1);
        specie.movementsByOtherWays = List.of(new Tuple<>(LearnMethod.MT, mock(Attack.class)));
        specie.name = "Beautiful";
    }


    @Test
    public void testSuccessfulAttack() {
        Pokemon pokemonRival = new Pokemon(specie, 10);
        Pokemon pokemonAttacker = new Pokemon(specie, 2);
        int oldLife = pokemonRival.getStats().life;
        attack.attack(pokemonAttacker, pokemonRival);
        assertNotEquals(oldLife, pokemonRival.getStats().life);
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
        Pokemon pokemonRival = new Pokemon(specie, 100);
        pokemonRival.setLife(2100000000);
        Pokemon pokemonAttacker = new Pokemon(specie, 1);
        int oldLife = pokemonRival.getStats().life;
        Attack attackNeverMissing = new Attack("Don't miss", Type.FIGHT, 1, 100, "Miss", Effect.NONE, 0);
        for(int i = 0; i < 1000000; i++) {
            attackNeverMissing.attack(pokemonAttacker, pokemonRival);
            if(oldLife == pokemonRival.getStats().life) {
                fail();
            }
            oldLife = pokemonRival.getStats().life;
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
