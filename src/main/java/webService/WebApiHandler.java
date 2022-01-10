package WebService;

import Domain.User;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class WebApiHandler {
    public void registerUser(Context context) throws ClassNotFoundException {
        User user = context.bodyAsClass(User.class);
        User newUser = user.addUser();

        context.header("Location", "/user/" + newUser.getEmail());
        context.status(HttpCode.CREATED);
        context.json(newUser);
    }
}