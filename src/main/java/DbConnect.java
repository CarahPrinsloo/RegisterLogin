package database.DatabaseInteraction;

import database.DatabaseInteraction.DataObjects.UserDO;

import java.io.File;
import java.sql.*;

public class DbConnect {
    private final String DISK_DB_URL = "jdbc:sqlite:";

    private String userDbFilename = System.getProperty("user.dir") + "/src/main/java/database/Users_DB.sqlite";
    private String dbUrl;

    public DbConnect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        setDbUrl();
    }

    private void setDbUrl() {
        File dbFile = new File(userDbFilename);
        if (dbFile.exists()) {
            dbUrl = DISK_DB_URL + userDbFilename;
        } else {
            throw new IllegalArgumentException("Database file " + userDbFilename + " not found.");
        }
    }

    private Connection dbConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(dbUrl);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not connect to the database");
        }
    }

    private void dbDisconnect(Connection connection) throws SQLException {
        connection.close();
    }

    /**
     * Get a user by email.
     * @param email the email of the user
     * @return a Domain.User
     */
    public UserDO get(String email) throws SQLException {
        Connection connection = dbConnect();

        try(final PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM users u WHERE u.email = ?"
        )){
            stmt.setString(1, email);

            final boolean gotAResultSet = stmt.execute();
            if(!gotAResultSet){
                throw new RuntimeException( "Expected a SQL resultset, but we got an update count instead!" );
            }

            try(ResultSet results = stmt.getResultSet()){
                while(results.next()) {
                    final String userEmail = results.getString( "email");
                    final String userFirstName = results.getString( "first_name");
                    final String userLastName = results.getString( "last_name");
                    final String userPassword = results.getString( "password");

                    dbDisconnect(connection);
                    return new UserDO(userFirstName, userLastName, userEmail, userPassword);
                }
            }
        } catch (SQLException e) {
            dbDisconnect(connection);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add a single user to the database.
     * @param user the user to add
     * @return the newly added Domain.User
     */
    public UserDO add(UserDO user) {
        Connection connection = dbConnect();

        try (final PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users(email, first_name, last_name, password) VALUES (?, ?, ?, ?)"
        )) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());

            final boolean gotAResultSet = stmt.execute();

            if (gotAResultSet || !(stmt.getUpdateCount() == 1)) {
                dbDisconnect(connection);
                throw new RuntimeException("Could not create user.");
            } else {
                dbDisconnect(connection);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update a single user in the database.
     * @param user the user to add
     * @param currentUserEmail the email of the user to update
     * @return true if the user information was updated
     */
    public boolean update(UserDO user, String currentUserEmail) throws SQLException {
        Connection connection = dbConnect();

        try(final PreparedStatement stmt = connection.prepareStatement(
                "UPDATE users " +
                        "SET email = ?, " +
                        "    first_name = ?, " +
                        "    last_name = ?, " +
                        "    password = ?, " +
                        "WHERE email = ?"
        )) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());
            stmt.setString(4, currentUserEmail);

            final boolean gotAResultSet = stmt.execute();
            if (gotAResultSet || !(stmt.getUpdateCount() == 1)) {
                dbDisconnect(connection);
                throw new RuntimeException("Could not create user.");
            } else {
                dbDisconnect(connection);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            dbDisconnect(connection);
        }
        return false;
    }
}
