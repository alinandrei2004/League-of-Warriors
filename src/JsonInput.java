import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class JsonInput {
    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "/home/alin/Desktop/League-of-Warriors/accounts.json";

        try {
            String content = new String(Files.readAllBytes(Paths.get(accountPath)));

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);

            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (Object accObj : accountsArray) {
                JSONObject accountJson = (JSONObject) accObj;

                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt((String) accountJson.get("maps_completed"));

                Credentials credentials = null;
                try {
                    JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");

                    credentials = new Credentials(email, password);
                } catch (Exception e) {
                    System.out.println("! This account doesn't have all credentials !");
                }

                SortedSet<String> favoriteGames = new TreeSet<>();
                try {
                    JSONArray games = (JSONArray) accountJson.get("favorite_games");
                    for (Object game : games) {
                        favoriteGames.add((String) game);
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have favorite games !");
                }

                ArrayList<Character> characters = new ArrayList<>();
                try {
                    JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                    for (Object charObj : charactersListJson) {
                        JSONObject charJson = (JSONObject) charObj;

                        String cname = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");
                        int lvl = Integer.parseInt((String) charJson.get("level"));
                        int experience = Integer.parseInt(String.valueOf(charJson.get("experience")));

                        Character newCharacter = null;
                        newCharacter = CharacterFactory.createCharacter(profession, cname, experience, lvl);

                        if (newCharacter != null) {
                            characters.add(newCharacter);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have characters !");
                }

                Information information = new Information.Builder()
                        .addCredentials(credentials)
                        .addFavoriteGames(favoriteGames)
                        .addName(name)
                        .addCountry(country)
                        .build();
                Account account = new Account(characters, gamesNumber, information);
                accounts.add(account);
            }
            return accounts;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
