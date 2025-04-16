package Service;

import Model.Message;
import DAO.MessageDAO;

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

    public Message postMessage(Message message) {
        return messageDAO.postMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    public List<Message> getMessagesByAccountID(int account_id) {
        return messageDAO.getMessagesByAccountID(account_id);
    }

    public Message deleteMessageByMessageID(int message_id) {
        return messageDAO.deleteMessagebyMessageID(message_id);
    }

    public Message updateMessageByMessageID (int message_id, String message_text) {
        return messageDAO.updateMessageByMessageID(message_id, message_text);
    }
}
