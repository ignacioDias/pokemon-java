import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
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
                        Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.FLOOR,
                        Block.FLOOR, Block.FLOOR, Block.FLOOR, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS,
                        Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS,
                        Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.GRASS, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER,
                        Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.WATER, Block.OBSTACLE
                },
                {
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE,
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE,
                        Block.OBSTACLE, Block.OBSTACLE,
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE,
                        Block.OBSTACLE, Block.OBSTACLE, Block.OBSTACLE
                }
        };
        Map map = new Map(mapArray, 1, 1);
        while(true) {
            System.out.println(map.toString());
            int first = System.in.read();
            if (first == 27) { // ESC
                int second = System.in.read(); // [
                if (second == 91) {
                    int third = System.in.read(); // A/B/C/D
                    switch (third) {
                        case 'A':
                            map.moveUp();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(map.toString());
                            break;
                        case 'B':
                            map.moveDown();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(map.toString());
                            break;
                        case 'C':
                            map.moveRight();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(map.toString());
                            break;
                        case 'D':
                            map.moveLeft();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println(map.toString());
                            break;
                        default:
                            System.out.println("Otra secuencia: " + third);
                    }
                }
            } else {
                System.out.println("Tecla regular: " + (char) first);
            }
        }
    }
}
