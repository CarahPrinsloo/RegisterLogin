package webService;

import io.javalin.Javalin;

public class WebApiServer {
    private final Javalin server;

    public WebApiServer() {
        server = Javalin.create(config -> {
            config.defaultContentType = "application/json";
        });
        WebApiHandler apiHandler = new WebApiHandler();

        this.server.post("/users", context -> apiHandler.registerUser(context));
    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }
}