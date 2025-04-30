import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PokemonTest {
    private Attack mockAttack;
    private Stats mockStats;
    private Specie mockSpecie;

    @Before
    public void setUp() {
        mockAttack = mock(Attack.class);
        List<Tuple<Integer, Attack>> movementsByLevel = List.of(new Tuple<>(0, mockAttack));
        List<Tuple<LearnMethod, Attack>> movementsByOtherWays = List.of(new Tuple<>(LearnMethod.MT, mockAttack));
        List<Sex> sexes = List.of(Sex.OTHER);
        mockStats = mock(Stats.class);
        mockSpecie = new Specie(1, "Dummy", null, movementsByLevel, movementsByOtherWays, Type.FIRE, null, mockStats, sexes, mockStats);
    }

    @Test
    public void testShinyRate() {
        boolean foundShiny = false;

        Attack[] attacks = {mockAttack};
        for (int i = 0; i < 900; i++) {
            Pokemon dummy = new Pokemon(mockSpecie, 5, attacks);
            if (dummy.isShiny) {
                foundShiny = true;
                break;
            }
        }
        assertTrue("Didn't find any shiny with 900 tries", foundShiny);
    }
    @Test
    public void testInvalidPokemonConstructor() {
        Attack[] validAttacks = {mockAttack};
        assertThrows(NullPointerException.class, () ->
                        new Pokemon(null, 5, validAttacks),
                "Debería lanzar una excepción si la especie es nula."
        );
        assertThrows(IllegalArgumentException.class, () ->
                        new Pokemon(mockSpecie, 5, null),
                "Debería lanzar una excepción si la lista de ataques es nula."
        );
        assertThrows(IllegalArgumentException.class, () ->
                        new Pokemon(mockSpecie, 5, new Attack[0]),
                "Debería lanzar una excepción si la lista de ataques está vacía."
        );
        assertThrows(IllegalArgumentException.class, () ->
                        new Pokemon(mockSpecie, 0, validAttacks),
                "Debería lanzar una excepción si el nivel es 0."
        );
        assertThrows(IllegalArgumentException.class, () ->
                        new Pokemon(mockSpecie, 101, validAttacks),
                "Debería lanzar una excepción si el nivel es mayor a 100."
        );
    }
    @Test
    public void testValidPokemonConstructor() {
        Attack[] attacks = {mockAttack};
        assertDoesNotThrow(() -> new Pokemon(mockSpecie, 10, attacks));
        Pokemon pokemon = new Pokemon(mockSpecie, 10, attacks);
        assertNotNull(pokemon);
        assertEquals("Dummy", pokemon.name);
        assertTrue(pokemon.level > 0 && pokemon.level <= 100);
        assertNotNull(pokemon.sex);
        assertNotNull(pokemon.nature);
        assertNotNull(pokemon.stats);
        assertEquals(0, pokemon.currentExperience);
    }
    @Test
    public void testIncreaseCurrentExperience() {
        Attack[] attacks = {mockAttack};
        Pokemon pokemon = new Pokemon(mockSpecie, 1, attacks);
        pokemon.increaseCurrentExperience(1);
        boolean currentExperienceIsOne = pokemon.currentExperience == 1;
        boolean levelDidntChange = pokemon.level == 1;
        assertTrue("Experience is more than one", currentExperienceIsOne);
        assertTrue("Level is more than one", levelDidntChange);
    }
    @Test
    public void testIncreaseCurrentExperienceWithLevelUp() {
        Attack[] attacks = {mockAttack};
        Pokemon pokemon = new Pokemon(mockSpecie, 1, attacks);
        pokemon.increaseCurrentExperience(100);
        assertTrue("Didnt level up", pokemon.level > 1);
        assertTrue("Didnt load the exp", pokemon.currentExperience > 0);
    }
}
