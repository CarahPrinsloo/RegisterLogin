package Domain;

import Database.DatabaseInteraction.DataObjects.UserDO;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private UserDO userDAO;

    public User(String firstName, String lastName, String email, String password) throws ClassNotFoundException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        userDAO = new UserDO(firstName, lastName, email, password);
    }

    public User addUser() throws ClassNotFoundException {
        UserDO user = userDAO.addUser();

        if (user == null) {
            System.out.println("ERROR: Could not register user.");
            return null;
        }
        return DAOToUser(user);
    }

    private User DAOToUser(UserDO userDAO) throws ClassNotFoundException {
        return new User(
                userDAO.getFirstName(),
                userDAO.getLastName(),
                userDAO.getEmail(),
                userDAO.getPassword()
        );
    }

    public static boolean isRegistered(String email, String password) throws ClassNotFoundException {
        return UserDO.isRegistered(email, password);
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