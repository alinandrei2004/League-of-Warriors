import java.util.ArrayList;
import java.util.Random;

public abstract class Spell {

    Random rand = new Random();
    protected int AbilityDamage, manaCost;
    protected ArrayList<Spell> abilities = new ArrayList<>();
    protected int nAbilities = rand.nextInt(3, 7);

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

//    public void generateAbilities() {
//        for (int i = 0; i < nAbilities; i++) {
//            int type = rand.nextInt(1, 4);
//            int damage = rand.nextInt(15, 21);
//            int manaCost = rand.nextInt(10) + 1;
//            switch (type) {
//                case 1:
//                    abilities.add(new Fire(damage, manaCost));
//                    break;
//                case 2:
//                    abilities.add(new Ice(damage, manaCost));
//                    break;
//                case 3:
//                    abilities.add(new Earth(damage, manaCost));
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

    public String toString() {
        if (this instanceof Fire) {
            return "[Fire]:  Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
        } else if (this instanceof Ice) {
            return "[Ice]:   Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
        } else if (this instanceof Earth) {
            return "[Earth]: Damage: " + AbilityDamage + ", Mana cost: " + manaCost;
        }
        return "";
    }
}
