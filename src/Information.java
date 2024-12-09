import java.util.Set;
import java.util.TreeSet;

public class Information {
    private Credentials credentials;
    private Set<String> favoriteGames;
    private String name;
    private String country;

    public Information(Credentials credentials,Set<String> favoriteGames, String name, String country) {
        this.credentials = credentials;
        this.name = name;
        this.country = country;
        this.favoriteGames = favoriteGames;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Set<String> getFavoriteGames() {
        return favoriteGames;
    }

    public void addFavoriteGame(String game) {
        favoriteGames.add(game);
    }

    @Override
    public String toString() {
        return " Information{ " +
                " name=' " + name + '\'' +
                ", country=' " + country + '\'' +
                ", credentials= " + credentials +
                ", favoriteGames= " + favoriteGames +
                " } ";
    }

}
