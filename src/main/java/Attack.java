import java.util.Random;
public class Attack {

    String name;
    Type type;
    int accuracy;
    String description;
    int damage;
    Effect effect;
    int probabilityOfEffect;
    boolean isStatusAttack;

    public Attack(String name, Type type, int damage, int accuracy, String description, Effect effect, int probabilityOfEffect) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.accuracy = accuracy;
        this.description = description;
        this.effect = effect;
        this.probabilityOfEffect = probabilityOfEffect;
    }

    public boolean attack(Pokemon attacker, Pokemon rival) {
        if(!AuxiliarMethods.calculateProbability(this.accuracy))
            return false; //attack missed
        if(isNotEffectiveAtAllToVictim(rival.specie.firstType) || isNotEffectiveAtAllToVictim(rival.specie.secondType))
            return false; //attack not effective
        if(effect != Effect.NONE && AuxiliarMethods.calculateProbability(this.probabilityOfEffect))
            applyEffect(this.effect, rival);
        if(!isStatusAttack) {
            int damage = calculateDamage(attacker, rival);
            rival.setLife(rival.currentLife - damage);
        }
        return true;
    }
    private boolean isNotEffectiveAtAllToVictim(Type type) {
        switch(this.type) {
            case POISON:
                return type == Type.STEEL;
            case GROUND:
                return type == Type.FLYING;
            case FIGHT:
            case NORMAL:
                return type == Type.GHOST;
            case GHOST:
                return type == Type.NORMAL;
            case PSYCHIC:
                return type == Type.DARK;
            case DRAGON:
                return type == Type.FAIRY;
            case ELECTRIC:
                return type == Type.GROUND;
            default:
                return false;
        }
    }
    private int calculateDamage(Pokemon attacker, Pokemon victim) {
        Random rand = new Random();

        Stats statsOfAttacker = attacker.getStats();
        Stats statsOfVictim = victim.getStats();
        // Protección contra división por 0
        if (statsOfVictim.defense == 0) {
            statsOfVictim.defense = 1;
        }
        // STAB (Same Type Attack Bonus)
        double modifier = 1.0;
        if (attacker.specie.firstType == this.type || attacker.specie.secondType == this.type) {
            modifier *= 1.5;
        }
        // Efectividad de tipo
        if (isWeakToAttack(victim.specie.firstType)) {
            modifier *= 2;
        }
        if (isWeakToAttack(victim.specie.secondType)) {
            modifier *= 2;
        }
        if (isStrongToAttack(victim.specie.firstType)) {
            modifier *= 0.5;
        }
        if (isStrongToAttack(victim.specie.secondType)) {
            modifier *= 0.5;
        }
        // Variación aleatoria entre 0.85 y 1.00
        double randomFactor = 0.85 + (0.15 * rand.nextDouble());
        // Fórmula de daño
        double baseDamage = (((((2 * attacker.level) / 5.0) + 2) * this.damage * statsOfAttacker.attack / statsOfVictim.defense) / 50.0) + 2;
        // Daño final
        int finalDamage = (int) (baseDamage * modifier * randomFactor);
        return Math.max(1, finalDamage); // mínimo 1 daño
    }

    private boolean isStrongToAttack(Type victimType) {
        switch (this.type) {
            case NORMAL:    return victimType == Type.ROCK || victimType == Type.STEEL;
            case FIRE:      return victimType == Type.FIRE || victimType == Type.WATER || victimType == Type.ROCK || victimType == Type.DRAGON;
            case WATER:     return victimType == Type.WATER || victimType == Type.GRASS || victimType == Type.DRAGON;
            case ELECTRIC:  return victimType == Type.ELECTRIC || victimType == Type.GRASS || victimType == Type.DRAGON;
            case GRASS:     return victimType == Type.FIRE || victimType == Type.GRASS || victimType == Type.POISON || victimType == Type.FLYING || victimType == Type.BUG || victimType == Type.DRAGON || victimType == Type.STEEL;
            case ICE:       return victimType == Type.FIRE || victimType == Type.WATER || victimType == Type.ICE || victimType == Type.STEEL;
            case FIGHT:     return victimType == Type.POISON || victimType == Type.FLYING || victimType == Type.PSYCHIC || victimType == Type.BUG || victimType == Type.FAIRY;
            case POISON:    return victimType == Type.POISON || victimType == Type.GROUND || victimType == Type.ROCK || victimType == Type.GHOST;
            case GROUND:    return victimType == Type.GRASS || victimType == Type.BUG;
            case FLYING:    return victimType == Type.ELECTRIC || victimType == Type.ROCK || victimType == Type.STEEL;
            case PSYCHIC:   return victimType == Type.PSYCHIC || victimType == Type.STEEL;
            case BUG:       return victimType == Type.FIRE || victimType == Type.FIGHT || victimType == Type.POISON || victimType == Type.FLYING || victimType == Type.GHOST || victimType == Type.STEEL || victimType == Type.FAIRY;
            case ROCK:      return victimType == Type.FIGHT || victimType == Type.GROUND || victimType == Type.STEEL;
            case GHOST:     return victimType == Type.DARK;
            case DRAGON:    return victimType == Type.STEEL;
            case DARK:      return victimType == Type.FIGHT || victimType == Type.DARK || victimType == Type.FAIRY;
            case STEEL:     return victimType == Type.FIRE || victimType == Type.WATER || victimType == Type.ELECTRIC || victimType == Type.STEEL;
            case FAIRY:     return victimType == Type.FIRE || victimType == Type.POISON || victimType == Type.STEEL;
            default:
                throw new IllegalArgumentException("Illegal or undefined attack type");
        }
    }

    private boolean isWeakToAttack(Type victimType) {
        switch (this.type) {
            case NORMAL:    return false;
            case FIRE:      return victimType == Type.GRASS || victimType == Type.ICE || victimType == Type.BUG || victimType == Type.STEEL;
            case WATER:     return victimType == Type.FIRE || victimType == Type.GROUND || victimType == Type.ROCK;
            case ELECTRIC:  return victimType == Type.WATER || victimType == Type.FLYING;
            case GRASS:     return victimType == Type.WATER || victimType == Type.GROUND || victimType == Type.ROCK;
            case ICE:       return victimType == Type.GRASS || victimType == Type.GROUND || victimType == Type.FLYING || victimType == Type.DRAGON;
            case FIGHT:     return victimType == Type.NORMAL || victimType == Type.ROCK || victimType == Type.STEEL || victimType == Type.ICE || victimType == Type.DARK;
            case POISON:    return victimType == Type.GRASS || victimType == Type.FAIRY;
            case GROUND:    return victimType == Type.FIRE || victimType == Type.ELECTRIC || victimType == Type.POISON || victimType == Type.ROCK || victimType == Type.STEEL;
            case FLYING:    return victimType == Type.FIGHT || victimType == Type.GRASS || victimType == Type.BUG;
            case PSYCHIC:   return victimType == Type.FIGHT || victimType == Type.POISON;
            case BUG:       return victimType == Type.GRASS || victimType == Type.PSYCHIC || victimType == Type.DARK;
            case ROCK:      return victimType == Type.FIRE || victimType == Type.ICE || victimType == Type.FLYING || victimType == Type.BUG;
            case GHOST:
            case DARK:      return victimType == Type.PSYCHIC || victimType == Type.GHOST;
            case DRAGON:    return victimType == Type.DRAGON;
            case STEEL:     return victimType == Type.ICE || victimType == Type.ROCK || victimType == Type.FAIRY;
            case FAIRY:     return victimType == Type.FIGHT || victimType == Type.DRAGON || victimType == Type.DARK;
            default:
                throw new IllegalArgumentException("Illegal or undefined attack type");
        }
    }

    private void applyEffect(Effect effect, Pokemon victim) throws IllegalArgumentException {
        Stats statsOfVictim = victim.getStats();
        if(victim.state != Effect.NONE) //Already affected by something
            return;
        if(effect == Effect.NONE) {
            throw new IllegalArgumentException("effect is NONE in applyEffect");
        } else if(effect == Effect.BURN) {
            victim.statsAffectedByStates.attack = 50;
        } else if(effect == Effect.PARALYSIS) {
            victim.statsAffectedByStates.speed = 50;
        }
        victim.state = effect;
    }
    public boolean repOk() {
        return !(type == Type.NONE || damage < 0 || (effect == Effect.NONE && isStatusAttack) ||
                (isStatusAttack && damage > 0) || (!isStatusAttack && damage == 0) ||
                accuracy < 0 || accuracy > 100 || probabilityOfEffect < 0 || probabilityOfEffect > 100 ||
                ((probabilityOfEffect == 0) == (effect == Effect.NONE)));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attack otherAttack = (Attack) o;
        return this.name.equals(otherAttack.name);
    }
}
