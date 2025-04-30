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
        if(effect != Effect.NONE && AuxiliarMethods.calculateProbability(this.probabilityOfEffect))
            applyEffect(this.effect, rival);
        if(!isStatusAttack) {
            int damage = calculateDamage(attacker, rival);
            rival.stats.life -= damage;
        }
        return true;
    }
    private int calculateDamage(Pokemon attacker, Pokemon victim) {
        Random rand = new Random();
        double modifier = 1;
        if(attacker.specie.firstType == this.type || attacker.specie.secondType == this.type)
            modifier = 1.5;
        if(isWeakToAttack(victim.specie.firstType))
            modifier *= 2;
        if(isWeakToAttack(attacker.specie.secondType))
            modifier *= 2;
        return (int) ((((double) (((2 * attacker.level) / 5 + 2) * this.damage * attacker.stats.attack / victim.stats.defense) / 50) + 2 ) * modifier) + rand.nextInt(victim.stats.life / 10);
    }
    private boolean isWeakToAttack(Type victimType) {
        switch(this.type) {
            case FIRE:
                return victimType == Type.GRASS || victimType == Type.BUG || victimType == Type.ICE || victimType == Type.STEAL;
            case WATER:
                return victimType == Type.FIRE || victimType == Type.GROUND || victimType == Type.ROCK;
            case GRASS:
                return victimType == Type.WATER || victimType == Type.GROUND || victimType == Type.ROCK;
            case ELECTRIC:
                return victimType == Type.WATER || victimType == Type.FLYING;
            case FLYING:
                return victimType == Type.FIGHT || victimType == Type.GRASS || victimType == Type.BUG;
            case FIGHT:
                return victimType == Type.NORMAL || victimType == Type.ROCK || victimType == Type.STEAL || victimType == Type.ICE || victimType == Type.DARK;
            case ROCK:
                return victimType == Type.FIRE || victimType == Type.FLYING || victimType == Type.BUG || victimType == Type.ICE;
            case STEAL:
                return victimType == Type.PSYCHIC || victimType == Type.ROCK || victimType == Type.BUG;
            case GROUND:
                return victimType == Type.FIRE || victimType == Type.ELECTRIC || victimType == Type.POISON || victimType == Type.STEAL || victimType == Type.ROCK;
            case GHOST:
                return victimType == Type.GHOST || victimType == Type.PSYCHIC;
            case DARK:
                return victimType == Type.PSYCHIC || victimType == Type.FIGHT;
            case PSYCHIC:
                return victimType == Type.FIGHT || victimType == Type.POISON;
            case FAIRY:
                return victimType == Type.DRAGON || victimType == Type.FIGHT || victimType == Type.POISON;
            case DRAGON:
                return victimType == Type.FAIRY || victimType == Type.ICE || victimType == Type.DRAGON;
            case ICE:
                return victimType == Type.FIRE || victimType == Type.FIGHT || victimType == Type.STEAL || victimType == Type.ROCK;
            case BUG:
                return victimType == Type.FIRE || victimType == Type.FLYING || victimType == Type.ROCK;
            case POISON:
                return victimType == Type.GROUND || victimType == Type.PSYCHIC;
            default:
                throw new IllegalArgumentException("illegal type of attack"); // NONE or unrecognized type
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
