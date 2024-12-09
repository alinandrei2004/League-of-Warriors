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

public class Test {

    public static void main(String[] args) throws ImpossibleMove, NoAbilities, ParseException, IOException {
        String email;
        String password;


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
        for (Account account : accounts) {
            Credentials credentials = account.getInformation().getCredentials();
            if (credentials != null) {
                while (!ok) {
                    System.out.println("Enter your email: ");
                    email = input.next();
                    System.out.println("Enter your password: ");
                    password = input.next();
                    if (credentials.getEmail().equals(email) && credentials.getPassword().equals(password)) {
                        System.out.println("You are logged in!");
                        ok = true;
                        break;
                    } else {
                        System.out.println("Invalid email or password!");
                    }
                }
            }
        }

        Game game = new Game();
        game.runHard();
    }
}
