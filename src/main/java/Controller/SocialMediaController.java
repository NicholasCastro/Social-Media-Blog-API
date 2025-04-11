package Controller;

import Model.Account;
import Service.AccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/*
Remaining Requirements:
3: Our API should be able to process the creation of new messages.
4: Our API should be able to retrieve all messages.
5: Our API should be able to retrieve a message by its ID.
6: Our API should be able to delete a message identified by a message ID.
7: Our API should be able to update a message text identified by a message ID.
8: Our API should be able to retrieve all messages written by a particular user.

Completed Requirements:
1: Our API should be able to process new User registrations
2: Our API should be able to process User logins.
 */
public class SocialMediaController {
    AccountService accountService;

    public SocialMediaController() {
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegistrationHandler);  // 1: Our API should be able to process new User registrations.
        app.post("/login", this::postLoginHandler);           // 2: Our API should be able to process User logins.


        // app.start(8080); Server is already started in tests
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if(addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }

    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.getAccount(account);

        if(loginAccount != null && loginAccount.getUsername().equals(account.getUsername()) && loginAccount.getPassword().equals(account.getPassword())) {
            ctx.json(mapper.writeValueAsString(loginAccount));
        } else {
            ctx.status(401);
        }

    }

}