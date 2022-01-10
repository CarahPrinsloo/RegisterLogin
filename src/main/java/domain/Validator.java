package Domain;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isDigit(String value) {
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidRegisterInfo(User user) {
        return !user.getFirstName().isBlank() &&
                !user.getLastName().isBlank() &&
                !user.getEmail().isBlank() && isValidEmailAddress(user.getEmail()) &&
                !user.getPassword().isBlank() && isValidPassword(user.getPassword());
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
}
