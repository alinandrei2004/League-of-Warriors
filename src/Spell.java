public abstract class Spell {

    protected int damage, manaCost;

    public Spell(int damage, int manaCost) {
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String toString() {
        return "Damage: " + damage + ", Mana cost: " + manaCost;
    }
}
