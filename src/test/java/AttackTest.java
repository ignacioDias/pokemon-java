import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttackTest {

    @Test
    public void testAttackMisses() {
        Attack attack = new Attack("Miss Attack", Type.FIRE, 0, "Always misses", Effect.NONE, 0);
        Pokemon attacker = PokemonFactory.createMockPokemon(Type.FIRE, 50);
        Pokemon rival = PokemonFactory.createMockPokemon(Type.GRASS, 50);

        assertFalse(attack.attack(attacker, rival));
    }

    @Test
    public void testAttackHitsWithoutEffect() {
        Attack attack = new Attack("Basic Attack", Type.FIRE, 100, "Guaranteed hit", Effect.NONE, 0);
        Pokemon attacker = PokemonFactory.createMockPokemon(Type.FIRE, 50);
        Pokemon rival = PokemonFactory.createMockPokemon(Type.GRASS, 50);

        int originalLife = rival.stats.life;
        boolean result = attack.attack(attacker, rival);

        assertTrue(result);
        assertTrue(rival.stats.life < originalLife);
    }

    @Test
    public void testAttackWithEffectTriggers() {
        Attack attack = new Attack("Burn Attack", Type.FIRE, 100, "Might burn", Effect.BURN, 100);
        Pokemon attacker = PokemonFactory.createMockPokemon(Type.FIRE, 50);
        Pokemon rival = PokemonFactory.createMockPokemon(Type.GRASS, 50);

        attack.attack(attacker, rival);
        assertEquals(Effect.BURN, rival.state);
    }

    @Test
    public void testStatusAttackOnlyAppliesEffect() {
        Attack attack = new Attack("Status Attack", Type.POISON, 100, "Poison only", Effect.POISON, 100);
        attack.isStatusAttack = true;

        Pokemon attacker = PokemonFactory.createMockPokemon(Type.POISON, 50);
        Pokemon rival = PokemonFactory.createMockPokemon(Type.GRASS, 50);

        int lifeBefore = rival.stats.life;
        attack.attack(attacker, rival);
        int lifeAfter = rival.stats.life;

        assertEquals(Effect.POISON, rival.state);
        assertEquals(lifeBefore, lifeAfter);
    }

    @Test
    public void testRepOkValidAttack() {
        Attack attack = new Attack("Valid Attack", Type.FIRE, 80, "Valid test", Effect.NONE, 0);
        attack.damage = 50;
        attack.isStatusAttack = false;
        assertTrue(attack.repOk());
    }

    @Test
    public void testRepOkInvalidDamage() {
        Attack attack = new Attack("Invalid Damage", Type.FIRE, 80, "Invalid damage test", Effect.NONE, 0);
        attack.damage = -5;
        attack.isStatusAttack = false;
        assertFalse(attack.repOk());
    }

    @Test
    public void testRepOkInvalidStatusLogic() {
        Attack attack = new Attack("Invalid Status", Type.FIRE, 100, "Status problem", Effect.NONE, 50);
        attack.damage = 0;
        attack.isStatusAttack = true;
        assertFalse(attack.repOk());
    }
}

// Helper factory class to generate mock Pokémon objects
class PokemonFactory {
    public static Pokemon createMockPokemon(Type type, int level) {
        Pokemon p = new Pokemon();
        p.level = level;
        p.specie = new Specie(type);
        p.stats = new Stats();
        p.stats.life = 100;
        p.stats.attack = 50;
        p.stats.defense = 50;
        return p;
    }
}

