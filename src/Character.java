public abstract class Character extends Entity{

    protected String name;
    protected int xp, level;
    protected int strength, charisma, dexterity, damage;

    public Character(String name, int experience, int level, int health, int mana, int strength, int charisma, int dexterity, boolean fireImmune, boolean iceImmune, boolean earthImmune) {
        super(health, mana, fireImmune, iceImmune, earthImmune);
        this.name = name;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.xp = experience;
        this.level = level;
    }

    @Override
    public abstract void receiveDamage(int damage);
//        boolean luck = Math.random() < 0.25;
//        if (luck) {
//            health -= damage / 2;
//        } else {
//            health -= damage;
//        }
//
//        if (health <= 0) {
//            health = 0;
//        }
//    }

    @Override
    public abstract int getDamage();
//        boolean crit = Math.random() < 0.5;
//        int baseDmg = (int) (strength * 0.5 + dexterity * 0.3 + charisma * 0.2);
//        if (crit) {
//            return baseDmg * 2;
//        }
//        return baseDmg;
//    }

//    public abstract void generateDamage();

    void levelUp() {
        level++;
        maxHealth += 10;
        maxMana += 5;
        strength += 2;
        charisma += 1;
        dexterity += 1;
        System.out.println("Congrats! Now your level is: " + level);

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Health: " + health + ", Mana: " + mana + ", Strength: " + strength + ", Charisma: " + charisma + ", Dexterity: " + dexterity + abilities;
    }
}
