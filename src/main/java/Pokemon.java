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
    Random random = new Random();

    public Pokemon(Stats stats, Specie specie, int level, Attack[] attacks) {


        this.name = specie.name;
        this.stats = stats;
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
    }
    private boolean determineShiny() {
        return random.nextInt(100) == 1;
    }
    private Stats calculateStats() {
        return null; //TODO
    }
    public void increaseCurrentExperience(int experience) {
        int potentialNewExp = this.currentExperience + experience;
        int currentMax = calculateCurrentMaxExperience();
        if(potentialNewExp >= currentMax) {
            this.levelUp();
            increaseCurrentExperience(currentMax - experience);
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
        for (Attack attack : this.attacks) {
            boolean attackLearnedByLevel = attackExistsInList(this.specie.movementsByLevel, attack);
            boolean attackLearnedByOtherWay = attackExistsInList(this.specie.movementsByOtherWays, attack);

            if(!attackLearnedByLevel && !attackLearnedByOtherWay) {
                return false;
            }
        }
        return sex != null && nature != null && stats != null && (level <= 0 || level > 100) && !name.isEmpty() && specie != null && currentExperience >= 0 && currentExperience < calculateCurrentMaxExperience();
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

