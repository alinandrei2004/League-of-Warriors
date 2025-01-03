import java.util.Set;
import java.util.TreeSet;

public class Information {
    private Credentials credentials;
    private Set<String> favoriteGames;
    private String name;
    private String country;

    private Information(Builder builder) {
        this.credentials = builder.credentials;
        this.favoriteGames = builder.favoriteGames;
        this.name = builder.name;
        this.country = builder.country;
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

    public static class Builder {
        private Credentials credentials;
        private Set<String> favoriteGames;
        private String name;
        private String country;

        public Builder addCredentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public Builder addName(String name) {
            this.name = name;
            return this;
        }

        public Builder addCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder addFavoriteGames(Set<String> favoriteGames) {
            if (this.favoriteGames == null) {
                this.favoriteGames = favoriteGames;
            }
            else {
                this.favoriteGames.addAll(favoriteGames);
            }
            return this;
        }


        public Information build() {
            return new Information(this);
        }
    }

}
