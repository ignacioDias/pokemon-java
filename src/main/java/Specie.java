import java.util.List;
import java.util.Random;

public class Specie {
    int id;
    String name;
    List<Tuple<EvolutionMethod, Specie>> evolutions;
    List<Tuple<Integer,Attack>> movementsByLevel; //(level, attack)
    List<Tuple<LearnMethod,Attack>> movementsByOtherWays;
    Type firstType;
    Type secondType;
    Stats baseStats;
    List<Sex> availableSexes;
    Stats evsGivenAfterDefeat;

    public Specie(int id, String name, List<Tuple<EvolutionMethod, Specie>> evolutions, List<Tuple<Integer, Attack>> movementsByLevel, List<Tuple<LearnMethod,Attack>>movementsByOtherWays, Type firstType, Type secondType, Stats baseStats, List<Sex> availableSexes, Stats evsGivenAfterDefeat) {
        this.id = id;
        this.name = name;
        this.evolutions = evolutions;
        this.movementsByLevel = movementsByLevel;
        this.movementsByOtherWays = movementsByOtherWays;
        this.firstType = firstType;
        this.secondType = secondType;
        this.baseStats = baseStats;
        this.availableSexes = availableSexes;
        this.evsGivenAfterDefeat = evsGivenAfterDefeat;
    }
    public Specie(Specie specieToCopy) {
        this.id = specieToCopy.id;
        this.name = specieToCopy.name;
        this.evolutions = specieToCopy.evolutions;
        this.movementsByLevel = specieToCopy.movementsByLevel;
        this.movementsByOtherWays = specieToCopy.movementsByOtherWays;
        this.firstType = specieToCopy.firstType;
        this.secondType = specieToCopy.secondType;
        this.baseStats = specieToCopy.baseStats;
        this.availableSexes = specieToCopy.availableSexes;

    }
    public boolean repOK() {
        //todo: check no attacks with wrong levels
        return !(firstType == Type.NONE || id < 0 || name.isEmpty() || firstType == null || movementsByLevel == null || movementsByOtherWays == null || movementsByOtherWays.isEmpty() || availableSexes == null || availableSexes.isEmpty() || movementsByLevel.isEmpty());
    }

    public Attack[] generateAttacks(int level) {
        Random rand = new Random();
        int index = 0;
        Attack[] attacks = {null, null, null, null};
        for(Tuple<Integer, Attack> attack : movementsByLevel) {
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
}
