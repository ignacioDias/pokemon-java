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

        // Limpia pantalla inicial
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Usa WASD para moverte. Presiona Q para salir.");

        while (true) {
            System.out.print("\033[H\033[2J"); // Limpia pantalla
            System.out.flush();
            System.out.println(map.toString());

            int input = System.in.read();
            char key = Character.toLowerCase((char) input);

            switch (key) {
                case 'w':
                    map.moveUp();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(map.toString());
                    break;
                case 's':
                    map.moveDown();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(map.toString());
                    break;
                case 'd':
                    map.moveRight();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(map.toString());
                    break;
                case 'a':
                    map.moveLeft();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(map.toString());
                    break;
                case 'q':
                    System.out.println("Saliendo...");
                    break;
            }
        }
    }
}
