import net.lemnik.eodsql.QueryTool;
import orm.UserDAI;
import orm.UserDO;

import java.io.File;
import java.sql.*;

public class DbConnect {
    private final UserDAI userQuery;
    private final String userDbFilename = System.getProperty("user.dir") + "/src/main/java/database/Users_DB.sqlite";

    private String dbUrl;
    private Connection connection;

    public DbConnect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        setDbUrl();
        setConnection();

        userQuery = QueryTool.getQuery(connection, UserDAI.class);
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    /**
     * Get a user by email.
     * @param email the email of the user
     * @return a UserDO
     */
    public UserDO get(String email) {
        return userQuery.getUser(email);
    }

    /**
     * Add a single user to the database.
     * @param user the user to add
     */
    public void add(UserDO user) {
        userQuery.addUser(user);
    }

    /**
     * Update a single user in the database.
     * @param user the user to add
     * @param currentUserEmail the email of the user to update
     * @return true if the user information was updated
     */
    public void update(UserDO user, String currentUserEmail) throws SQLException {
        userQuery.updateUser(user, currentUserEmail);
    }

    private void setDbUrl() {
        File dbFile = new File(userDbFilename);
        if (dbFile.exists()) {
            String DISK_DB_URL = "jdbc:sqlite:";
            dbUrl = DISK_DB_URL + userDbFilename;
        } else {
            throw new IllegalArgumentException("Database file " + userDbFilename + " not found.");
        }
    }

    private void setConnection() {
        try {
            connection = DriverManager.getConnection(dbUrl);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
