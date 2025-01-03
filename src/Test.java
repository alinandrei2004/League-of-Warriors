import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.System.exit;
import static java.lang.System.in;

public class Test {

    public static void main(String[] args) throws ImpossibleMove, NoAbilities, ParseException, IOException {
        String email;
        String password;
        Account selectedAccount = null;
        int i = 0;


        Scanner input = new Scanner(System.in);

        ArrayList<Account> accounts = JsonInput.deserializeAccounts();

        if (accounts != null) {
            for (Account account : accounts) {
                Credentials credentials = account.getInformation().getCredentials();
                if (credentials != null) {
                    System.out.println("Email: " + credentials.getEmail());
                    System.out.println("Password: " + credentials.getPassword());
                    System.out.println("----------------------");
                }
            }
        } else {
            System.out.println("No accounts were loaded.");
        }

        boolean ok = false;

        while(!ok) {
            System.out.println("Enter your email: ");
            email = input.next();
            System.out.println("Enter your password: ");
            password = input.next();
            for (Account account : accounts) {
                Credentials credentials = account.getInformation().getCredentials();
                if (credentials != null) {
                    if (credentials.getEmail().equals(email) && credentials.getPassword().equals(password)) {
                        System.out.println("You are logged in!");
                        ok = true;
                        selectedAccount = account;
                        break;
                    }
                }
            }
            if (!ok) {
                System.out.println("Invalid email or password!");
            }
        }

        System.out.println("Characters for the selected account: " + selectedAccount.getInformation().getName());
        System.out.println("Select a character: ");
        ArrayList<Character> characters = selectedAccount.getCharacters();
        for (Character c : characters) {
            i++;
            System.out.println(i + ") " + c.toString());
        }
        int nCharacter = input.nextInt();
        Character selectedCharacter = characters.get(nCharacter - 1);

        Game game = Game.StartGame(selectedCharacter);
        game.runHard();
    }
}
