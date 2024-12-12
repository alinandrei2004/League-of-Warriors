import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ImpossibleMove, NoAbilities {
        String email;
        String password;
        Account selectedAccount = null;
        int i = 0;


        Scanner input = new Scanner(System.in);
        char q = 'a';
        while (q!='q') {
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

            while (!ok) {
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
            int nCharacter = 0;

            boolean valid = false;
            while(!valid) {
                try {
                    nCharacter = input.nextInt();
                    while (nCharacter > characters.size()) {
                        System.out.println("Wrong number");
                        nCharacter = input.nextInt();
                    }
                    valid = true;
                } catch (Exception e) {
                    System.out.println("Wrong input");
                    input.nextLine();
                }
            }
            Character selectedCharacter = characters.get(nCharacter - 1);

            Game game = new Game(selectedCharacter);
            game.run();
            System.out.println("Press q to quit or any other key to continue");
            q = input.next().charAt(0);
            while(!characters.isEmpty()) {
                characters.remove(0);
            }
            i = 0;
        }
    }
}