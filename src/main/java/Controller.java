import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static Scanner in;

    public static void main(String[] args) {
        in = new Scanner(System.in);

        while (true) {
            String input = getUserInput("Please choose an option: 1) Register 2) Login 3) Exit");
            if (!input.isBlank() && isDigit(input)) {
                if (Integer.parseInt(input) == 3) break;
                executeCommand(input);
            }
        }
    }

    private static String getUserInput(String prompt) {
        String input = "";

        System.out.println(prompt);
        if (in.hasNextLine()) {
            input = in.nextLine();
        }

        return input;
    }

    private static boolean isDigit(String value) {
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void register() {
        User user = getRegistrationInfo();

        if (isValidRegisterInfo(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword())) {
            user.addUser();
            System.out.println("Successfully registered.");
        } else {
            System.out.println("ERROR: Could not register. Please enter valid information.");
        }
    }

    private static User getRegistrationInfo() {
        System.out.println("---REGISTRATION---");

        String firstName = getUserInput("Please enter your first name:");
        String lastName = getUserInput("Please enter your last name:");
        String email = getUserInput("Please enter your email:");
        String password = getUserInput(
                "Please enter a password that follows the following guidelines:\n"+
                        "- 8-20 characters\n" +
                        "- contains at least one digit\n" +
                        "- contains at least one upper case alphabet\n" +
                        "- contains at least one lower case alphabet\n" +
                        "- contains at least one special character"
        );

        return new User(firstName, lastName, email, password);
    }

    private static boolean isValidRegisterInfo(String firstName, String lastName, String email, String password) {
        return !firstName.isBlank() &&
                !lastName.isBlank() &&
                !email.isBlank() && isValidEmailAddress(email) &&
                !password.isBlank() && isValidPassword(password);
    }

    private static boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException e) {
            return false;
        }

        return true;
    }

    private static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    private static void login() {
        System.out.println("---LOGIN---");

        String email = getUserInput("Please enter your email:");
        String password = getUserInput("Please enter your password");

        if (User.isRegistered(email, password)) {
            System.out.println("Successfully logged in.");
        } else {
            System.out.println("Could not log-in. Please register.");
        }
    }

    private static void executeCommand(String choice) {
        if (Integer.parseInt(choice) == 1) {
            register();
        } else if (Integer.parseInt(choice) == 2) {
            login();
        } else {
            System.out.println("Invalid input: " + choice);
        }
    }
}
