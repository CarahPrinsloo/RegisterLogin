package orm;

import net.lemnik.eodsql.ResultColumn;

public class UserDO {
    @ResultColumn( value = "first_name" )
    public String first_name;

    @ResultColumn( value = "last_name" )
    public String last_name;

    @ResultColumn( value = "email" )
    public String email;

    @ResultColumn( value = "password" )
    public String password;

    public UserDO() {}

    public UserDO(String first_name, String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @ResultColumn(value = "first_name")
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    @ResultColumn(value = "last_name")
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ResultColumn( value = "email" )
    public void setEmail(String email) {
        this.email = email;
    }
}
