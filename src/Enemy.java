import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    Random rand = new Random();
//    private int fireImmune, iceImmune, earthImmune;
//    private int nAbilities = rand.nextInt( 3, 6) + 1;
//    private ArrayList<Spell> abilities = new ArrayList<>();

    public Enemy(int maxHealth, int maxMana, int health, int mana, boolean fireImmune, boolean iceImmune, boolean earthImmune) {
        super(maxHealth, maxMana, health, mana, fireImmune, iceImmune, earthImmune);
    }

    public void receiveDamage(int damage) {
        boolean chance = Math.random() < 0.5;
        if (!chance) {
            health -= damage;
        }
        else {
            System.out.println(RED + "The Enemy dodged the attack!" + RESET);
        }

    }

    public int getDamage() {
        int damage = rand.nextInt(10) + 1;
        boolean chance = Math.random() < 0.5;
        if (chance) {
            System.out.println(RED + "The Enemy feels powerful today" + RESET);
            return damage * 2;
        }
        return damage;
    }

    public String toString () {
        return "Enemy: Health: " + health + ", Mana: " + mana + ", Fire Immunity: " + fireImmune + ", Ice Immunity: " + iceImmune + ", Earth Immunity: " + earthImmune;
    }

    @Override
    public int regenH() {
        int value = rand.nextInt(10, 21);
        health += value;
        if (health > maxHealth) {
            health = maxHealth;
        }
        return value;
    }

    @Override
    public int regenM() {
        int value = rand.nextInt(10, 21);
        mana += value;
        if (mana > maxMana) {
            mana = maxMana;
        }
        return value;
    }

    @Override
    public void useAbility(Spell spell, Entity target) {
        if (mana >= spell.getManaCost()) {
            mana -= spell.getManaCost();
//            target.receiveDamage(spell.getDamage());
        } else {
            System.out.println(RED + "Not enough mana!" + RESET);
        }
    }
}
