import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AttackTests {
    Attack attack = new Attack("a", Type.FIGHT, 70, 100, "attack", Effect.NONE, 0);
    @Test
    public void testSuccessfulAttack() {
        Pokemon pokemonRival = new Pokemon(mock(Specie.class), 10);
        Pokemon pokemonAttacker = new Pokemon(mock(Specie.class), 2);
        int oldLife = pokemonRival.getStats().life;
        attack.attack(pokemonRival, pokemonAttacker);
        assertTrue(oldLife != pokemonRival.getStats().life);
    }
    @Test
    public void testKillerAttack() {

    }
    @Test
    public void testAlwaysMissingAttack() {

    }
    @Test
    public void testNeverMissingAttack() {

    }
    @Test
    public void testNoEffectiveAttack() {

    }
    @Test
    public void testEffectAttack() {
    }
}
/*
    String name;
    Type type;
    int accuracy;
    String description;
    int damage;
    Effect effect;
    int probabilityOfEffect;
    boolean isStatusAttack;
 */