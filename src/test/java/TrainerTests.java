import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrainerTests {
    Trainer trainer;

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
        Pokemon pokemon = new Pokemon(specie, 10);
        Pokemon pokemon2 = new Pokemon(specie, 20);
        this.trainer = new Trainer("Red");
        this.trainer.addPokemon(pokemon);
        this.trainer.addPokemon(pokemon2);
    }
    @Test
    public void testValidAndTrueCanFight() {
        trainer.getNextPokemon();
        assertTrue(trainer.canFight());
    }
    @Test
    public void testCanFightWithoutCurrentPokemon() {
        assertTrue(trainer.canFight());
    }
    @Test
    public void testGetNextPokemon() {
        assertNotNull(trainer.getNextPokemon());
    }
    @Test
    public void testGetPokemonWithOnlyOneAvailable() {

    }
    @Test
    public void killingPokemonChangesCurrentPokemon() {
        Pokemon oldPokemon = trainer.getNextPokemon();
        trainer.killPokemon();
        assertNotEquals(oldPokemon, trainer.getNextPokemon());
    }

    @Test
    public void killingLastPokemonDoesntLetContinueFighting() {
        trainer.getNextPokemon();
        trainer.killPokemon();
        trainer.getNextPokemon();
        trainer.killPokemon();
        assertFalse(trainer.canFight());
    }
    @Test
    public void killingOnlyOneOfTwoPokemonDoesntMeansToStopFighting() {
        trainer.getNextPokemon();
        trainer.killPokemon();
        assertTrue(trainer.canFight());
    }
    @Test
    public void killingWithoutChangingPokemonLeadsToBug() {
        trainer.getNextPokemon();
        trainer.killPokemon();
        assertThrows(IllegalStateException.class, () -> {
            trainer.killPokemon();  // Should throw
        });
    }
    @Test
    public void killingWithoutStarterPokemonLeadsToBug() {
        assertThrows(IllegalStateException.class, () -> {
            trainer.killPokemon();  // Should throw
        });
    }
    @Test
    public void testGetNextAttackWithNoCurrentPokemon() {
        assertThrows(IllegalStateException.class, () -> {
            Attack attack = trainer.getNextAttack();
        });
    }
    @Test
    public void textGetNextPokemonWithoutPokemon() {
        trainer.getNextPokemon();
        trainer.killPokemon();
        trainer.getNextPokemon();
        trainer.killPokemon();
        assertThrows(IllegalStateException.class, () -> {
            trainer.getNextPokemon();
        });
    }
    @Test
    public void addNullPokemon() {
        assertThrows(IllegalArgumentException.class, () -> {
            trainer.addPokemon(null);
        });
    }
    @Test
    public void addMoreThanSixPokemon() {
        Pokemon clone = new Pokemon(trainer.getNextPokemon());
        trainer.addPokemon(clone);
        trainer.addPokemon(clone);
        trainer.addPokemon(clone);
        trainer.addPokemon(clone);
        assertThrows(IllegalArgumentException.class, () -> {
            trainer.addPokemon(clone);
        });
    }
    @Test
    public void testGetNextAttackReturnsValidAttack() {
        trainer.getNextPokemon();
        Attack attack = trainer.getNextAttack();
        assertNotNull(attack);
    }
    @Test
    public void testKillPokemonResetsCurrentPokemonIndex() {
        trainer.getNextPokemon();
        trainer.killPokemon();

        assertThrows(IllegalStateException.class, () -> {
            trainer.getNextAttack();
        });
    }

    @Test
    public void testCurrentIndexWasChanged() {
        int oldIndex = trainer.currentPokemonIndex;
        trainer.getNextPokemon();
        assertNotEquals(oldIndex, trainer.currentPokemonIndex);
    }

}
