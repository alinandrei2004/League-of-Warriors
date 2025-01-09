public class Ice extends Spell{

    public Ice(int damage, int manaCost) {
        super(damage, manaCost);
    }

    @Override
    public String toString() {
        return "[Ice]:" + "    Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
    }
}
