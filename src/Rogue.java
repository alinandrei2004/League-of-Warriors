public class Rogue extends Character{
    public Rogue(String name, int experience, int level, int health, int mana, int strength, int charisma, int dexterity) {
        super(name, experience, level, health, mana, strength, charisma, dexterity, true, false, false);
    }

    @Override
    public int getDamage() {
        boolean crit = Math.random() < 0.5;
        int baseDmg = (int) (strength * 0.3 + dexterity * 0.5 + charisma * 0.2);
        if (crit) {
            return baseDmg * 2;
        }
        return baseDmg;
    }

    @Override
    public void receiveDamage(int damage) {
        boolean luck = Math.random() < 0.4;
        if (luck) {
            health -= damage / 2;
        } else {
            health -= damage;
        }

        if (health < 0) {
            health = 0;
        }
    }
}
