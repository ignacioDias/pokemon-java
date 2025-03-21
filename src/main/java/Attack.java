public class Attack {
    String name;
    int damage;
    Type type;
    TypeAttack typeAttack;
    int accuracy;
    
    public Attack(String name, int damage, Type type, TypeAttack typeAttack, int accuracy) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.typeAttack = typeAttack;
        this.accuracy = accuracy;
    }
    boolean repOK() {
        return (damage == 0 && typeAttack != TypeAttack.STATE) || (damage != 0 && typeAttack == TypeAttack.STATE) && !name.isEmpty();
    }
}
