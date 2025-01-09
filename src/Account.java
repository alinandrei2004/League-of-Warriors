import java.util.ArrayList;

public class Account {
    private Information information;
    private ArrayList<Character> characters;
    private int nGames;
    public int nKills = 0;

    public Account(ArrayList<Character> characters, int nGames, Information information) {
        this.information = information;
        this.nGames = nGames;
        this.characters = characters;
    }

    @Override
    public String toString() {
        return " Account{ " +
                " information= " + information +
                ", gamesPlayed= " + information.getFavoriteGames() +
                ", characters= " + characters +
                " } ";
    }

    public Information getInformation() {
        return information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }
}
