public interface Fighter {
    public boolean canFight();
    public Pokemon getNextPokemon();
    public Attack getNextAttack();
    public void killPokemon();
    public String getName();
}
