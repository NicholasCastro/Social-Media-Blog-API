import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import Controller.SocialMediaController;
import io.javalin.Javalin;

import Model.Account;
import Service.AccountService;
import Util.ConnectionUtil;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        ConnectionUtil.resetTestDatabase();
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
        
        //TODO: Remove debug print statements

        // AccountService accountService = new AccountService();
        
        /* 
        HttpRequest postRequest = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/login"))
        .POST(HttpRequest.BodyPublishers.ofString("{" +
                "\"username\": \"testuser404\", " +
                "\"password\": \"password\" }"))
        .header("Content-Type", "application/json")
        .build();
        HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        */
        /* 
        Account loginTest = new Account("username", "password");
        accountService.addAccount(loginTest);
        System.out.println("Added loginTest account");

        System.out.println("MAIN - loginTest: " + accountService.getAccount(loginTest));
        */
    }
}
