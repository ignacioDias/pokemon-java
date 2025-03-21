public class Stats {
    int life;
    int specialAttack;
    int attack;
    int defense;
    int specialDefense;
    int speed;

    public Stats(int life, int specialAttack, int attack, int defense, int specialDefense, int speed) {
        this.life = life;
        this.specialAttack = specialAttack;
        this.attack = attack;
        this.defense = defense;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }

    boolean repOK() {
        return !(life < 0 || specialAttack < 0 || attack < 0 || defense < 0 || speed < 0 || specialDefense < 0);
    }
}
