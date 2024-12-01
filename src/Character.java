public abstract class Character extends Entity{

    protected String name;
    protected int xp, level;
    protected int strength, charisma, dexterity;

    public Character(String name, int health, int mana, int strength, int charisma, int dexterity) {
        super(health, mana);
        this.name = name;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.xp = 0;
        this.level = 1;
    }

    @Override
    public void receiveDamage(int damage) {
        boolean luck = Math.random() < 0.25;
        if (luck) {
            health -= damage / 2;
        } else {
            health -= damage;
        }

        if (health <= 0) {
            health = 0;
        }
    }

    @Override
    public int getDamage() {
        boolean crit = Math.random() < 0.5;
        int baseDmg = (int) (strength * 0.5 + dexterity * 0.3 + charisma * 0.2);
        if (crit) {
            return baseDmg * 2;
        }
        return baseDmg;
    }

    void levelUp(int value) {
        xp += value;
        if (xp >= 100) {
            xp -= 100;
            level++;
            maxHealth += 10;
            maxMana += 5;
            strength += 2;
            charisma += 1;
            dexterity += 1;
        }
        System.out.println("Congrats! Now your level is: " + level);
    }
}
