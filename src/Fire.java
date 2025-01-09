public class Fire extends Spell{

    public Fire(int damage, int manaCost) {
        super(damage, manaCost);
    }

    @Override
    public String toString() {
        return "[Fire]:" + "   Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
    }
}
