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
            // Read JSON content from file
            String content = new String(Files.readAllBytes(Paths.get(accountPath)));

            // Parse JSON content
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);

            // Retrieve the accounts array
            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (Object accObj : accountsArray) {
                JSONObject accountJson = (JSONObject) accObj;

                // name, country, games_number
                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int gamesNumber = Integer.parseInt((String) accountJson.get("maps_completed"));

                // Credentials
                Credentials credentials = null;
                try {
                    JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");

                    credentials = new Credentials(email, password);
                } catch (Exception e) {
                    System.out.println("! This account doesn't have all credentials !");
                }

                // Favorite games
                SortedSet<String> favoriteGames = new TreeSet<>();
                try {
                    JSONArray games = (JSONArray) accountJson.get("favorite_games");
                    for (Object game : games) {
                        favoriteGames.add((String) game);
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have favorite games !");
                }

                // Characters
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
                        switch (profession) {
                            case "Warrior":
                                newCharacter = new Warrior(cname, experience, lvl, 90, 20, 50, 10, 2);
                                break;
                            case "Rogue":
                                newCharacter = new Rogue(cname, experience, lvl, 80, 30, 40, 20, 5);
                                break;
                            case "Mage":
                                newCharacter = new Mage(cname, experience, lvl, 70, 50, 10, 20, 10);
                                break;
                            default:
                                System.out.println("Unknown profession: " + profession);
                        }

                        if (newCharacter != null) {
                            characters.add(newCharacter);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have characters !");
                }

                // Create Information and Account objects
                Information information = new Information(credentials, favoriteGames, name, country);
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
