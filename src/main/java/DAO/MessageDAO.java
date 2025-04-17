package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // 3: Our API should be able to process the creation of new messages.
    public Message postMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                int result_message_id = resultSet.getInt(1);

                return new Message (result_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    // 4: Our API should be able to retrieve all messages.
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();

        List<Message> output = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );

                output.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return output;
    }

    // 5: Our API should be able to retrieve a message by its ID.
    public Message getMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "SELECT * FROM Message WHERE (message_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );

                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // 6: Our API should be able to delete a message identified by a message ID.
    public Message deleteMessagebyMessageID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Message WHERE (message_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );

                String deleteSql = "DELETE FROM Message WHERE (message_id = ?)";
                PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteSql);
                deletePreparedStatement.setInt(1, message_id);

                deletePreparedStatement.executeUpdate();

                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 7: Our API should be able to update a message text identified by a message ID.
    public Message updateMessageByMessageID (int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int result_message_id = resultSet.getInt(1);

                return this.getMessageByID(result_message_id);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // 8: Our API should be able to retrieve all messages written by a particular user.
    public List<Message> getMessagesByAccountID (int account_id) {
        Connection connection = ConnectionUtil.getConnection();

        List<Message> output = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Message WHERE (posted_by = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );

                output.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return output;
    }
}
