import domain.User;
import domain.Validator;
import orm.UserDO;
import webService.WebApiServer;

import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    private static Scanner in;
    private static DbConnect db;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        in = new Scanner(System.in);
        db = new DbConnect();

        WebApiServer server = new WebApiServer();
        server.start(5000);

        while (true) {
            String input = getUserInput("Please choose an option: 1) Register 2) Login 3) Exit");
            if (!input.isBlank() && Validator.isDigit(input)) {
                if (Integer.parseInt(input) == 3) {
                    server.stop();
                    break;
                }
                executeCommand(input);
            }
        }

        db.disconnect();
    }

    private static String getUserInput(String prompt) {
        String input = "";

        System.out.println(prompt);
        if (in.hasNextLine()) {
            input = in.nextLine();
        }

        return input;
    }

    private static void register() throws ClassNotFoundException {
        User user = getRegistrationInfo();

        if (Validator.isValidRegisterInfo(user)) {
            db.add(new UserDO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()));
            System.out.println("Successfully registered.");
        }
        System.out.println("Registration failed.");
    }

    private static User getRegistrationInfo() throws ClassNotFoundException {
        System.out.println("---REGISTRATION---");

        String firstName = getUserInput("Please enter your first name:");
        String lastName = getUserInput("Please enter your last name:");
        String email = getUserInput("Please enter your email:");
        String password = getUserInput(
                "Please enter a password that follows the following guidelines:\n" +
                        "- 8-20 characters\n" +
                        "- contains at least one digit\n" +
                        "- contains at least one upper case alphabet\n" +
                        "- contains at least one lower case alphabet\n" +
                        "- contains at least one special character"
        );

        return new User(firstName, lastName, email, password);
    }

    private static void login() throws ClassNotFoundException {
        System.out.println("---LOGIN---");

        String email = getUserInput("Please enter your email:");
        String password = getUserInput("Please enter your password");

        if (isRegistered(email) && isCorrectLoginInfo(email, password)) {
            System.out.println("Successfully logged in.");
        } else {
            System.out.println("Could not log-in. Please register.");
        }
    }

    private static boolean isRegistered(String email) throws ClassNotFoundException {
        DbConnect db = new DbConnect();

        return db.get(email) != null;
    }

    private static boolean isCorrectLoginInfo(String email, String password) throws ClassNotFoundException {
        DbConnect db = new DbConnect();
        UserDO userDO = db.get(email);

        return userDO != null && userDO.getPassword().equals(password);
    }

    private static void executeCommand(String choice) throws ClassNotFoundException {
        if (Integer.parseInt(choice) == 1) {
            register();
        } else if (Integer.parseInt(choice) == 2) {
            login();
        } else {
            System.out.println("Invalid input: " + choice);
        }
    }
}