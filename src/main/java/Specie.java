import java.util.List;

public class Specie {
    int id;
    String name;
    List<Tuple<EvolutionMethod, Specie>> evolutions;
    List<Tuple<Integer,Attack>> movementsByLevel; //(level, attack)
    List<Attack> movementsByOtherWays;
    Type firstType;
    Type secondType;
    Stats baseStats;
    List<Sex> availableSexes;

    public Specie(int id, String name, List<Tuple<EvolutionMethod, Specie>> evolutions, List<Tuple<Integer, Attack>> movementsByLevel, List<Attack> movementsByOtherWays, Type firstType, Type secondType, Stats baseStats, List<Sex> availableSexes) {
        this.id = id;
        this.name = name;
        this.evolutions = evolutions;
        this.movementsByLevel = movementsByLevel;
        this.movementsByOtherWays = movementsByOtherWays;
        this.firstType = firstType;
        this.secondType = secondType;
        this.baseStats = baseStats;
        this.availableSexes = availableSexes;
    }

    public boolean repOK() {
        return !(id < 0 || name.isEmpty() || firstType == null || movementsByLevel == null || movementsByOtherWays == null);
    }

}
