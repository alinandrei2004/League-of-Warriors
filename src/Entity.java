import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle{

    Random rand = new Random();
    protected int health, maxHealth, mana, maxMana;
    protected ArrayList<Spell> abilities;
    protected int nAbilities = rand.nextInt(3, 7);
    protected boolean fireImmune, iceImmune, earthImmune;
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";

    public Entity(int health, int mana, boolean fireImmune, boolean iceImmune, boolean earthImmune) {
        this.health = health;
        this.mana = mana;
        this.abilities = new ArrayList<>();
        this.maxHealth = 100;
        this.maxMana = 100;
        this.fireImmune = fireImmune;
        this.iceImmune = iceImmune;
        this.earthImmune = earthImmune;
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

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public void generateAbilities() {
        for (int i = 0; i < nAbilities; i++) {
            int type = rand.nextInt(1, 4);
            int damage = rand.nextInt(15, 21);
            int manaCost = rand.nextInt(10) + 1;
            switch (type) {
                case 1:
                    abilities.add(new Fire(damage, manaCost));
                    break;
                case 2:
                    abilities.add(new Ice(damage, manaCost));
                    break;
                case 3:
                    abilities.add(new Earth(damage, manaCost));
                    break;
                default:
                    break;
            }
        }
    }

    public void useAbility(Spell spell, Entity target) {
        if (mana >= spell.getManaCost()) {
            mana -= spell.getManaCost();
            target.receiveDamage(spell.getDamage());
        }
    }
}
