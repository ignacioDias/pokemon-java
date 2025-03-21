public class Pokemon {
    String name;
    Stats stats;
    Specie specie;
    int level;
    Attack[] attacks = new Attack[4];
    Sex sex;
    Nature nature;

    public Pokemon(Stats stats, Specie specie, int level, Attack[] attacks) {
        this.name = specie.name;
        this.stats = stats;
        this.specie = specie;
        this.level = level;
        this.sex = Sex.FEMALE; //TODO: CHANGE FOR RANDOM CHOICE IN AVAILABLE SEXES OF SPECIES
        this.stats = this.calculateStats();
        this.nature = Nature.ADAMANT; //TODO: CHANGE FOR RANDOM CHOICE
        this.attacks = attacks;
    }

    private Stats calculateStats() {
        return null;
    }

}

