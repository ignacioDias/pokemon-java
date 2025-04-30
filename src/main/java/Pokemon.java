import java.util.List;
import java.util.Random;

public class Pokemon {

    String name;
    Stats stats;
    Stats evs = new Stats(0, 0, 0, 0);
    Stats ivs;
    Specie specie;
    int level;
    Attack[] attacks = new Attack[4];
    Sex sex;
    Nature nature;
    boolean isShiny;
    int currentExperience;
    Effect state;

    private final Random random = new Random();

    public Pokemon(Pokemon pokemonToCopy) {
        this.name = pokemonToCopy.name;
        this.stats = new Stats(pokemonToCopy.stats);
        this.evs = pokemonToCopy.evs;
        this.ivs = pokemonToCopy.ivs;
        this.specie = pokemonToCopy.specie;
        this.level = pokemonToCopy.level;
        this.attacks = pokemonToCopy.attacks;
    }
    public Pokemon(Specie specie, int level, Attack[] attacks) {

        this.name = specie.name;
        this.specie = specie;
        this.level = level;

        //Determine sex
        int quantityOfAvailableSexes = specie.availableSexes.size();
        int randomSexPosition = random.nextInt(quantityOfAvailableSexes);
        this.sex = specie.availableSexes.get(randomSexPosition);
        this.stats = this.calculateStats();

        //Determine nature
        Nature[] natures = Nature.values();
        int randomNaturePosition = random.nextInt(natures.length);
        this.nature = natures[randomNaturePosition];

        this.attacks = attacks;
        this.isShiny = this.determineShiny();
        this.currentExperience = 0;

        int attack = random.nextInt(31) + 1;
        int life = random.nextInt(31) + 1;
        int defense = random.nextInt(31) + 1;
        int speed = random.nextInt(31) + 1;
        this.ivs = new Stats(life, attack, defense, speed);

        if(!repOk())
            throw new IllegalArgumentException("Pokemon doesn't sat repOk");
    }
    private boolean determineShiny() {
        return random.nextInt(100) == 1;
    }
    private Stats calculateStats() {
        return new Stats(1,1,1,1); //TODO
    }
    public void increaseEvs(Stats evsToIncrement) {

    }
    public void increaseCurrentExperience(int experience) {
        if(experience < 1)
            throw new IllegalArgumentException("experience must be greater than 0");
        int experienceNeededForLevelUp = calculateCurrentMaxExperience() - this.currentExperience;
        if(experience >= experienceNeededForLevelUp) {
            this.levelUp();
            increaseCurrentExperience(experience - experienceNeededForLevelUp);
        } else {
            this.currentExperience += experience;
        }
    }
    private int calculateCurrentMaxExperience() {
        return 10 * (this.level + 1);
    }
    private void levelUp() {
        if(this.level < 100) {
            this.level++;
            this.currentExperience = 0;
        }
    }
    public void clearStatus() {
        if(this.state == Effect.BURN)
            this.stats.attack *= 2;
        if(this.state == Effect.PARALYSIS)
            this.stats.speed *= 2;
        this.state = Effect.NONE;
    }
    public boolean repOk() {
        if(this.attacks == null || this.attacks.length == 0 || this.state == null || this.specie == null)
            return false;
        for (Attack attack : this.attacks) {
            boolean attackLearnedByLevel = attackExistsInList(this.specie.movementsByLevel, attack);
            boolean attackLearnedByOtherWay = attackExistsInList(this.specie.movementsByOtherWays, attack);
            if(!attackLearnedByLevel && !attackLearnedByOtherWay)
                return false;
        }
        return ivs != null && evs != null && sex != null && nature != null && stats != null && level > 0 && level <= 100
                && !name.isEmpty() && currentExperience >= 0 && currentExperience < calculateCurrentMaxExperience();
    }

    private <T> boolean attackExistsInList(List<Tuple<T, Attack>> movements, Attack attack) {
        for (Tuple<T, Attack> movement : movements) {
            if (movement.second.equals(attack))
                return true;
        }
        return false;
    }
}

