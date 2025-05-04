public class Map {
    Block[][] map;
    int width;
    int height;
    Tuple<Integer, Integer> currentPlayerPosition;

    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[36m";
    public static final String BROWN = "\u001B[33m";
    public static final String YELLOW = "\u001B[93m";
    public static final String WHITE = "\u001B[97m";
    public static final String RED = "\u001B[31m";

    public Map(Block[][] map, int xPosPlayer, int yPosPlayer) {
        this.map = map;
        this.width = map.length;
        this.height = map[0].length;
        this.currentPlayerPosition = new Tuple<>(xPosPlayer, yPosPlayer);
        if(!repOk()) {
            throw new IllegalArgumentException("Map doesn't sat repOk");
        }
    }
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                switch(map[i][j]) {
                    case OBSTACLE:
                        output.append(WHITE + "[B]" + RESET);
                        break;
                    case FLOOR:
                        output.append(BROWN + "[F]" + RESET);
                        break;
                    case WATER:
                        output.append(BLUE + "[W]" + RESET);
                        break;
                    case PLAYER:
                        output.append(YELLOW + "[P]" + RESET);
                        break;
                    case GRASS:
                        output.append(GREEN + "[G]" + RESET);
                        break;
                    case DOOR:
                        output.append(RED + "[D]" + RESET);
                }
            }
            output.append("\n");
        }
        return output.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Map map1 = (Map) o;
        if (width != map1.width) return false;
        if (height != map1.height) return false;
        if (currentPlayerPosition != map1.currentPlayerPosition) return false;
        for(int i = 0; i < map1.width; i++) {
            for(int j = 0; j < map1.height; j++) {
                if(map[i][j] != map1.map[i][j]) return false;
            }
        }
        return true;
    }
    public boolean repOk() {
        if(currentPlayerPosition == null) {
            return false;
        }
        if(width != map.length || height != map[0].length)
            return false;
        for (Block[] blocks : map) {
            if (blocks.length != map[0].length)
                return false;
        }
        if(map[currentPlayerPosition.first][currentPlayerPosition.second] != Block.PLAYER) {
            return false;
        }
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(map[x][y] == Block.PLAYER && x != currentPlayerPosition.first && y != currentPlayerPosition.second) {
                    return false;
                }
            }
        }
        return true;
    }
}

