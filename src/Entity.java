import java.util.ArrayList;

public abstract class Entity implements Battle{
    protected int health, maxHealth, mana, maxMana;
    protected ArrayList<Spell> abilities;
    protected boolean fireImmune, iceImmune, earthImmune;

    public Entity(int health, int mana) {
        this.health = health;
        this.mana = mana;
        this.abilities = new ArrayList<>();
        this.maxHealth = 100;
        this.maxMana = 100;
    }

    public void regenH(int value) {
        health += value;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void regenM(int value) {
        mana += value;
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void useAbility(Spell spell, Entity target) {
        if (mana >= spell.getManaCost()) {
            mana -= spell.getManaCost();
            target.receiveDamage(spell.getDamage());
        }
    }
}
