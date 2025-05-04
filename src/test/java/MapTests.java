import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MapTests {
    @Test
    public void testMap() {
        Block[][] mapArray = {
                {
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE,
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE,
                        Block.GRASS, Block.GRASS,
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE,
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE,
                        Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR,
                        Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR,
                        Block.FLOOR,
                        Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR,
                        Block.DOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR,
                        Block.DOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR,
                        Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS,
                        Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.PLAYER, Block.FLOOR,
                        Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS,
                        Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS,
                        Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.OBSTACLE
                },
                {
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER
                },
                {
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER
                }
        };


        int xPos = 4;
        int yPos = 8;
        Map map = new Map(mapArray, xPos, yPos);
        System.out.println(map.toString());
        assertTrue(map.repOk());
    }
    @Test
    public void testToString() {
        Block[][] arr = {{}, {Block.FLOOR, Block.PLAYER}};
        Map map = new Map(arr, 1, 1);
    }
}
