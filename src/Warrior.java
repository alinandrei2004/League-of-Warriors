public class Warrior extends Character implements Battle {

    public Warrior(String name, int health, int mana, int strength, int charisma, int dexterity) {
        super(name, health, mana, strength, charisma, dexterity);
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
            return baseDmg * 2;
        }
        return baseDmg;
    }

}
