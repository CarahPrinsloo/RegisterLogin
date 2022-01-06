import java.sql.SQLException;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void addUser() {
        try {
            DbConnect db = new DbConnect();
            boolean userAdded = db.create(new User(firstName, lastName, email, password));
            if (!userAdded) {
                System.out.println("ERROR: Could not register user.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRegistered(String email, String password) {
        User user = null;

        try {
            DbConnect db = new DbConnect();

            user = db.read(email);
        } catch (ClassNotFoundException ex) {
            System.out.println("Could not load class.");
        } catch (SQLException e) {
            System.out.println("Got an SQL exception.");
        }

        return user != null && user.getPassword().equals(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
