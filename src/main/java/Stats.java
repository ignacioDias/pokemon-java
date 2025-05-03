public class Stats {
    int life;
    int attack;
    int defense;
    int speed;

    public Stats(int life, int attack, int defense, int speed) {
        this.life = life;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        if(!repOK())
            throw new IllegalArgumentException("Stats cannot be repOK");
    }
    public Stats(Stats statsToCopy) {
        this.life = statsToCopy.life;
        this.attack = statsToCopy.attack;
        this.defense = statsToCopy.defense;
        this.speed = statsToCopy.speed;
        if(!repOK())
            throw new IllegalArgumentException("Stats cannot be repOK");
    }
    boolean repOK() {
        return life >= 0 && attack >= 0 && defense >= 0 && speed >= 0;
    }
}
