package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

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
}
