import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SpecieTests {
    private Stats validStats;
    private List<Sex> sexes;
    private List<Tuple<Integer, Attack>> levelMoves;
    private List<Tuple<LearnMethod, Attack>> otherMoves;
    private List<Tuple<Integer, Specie>> evolutionsByLevel;
    private List<Tuple<EvolutionMethod, Specie>> evolutionsByOther;

    @Before
    public void setUp() {
        validStats = new Stats(1, 1, 1, 1);
        sexes = List.of(Sex.MALE, Sex.FEMALE);
        levelMoves = List.of(new Tuple<>(1, mock(Attack.class)));
        otherMoves = List.of(new Tuple<>(LearnMethod.MT, mock(Attack.class)));
        evolutionsByLevel = List.of();
        evolutionsByOther = List.of();
    }

    @Test
    public void validSpecieShouldPassRepOK() {
        Specie s = new Specie(
                1,
                "Pikachu",
                evolutionsByLevel,
                evolutionsByOther,
                levelMoves,
                otherMoves,
                Type.ELECTRIC,
                null,
                validStats,
                sexes,
                validStats
        );
        assertTrue(s.repOK());
    }

    @Test
    public void copiedSpecieShouldBeEqualToOriginal() {
        Specie original = new Specie(
                10,
                "Eevee",
                evolutionsByLevel,
                evolutionsByOther,
                levelMoves,
                otherMoves,
                Type.NORMAL,
                null,
                validStats,
                sexes,
                validStats
        );
        Specie copy = new Specie(original);

        assertEquals(original.name, copy.name);
        assertEquals(original.id, copy.id);
        assertEquals(original.firstType, copy.firstType);
        assertSame(original.availableSexes, copy.availableSexes); // referencia compartida
        assertTrue(copy.repOK());
    }

    @Test
    public void repOKFailsWhenFirstTypeIsNull() {
        Specie s = new Specie(1, "Failmon", evolutionsByLevel, evolutionsByOther, levelMoves, otherMoves, null, null, validStats, sexes, validStats);
        assertFalse(s.repOK());
    }

    @Test
    public void repOKFailsWhenMovementsByLevelIsNull() {
        Specie s = new Specie(1, "Failmon", evolutionsByLevel, evolutionsByOther, null, otherMoves, Type.FIRE, null, validStats, sexes, validStats);
        assertFalse(s.repOK());
    }

    @Test
    public void repOKFailsWhenMovementsByOtherWaysIsEmpty() {
        Specie s = new Specie(1, "Failmon", evolutionsByLevel, evolutionsByOther, levelMoves, List.of(), Type.FIRE, null, validStats, sexes, validStats);
        assertFalse(s.repOK());
    }

    @Test
    public void repOKFailsWhenAvailableSexesIsEmpty() {
        Specie s = new Specie(1, "Failmon", evolutionsByLevel, evolutionsByOther, levelMoves, otherMoves, Type.FIRE, null, validStats, List.of(), validStats);
        assertFalse(s.repOK());
    }

    @Test
    public void repOKFailsWhenEvsGivenAfterDefeatIsNull() {
        Specie s = new Specie(1, "Failmon", evolutionsByLevel, evolutionsByOther, levelMoves, otherMoves, Type.FIRE, null, validStats, sexes, null);
        assertFalse(s.repOK());
    }

    @Test
    public void repOKFailsWhenNameIsEmpty() {
        Specie s = new Specie(1, "", evolutionsByLevel, evolutionsByOther, levelMoves, otherMoves, Type.FIRE, null, validStats, sexes, validStats);
        assertFalse(s.repOK());
    }
}
