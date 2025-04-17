package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    // No-arg Constructor
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    // Constructor with argument, used with mock DAOs in test cases
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // 3: Our API should be able to process the creation of new messages.
    public Message postMessage(Message message) {
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountByID(message.getPosted_by());

        if (message.getMessage_text().length() <= 255 && message.getMessage_text().length() > 0 && account != null) {
            return messageDAO.postMessage(message);
        } else {
            return null;
        }
    }

    // 4: Our API should be able to retrieve all messages.
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // 5: Our API should be able to retrieve a message by its ID.
    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    // 6: Our API should be able to delete a message identified by a message ID.
    public Message deleteMessageByMessageID(int message_id) {
        return messageDAO.deleteMessagebyMessageID(message_id);
    }

    // 7: Our API should be able to update a message text identified by a message ID.
    public Message updateMessageByMessageID (int message_id, String message_text) {
        Message message = this.getMessageByID(message_id);
        Account account = null;
        if (message != null) {
            AccountDAO accountDAO = new AccountDAO();
            account = accountDAO.getAccountByID(message.getPosted_by());
        }

        if (message_text.length() > 0 && message_text.length() <= 255 && account != null) {
            return messageDAO.updateMessageByMessageID(message_id, message_text);
        } else {
            return null;
        }
    }

    // 8: Our API should be able to retrieve all messages written by a particular user.
    public List<Message> getMessagesByAccountID(int account_id) {
        return messageDAO.getMessagesByAccountID(account_id);
    }
}
