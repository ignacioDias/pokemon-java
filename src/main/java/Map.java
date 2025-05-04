public class Map {
    Block[][] mapCopy;
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

    public Map(Block[][] map, int rowPlayer, int colPlayer) {
        this.map = map;
        this.mapCopy = new Block[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, this.mapCopy[i], 0, map[i].length);
        }

        this.width = map.length;
        this.height = map[0].length;
        this.currentPlayerPosition = new Tuple<>(rowPlayer, colPlayer);
        this.map[rowPlayer][colPlayer] = Block.PLAYER;
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
    public void moveRight() {
        if(currentPlayerPosition.second == this.width - 1) {
            return;
        }
        Block blockToMove = this.map[currentPlayerPosition.first][currentPlayerPosition.second + 1];
        if(!isBlockAvailableToMove(blockToMove)) {
            return;
        }
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = this.mapCopy[currentPlayerPosition.first][currentPlayerPosition.second];
        currentPlayerPosition.second = currentPlayerPosition.second + 1;
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = Block.PLAYER;
        if(!repOk()) {
            throw new IllegalArgumentException("Map doesn't sat repOk");
        }
    }
    public void moveLeft() {
        if(currentPlayerPosition.second == 0) {
            return;
        }
        Block blockToMove = this.map[currentPlayerPosition.first][currentPlayerPosition.second - 1];
        if(!isBlockAvailableToMove(blockToMove)) {
            return;
        }
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = this.mapCopy[currentPlayerPosition.first][currentPlayerPosition.second];
        currentPlayerPosition.second = currentPlayerPosition.second - 1;
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = Block.PLAYER;
        if(!repOk()) {
            throw new IllegalArgumentException("Map doesn't sat repOk");
        }
    }
    public void moveUp() {
        if(currentPlayerPosition.first == 0) {
            return;
        }
        Block blockToMove = this.map[currentPlayerPosition.first - 1][currentPlayerPosition.second];
        if(!isBlockAvailableToMove(blockToMove)) {
            return;
        }
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = this.mapCopy[currentPlayerPosition.first][currentPlayerPosition.second];
        currentPlayerPosition.first = currentPlayerPosition.first - 1;
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = Block.PLAYER;
        if(!repOk()) {
            throw new IllegalArgumentException("Map doesn't sat repOk");
        }
    }
    public void moveDown() {
        if(currentPlayerPosition.first == this.map.length - 1) {
            return;
        }
        Block blockToMove = this.map[currentPlayerPosition.first + 1][currentPlayerPosition.second];
        if(!isBlockAvailableToMove(blockToMove)) {
            return;
        }
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = this.mapCopy[currentPlayerPosition.first][currentPlayerPosition.second];
        currentPlayerPosition.first = currentPlayerPosition.first + 1;
        this.map[currentPlayerPosition.first][currentPlayerPosition.second] = Block.PLAYER;
        if(!repOk()) {
            throw new IllegalArgumentException("Map doesn't sat repOk");
        }
    }
    private boolean isBlockAvailableToMove(Block block) {
        return block != Block.OBSTACLE && block != Block.DOOR && block != Block.WATER;
    }
    public boolean repOk() {
        if(currentPlayerPosition == null) {
            return false;
        }
        if(currentPlayerPosition.second >= width || currentPlayerPosition.first >= height) {
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
                if(this.mapCopy[x][y] == Block.PLAYER) {
                    return false;
                }
                if(map[x][y] == Block.PLAYER && (x != currentPlayerPosition.first || y != currentPlayerPosition.second)) {
                    return false;
                }
            }
        }
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(x == currentPlayerPosition.first && y == currentPlayerPosition.second) {
                    continue;
                }
                if(this.map[x][y] != this.mapCopy[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }
}

