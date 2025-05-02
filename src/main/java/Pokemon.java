import java.util.List;
import java.util.Random;

public class Pokemon {

    String name;
    private Stats stats;
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
    Stats statsAffectedByStates = new Stats(100, 100, 100, 100);
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
    public Pokemon(Specie specie, int level) {

        this.name = specie.name;
        this.specie = specie;
        this.level = level;
        this.state = Effect.NONE;

        //Determine sex
        int quantityOfAvailableSexes = specie.availableSexes.size();
        int randomSexPosition = random.nextInt(quantityOfAvailableSexes);
        this.sex = specie.availableSexes.get(randomSexPosition);

        //Determine nature
        Nature[] natures = Nature.values();
        int randomNaturePosition = random.nextInt(natures.length);
        this.nature = natures[randomNaturePosition];

        this.attacks = specie.generateAttacks(level);
        this.isShiny = this.determineShiny();
        this.currentExperience = 0;

        int attackIVS = random.nextInt(31) + 1;
        int lifeIVS = random.nextInt(31) + 1;
        int defenseIVS = random.nextInt(31) + 1;
        int speedIVS = random.nextInt(31) + 1;
        this.ivs = new Stats(lifeIVS, attackIVS, defenseIVS, speedIVS);

        int life = (((2 * specie.baseStats.life + ivs.life + (evs.life / 4)) * level) / 100) + level + 10;
        this.stats = new Stats(life, 0,0,0);
        this.stats = this.calculateStats();

        if(!repOk())
            throw new IllegalArgumentException("Pokemon doesn't sat repOk");
    }
    private boolean determineShiny() {
        return random.nextInt(100) == 1;
    }
    public Stats getStats() {
        this.stats = this.calculateStats();
        return this.stats;
    }
    public void setLife(int life) {
        this.stats = this.calculateStats();
        this.stats.life = life;
    }
    public void setAttack(int attack) {
        this.stats = this.calculateStats();
        this.stats.attack = attack;
    }
    public void setDefense(int defense) {
        this.stats = this.calculateStats();
        this.stats.defense = defense;
    }
    public void setSpeed(int speed) {
        this.stats = this.calculateStats();
        this.stats.speed = speed;
    }

    private Stats calculateStats() {
        int attack = applyNatureAndState(
                specie.baseStats.attack, ivs.attack, evs.attack, level,
                getNatStats().attack, statsAffectedByStates.attack
        );

        int defense = applyNatureAndState(
                specie.baseStats.defense, ivs.defense, evs.defense, level,
                getNatStats().defense, statsAffectedByStates.defense
        );

        int speed = applyNatureAndState(
                specie.baseStats.speed, ivs.speed, evs.speed, level,
                getNatStats().speed, statsAffectedByStates.speed
        );

        return new Stats(this.stats.life, attack, defense, speed);
    }
    private int applyNatureAndState(int base, int iv, int ev, int level, int natureModifier, int stateModifier) {
        float stat = (((2 * base + iv + (ev / 4.0f)) * level) / 100.0f) + 5;
        stat *= natureModifier / 100.0f;
        stat *= stateModifier / 100.0f;
        return (int) stat;
    }

    private Stats getNatStats() {
        switch(this.nature) {
            case LONELY:
                return new Stats(1, 110, 90, 100);
            case BRAVE:
                return new Stats(1, 110, 100, 90);
            case BOLD:
                return new Stats(1, 90, 110, 100);
            case RELAXED:
                return new Stats(1, 100, 110, 90);
            case TIMID:
                return new Stats(1, 90, 100, 110);
            case HASTY:
                return new Stats(1, 100, 90, 110);
            case DOCILE:
            case HARDY:
            case SERIOUS:
            case BASHFUL:
            case QUIRKY:
                return new Stats(1, 100, 100, 100);
            default:
                throw new IllegalArgumentException("Invalid Nature");
        }
    }

    public void increaseEvs(Stats evsToIncrement) {
    //TODO
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
        //TODO: CHECK EVOLVING
    }
    public void clearStatus() {
        if(this.state == Effect.BURN)
            this.statsAffectedByStates.attack = 100;
        if(this.state == Effect.PARALYSIS)
            this.statsAffectedByStates.speed = 100;
        this.state = Effect.NONE;
    }
    public boolean repOk() {
        if(this.attacks == null || this.attacks.length == 0 || this.state == null || this.specie == null)
            return false;
        for (Attack attack : this.attacks) {
            if(attack == null || attack.name.equals("Punch"))
                continue;
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
    public void kill() {
        this.setLife(0);
        assert isDead();
    }
    public boolean isDead() {
        this.calculateStats();
        return this.stats.life <= 0;
    }
}

