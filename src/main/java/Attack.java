public class Attack {
    String name;
    int damage;
    Type type;
    TypeAttack typeAttack;
    public Attack(String name, int damage, Type type, TypeAttack typeAttack) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.typeAttack = typeAttack;
    }
    boolean repOK() {
        return (damage == 0 && typeAttack != TypeAttack.STATE) || (damage != 0 && typeAttack == TypeAttack.STATE) && !name.isEmpty();
    }
}
