import java.util.List;

public class Specie {
    int id;
    String name;
    List<Tuple<EvolutionMethod, Specie>> evolutionsByOtherMethods;
    List<Tuple<Integer, Specie>> evolutionsByLevel;
    List<Tuple<Integer,Attack>> movementsByLevel; //(level, attack)
    List<Tuple<LearnMethod,Attack>> movementsByOtherWays;
    Type firstType;
    Type secondType;
    Stats baseStats;
    List<Sex> availableSexes;
    Stats evsGivenAfterDefeat;

    public Specie(int id, String name, List<Tuple<Integer, Specie>> evolutionsByLevel, List<Tuple<EvolutionMethod, Specie>> evolutionsByOtherMethods, List<Tuple<Integer, Attack>> movementsByLevel, List<Tuple<LearnMethod,Attack>>movementsByOtherWays, Type firstType, Type secondType, Stats baseStats, List<Sex> availableSexes, Stats evsGivenAfterDefeat) {
        this.id = id;
        this.name = name;
        this.evolutionsByLevel = evolutionsByLevel;
        this.evolutionsByOtherMethods = evolutionsByOtherMethods;
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
        this.evolutionsByLevel = specieToCopy.evolutionsByLevel;
        this.evolutionsByOtherMethods = specieToCopy.evolutionsByOtherMethods;
        this.movementsByLevel = specieToCopy.movementsByLevel;
        this.movementsByOtherWays = specieToCopy.movementsByOtherWays;
        this.firstType = specieToCopy.firstType;
        this.secondType = specieToCopy.secondType;
        this.baseStats = specieToCopy.baseStats;
        this.availableSexes = specieToCopy.availableSexes;
        this.evsGivenAfterDefeat = specieToCopy.evsGivenAfterDefeat;
    }
    public boolean repOK() {
        //todo: check no attacks with wrong levels
        return !(firstType == Type.NONE || id < 0 || name.isEmpty() || firstType == null || movementsByLevel == null ||
                availableSexes == null || availableSexes.isEmpty() || movementsByLevel.isEmpty() ||
                evsGivenAfterDefeat == null);
    }

}
