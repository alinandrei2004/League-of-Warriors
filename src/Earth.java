public class Earth extends Spell{

    public Earth(int damage, int manaCost) {
        super(damage, manaCost);
    }

    @Override
    public String toString() {
        return "[Earth]:" + "  Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
    }
}
