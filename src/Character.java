public abstract class Character extends Entity{

    protected String name;
    protected int xp, level;
    protected int strength, charisma, dexterity, damage;

    public Character(int maxPlayerHealth, int maxPlayerMana, String name, int experience, int level, int health, int mana, int strength, int charisma, int dexterity, boolean fireImmune, boolean iceImmune, boolean earthImmune) {
        super(maxPlayerHealth, maxPlayerMana, health, mana, fireImmune, iceImmune, earthImmune);
        this.name = name;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.xp = experience;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getXP() {
        return xp;
    }

    public void addXP(int xp) {
        this.xp += xp;
    }

    @Override
    public abstract void receiveDamage(int damage);

    @Override
    public abstract int getDamage();

    public void levelUp() {
        xp += level * 5;
        level++;
        maxHealth += 10;
        maxMana += 5;
        strength += 2;
        charisma += 1;
        dexterity += 1;
        System.out.println(GREEN + "Congrats! Now your level is: " + RESET + level);

    }

    public void doubleHeatlh() {
        health *= 2;
        if(health > maxHealth) {
            health = maxHealth;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Health: " + health + ", Mana: " + mana + ", Strength: " + strength + ", Charisma: " + charisma + ", Dexterity: " + dexterity;
    }
}
