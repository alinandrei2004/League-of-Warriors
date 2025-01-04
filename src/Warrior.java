public class Warrior extends Character {

    public Warrior(int maxHealth, int maxMana ,String name, int experience, int level, int health, int mana, int strength, int charisma, int dexterity) {
        super(maxHealth, maxMana, name, experience, level, health, mana, strength, charisma, dexterity, true, false, false);
    }

    @Override
    public void receiveDamage(int damage) {
        boolean luck = Math.random() < 0.3;
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
        int baseDmg = (int) (strength * 0.75 + dexterity * 0.15 + charisma * 0.1);
        if (crit) {
            System.out.println(RED + "The Warrior feels powerful today" + RESET);
            return baseDmg * 2;
        }
        return baseDmg;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Health: " + health + ", Mana: " + mana + ", Strength: " + strength + ", Charisma: " + charisma + ", Dexterity: " + dexterity + "; Immunity: Fire, " + "Level: " + super.getLevel() + ", Experience: " + super.getXP();
    }

}
