package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/*
Remaining Requirements:
3: Our API should be able to process the creation of new messages.
6: Our API should be able to delete a message identified by a message ID.
7: Our API should be able to update a message text identified by a message ID.
8: Our API should be able to retrieve all messages written by a particular user.

Completed Requirements:
1: Our API should be able to process new User registrations
2: Our API should be able to process User logins.
4: Our API should be able to retrieve all messages.
5: Our API should be able to retrieve a message by its ID.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // 1: Our API should be able to process new User registrations:
        app.post("/register", this::postRegistrationHandler);
        // 2: Our API should be able to process User logins:
        app.post("/login", this::postLoginHandler);       
        // 4: Our API should be able to retrieve all messages:  
        app.get("/messages", this::getAllMessagesHandler);
        // 5: Our API should be able to retrieve a message by its ID:
        app.get("/messages/{message_id}", this::getMessageByIDHandler);


        // app.start(8080); Server is already started in tests
        return app;
    }

    // 1: Our API should be able to process new User registrations
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

    // 2: Our API should be able to process User logins.
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

    // 4: Our API should be able to retrieve all messages.
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();

        ctx.json(messages);
    }

    // 5: Our API should be able to retrieve a message by its ID.
    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = mapper.readValue(ctx.pathParam("message_id"), int.class);

        Message message = messageService.getMessageByID(message_id);
        if (message != null)
            ctx.json(message);
        else
            ctx.result();
    }
}