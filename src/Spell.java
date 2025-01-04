import java.util.ArrayList;
import java.util.Random;

public abstract class Spell implements Visitor<Entity>{

    Random rand = new Random();
    protected int AbilityDamage, manaCost;
    protected ArrayList<Spell> abilities = new ArrayList<>();
    protected int nAbilities = rand.nextInt(3, 7);

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    public Spell(int damage, int manaCost) {
        this.AbilityDamage = damage;
        this.manaCost = manaCost;
    }

    public int getDamage() {
        return AbilityDamage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String toString() {
        if (this instanceof Fire) {
            return RED + "[Fire]:" + RESET + "   Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
        } else if (this instanceof Ice) {
            return BLUE + "[Ice]:" + RESET + "    Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
        } else if (this instanceof Earth) {
            return GREEN + "[Earth]:" + RESET + "  Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
        }
        return "";
    }

    @Override
    public void visit(Entity entity) {
        entity.receiveDamage(AbilityDamage);
    }
}
