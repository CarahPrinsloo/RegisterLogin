package orm;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;

import java.util.List;

public interface UserDAI extends BaseQuery {
    @Select(
            "SELECT * FROM users"
    )
    List<UserDO> getAllUsers();

    @Select(
            "SELECT * FROM users u "
                    + "WHERE u.email = ?{1}"
    )
    UserDO getUser(String email);

    @Update(
            "INSERT INTO users (email, first_name, last_name, password) "
                    + "VALUES (?{1.email}, ?{1.first_name}, ?{1.last_name}, ?{1.password})"
    )
    void addUser(UserDO user);

    @Update(
            "UPDATE users "
                    + "SET email = ?{1.email}, first_name = ?{1.firstName}, last_name = ?{1.lastName}, password = ?{1.password}"
                    + "WHERE id = ?{2} "
    )
    void updateUser(UserDO user, String currentEmail);
}
