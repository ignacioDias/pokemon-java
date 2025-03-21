import org.junit.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;

public class PokemonTest {

    @Test
    public void testShinyRate() {
        boolean foundShiny = false;

        Attack mockAttack = mock(Attack.class);
        List<Tuple<Integer, Attack>> movementsByLevel = List.of(new Tuple<>(0, mockAttack));
        List<Tuple<LearnMethod, Attack>> movementsByOtherWays = List.of(new Tuple<>(LearnMethod.MT, mockAttack));

        List<Sex> sexes = List.of(Sex.OTHER);
        Stats mockStats = mock(Stats.class);

        Specie mockSpecie = new Specie(1, "Dummy", null, movementsByLevel, movementsByOtherWays, Type.OTHER, null, mockStats, sexes);
        Attack[] attacks = {mockAttack};
        for (int i = 0; i < 100; i++) {
            Pokemon dummy = new Pokemon(mockStats, mockSpecie, 5, attacks);
            if (dummy.isShiny) {
                foundShiny = true;
                break;
            }
        }
        assertTrue("No se encontró ningún Pokémon shiny en 100 intentos.", foundShiny);
    }
}
