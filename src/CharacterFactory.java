public class CharacterFactory {
    public static Character createCharacter(String profession, String name, int experience, int level) {
        int health = 0, mana = 0, strength = 0, charisma = 0, dexterity = 0;
        health = 100 + level * 10;
        mana = 50 + level * 5;
        strength = 10 + level * 2;
        charisma = 10 + level;
        dexterity = 10 + level;
        Character newCharacter = null;
        switch (profession) {
            case "Warrior":
                newCharacter = new Warrior(name, experience, level, health, mana, strength, charisma, dexterity);
                break;
            case "Rogue":
                newCharacter = new Rogue(name, experience, level, health, mana, strength, charisma, dexterity);
                break;
            case "Mage":
                newCharacter = new Mage(name, experience, level, health, mana, strength, charisma, dexterity);
                break;
            default:
                System.out.println("Unknown profession: " + profession);
        }
        return newCharacter;
    }
}