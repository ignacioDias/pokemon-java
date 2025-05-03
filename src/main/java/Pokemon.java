import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Pokemon {

    String name;
    private Stats stats;
    Stats evs = new Stats(1, 1, 1, 1);
    Stats ivs;
    Specie specie;
    int level;
    Attack[] attacks = new Attack[4];
    Sex sex;
    Nature nature;
    boolean isShiny;
    int currentLife;
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
        this.sex = pokemonToCopy.sex;
        this.nature = pokemonToCopy.nature;
        this.isShiny = pokemonToCopy.isShiny;
        this.currentExperience = pokemonToCopy.currentExperience;
        this.state = pokemonToCopy.state;
        this.statsAffectedByStates = pokemonToCopy.statsAffectedByStates;
        this.currentLife = pokemonToCopy.currentLife;
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

        this.attacks = generateAttacks();
        this.isShiny = this.determineShiny();
        this.currentExperience = 0;

        int attackIVS = random.nextInt(31) + 1;
        int lifeIVS = random.nextInt(31) + 1;
        int defenseIVS = random.nextInt(31) + 1;
        int speedIVS = random.nextInt(31) + 1;
        this.ivs = new Stats(lifeIVS, attackIVS, defenseIVS, speedIVS);

        this.updateStats();
        this.currentLife = this.stats.life;
        if(!repOk())
            throw new IllegalArgumentException("Pokemon doesn't sat repOk");
    }
    private Attack[] generateAttacks() {
        Random rand = new Random();
        int index = 0;
        Attack[] attacks = {null, null, null, null};
        for(Tuple<Integer, Attack> attack : specie.movementsByLevel) {
            if(attack.first <= level && rand.nextBoolean()) {
                attacks[index++] = attack.second;
            }
            if(index == 4)
                break;
        }
        if(index == 0)
            attacks[0] = new Attack("Punch", Type.NORMAL, 15, 100, "Generic attack", Effect.NONE, 0) ;
        return attacks;
    }
    private boolean determineShiny() {
        return random.nextInt(100) == 1;
    }
    public Stats getStats() {
        this.updateStats();
        return this.stats;
    }
    public void setLife(int life) {
        this.updateStats();
        assert life < this.stats.life;
        this.currentLife = life;
        if(currentLife < 0)
            currentLife = 0;
    }
    public void setAttack(int attack) {
        this.updateStats();
        this.stats.attack = attack;
    }
    public void setDefense(int defense) {
        this.updateStats();
        this.stats.defense = defense;
    }
    public void setSpeed(int speed) {
        this.updateStats();
        this.stats.speed = speed;
    }

    private void updateStats() {
        if(this.currentLife < 0)
            this.currentLife = 0;
        int life = (((2 * specie.baseStats.life + ivs.life + (evs.life / 4)) * level) / 100) + level + 10;
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
        this.stats = new Stats(life, attack, defense, speed);
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
        this.evs.speed += evsToIncrement.speed;
        if(this.evs.speed > 252)
            this.evs.speed = 252;

        this.evs.life += evsToIncrement.life;
        if(this.evs.life > 252)
            this.evs.life = 252;

        this.evs.attack += evsToIncrement.attack;
        if(this.evs.attack > 252)
            this.evs.attack = 252;

        this.evs.defense += evsToIncrement.defense;
        if(this.evs.defense > 252)
            this.evs.defense = 252;
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
            checkLearnMove();
            updateStats();
        }
        if(specie.evolutionsByLevel.isEmpty()) {
            return;
        }
        Integer levelToEvolve = specie.evolutionsByLevel.get(0).first;
        if(levelToEvolve <= this.level) {
            Specie specieToEvolve = specie.evolutionsByLevel.get(0).second;
            evolve(specieToEvolve);
            updateStats();
            checkLearnMove();
        }
    }
    private void checkLearnMove() {
        Scanner sc = new Scanner(System.in);
        for(Tuple<Integer, Attack> attackToLearn : specie.movementsByLevel) {
            if(attackToLearn.first == this.level) {
                System.out.println("Want to lean:" + attackToLearn.second.name + "? (1/0)");
                if(sc.nextInt() == 1) {
                    System.out.println("Choose a move to forget: 1-4");
                    int input = sc.nextInt();
                    if(input < 1 || input > 4)
                        throw new IllegalArgumentException("Invalid move");
                    this.attacks[input - 1] = attackToLearn.second;
                } else {
                    System.out.println("Move not learned");
                }
            }
        }
    }
    private void evolve(Specie specieToEvolve) {
        this.specie = specieToEvolve;
        this.name = specieToEvolve.name;
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
            if(attack == null)
                continue;
            if(attack.name.equals("Punch"))
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
        this.updateStats();
        return this.currentLife == 0;
    }
    public void heal() {
        this.updateStats();
        setLife(this.stats.life);
    }

    public int getCantAttacks() {
        int cantAttacks = 0;
        for(Attack attack : this.attacks) {
            if(attack != null)
                cantAttacks++;
        }
        return cantAttacks;
    }
}

