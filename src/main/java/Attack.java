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

    public Attack(String name, Type type, int accuracy, String description, Effect effect, int probabilityOfEffect) {
        this.name = name;
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
            rival.stats.life -= damage;
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
        double modifier = 1;
        if(attacker.specie.firstType == this.type || attacker.specie.secondType == this.type)
            modifier = 1.5;
        if(isWeakToAttack(victim.specie.firstType))
            modifier *= 2;
        if(isWeakToAttack(victim.specie.secondType))
            modifier *= 2;
        if(isStrongToAttack(victim.specie.firstType))
            modifier /= 2;
        if(isStrongToAttack(victim.specie.secondType))
            modifier /= 2;
        return (int) ((((double) (((2 * attacker.level) / 5 + 2) * this.damage * attacker.stats.attack / victim.stats.defense) / 50) + 2 ) * modifier) + rand.nextInt(victim.stats.life / 10);
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
        if(effect == Effect.NONE) {
            throw new IllegalArgumentException("effect is NONE in applyEffect");
        } else if(effect == Effect.BURN) {
            victim.stats.attack /= 2;
        } else if(effect == Effect.PARALYSIS) {
            victim.stats.speed /= 2;
        }
        victim.state = effect;
    }
    public boolean repOk() {
        return !(type == Type.NONE || damage < 0 || (effect == Effect.NONE && isStatusAttack) ||
                (isStatusAttack && damage > 0) || (!isStatusAttack && damage == 0) ||
                accuracy < 0 || accuracy > 100 || probabilityOfEffect < 0 || probabilityOfEffect > 100 ||
                ((probabilityOfEffect == 0) == (effect == Effect.NONE)));
    }
}
