import java.util.List;
import java.util.Random;

public class Pokemon {

    String name;
    Stats stats;
    Specie specie;
    int level;
    Attack[] attacks = new Attack[4];
    Sex sex;
    Nature nature;
    boolean isShiny;
    int currentExperience;
    private final Random random = new Random();

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

        if(!repOk())
            throw new IllegalArgumentException("Pokemon doesn't sat repOk");
    }
    private boolean determineShiny() {
        return random.nextInt(100) == 1;
    }
    private Stats calculateStats() {
        return new Stats(1,1,1,1,1,1); //TODO
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
    public boolean repOk() {
        if(this.attacks == null || this.attacks.length == 0)
            return false;
        for (Attack attack : this.attacks) {
            boolean attackLearnedByLevel = attackExistsInList(this.specie.movementsByLevel, attack);
            boolean attackLearnedByOtherWay = attackExistsInList(this.specie.movementsByOtherWays, attack);

            if(!attackLearnedByLevel && !attackLearnedByOtherWay)
                return false;
        }
        return sex != null && nature != null && stats != null && level > 0 && level <= 100 && !name.isEmpty() && specie != null && currentExperience >= 0 && currentExperience < calculateCurrentMaxExperience();
    }

    private <T> boolean attackExistsInList(List<Tuple<T, Attack>> movements, Attack attack) {
        for (Tuple<T, Attack> movement : movements) {
            if (movement.getSecond().equals(attack)) {
                return true;
            }
        }
        return false;
    }
}

