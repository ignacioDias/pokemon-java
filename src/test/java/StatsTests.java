import org.junit.Test;
import static org.junit.Assert.*;

public class StatsTests {

    @Test
    public void validStatsShouldPassRepOK() {
        Stats stats = new Stats(10, 20, 30, 40);
        assertTrue(stats.repOK());
    }

    @Test
    public void copiedStatsShouldMatchOriginal() {
        Stats original = new Stats(12, 34, 56, 78);
        Stats copy = new Stats(original);
        assertEquals(original.life, copy.life);
        assertEquals(original.attack, copy.attack);
        assertEquals(original.defense, copy.defense);
        assertEquals(original.speed, copy.speed);
        assertTrue(copy.repOK());
    }

    @Test(expected = IllegalArgumentException.class)
    public void statsWithZeroLifeShouldFail() {
        new Stats(0, 10, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void statsWithNegativeAttackShouldFail() {
        new Stats(10, -5, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void statsWithZeroDefenseShouldFail() {
        new Stats(10, 10, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void statsWithNegativeSpeedShouldFail() {
        new Stats(10, 10, 10, -1);
    }

}
