package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    // 2: Our API should be able to process User logins. 
    // Also used for insertAccount method (Requirement 1)
    public Account getAccount (Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String selectSql = "SELECT * FROM account WHERE (username = ?)";
            PreparedStatement preparedSelectStatement = connection.prepareStatement(selectSql);
            preparedSelectStatement.setString(1, account.getUsername());

            ResultSet selectPkeyResultSet = preparedSelectStatement.executeQuery();


            if(selectPkeyResultSet.next()){
                int generated_account_id = (int) selectPkeyResultSet.getLong(1);
                String generated_account_username = selectPkeyResultSet.getString(2);
                String generated_account_password = selectPkeyResultSet.getString(3);
                return new Account(generated_account_id, generated_account_username, generated_account_password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // 1: Our API should be able to process new User registrations.
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();    

        try {
            String insertSql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement preparedInsertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedInsertStatement.setString(1, account.getUsername());
            preparedInsertStatement.setString(2, account.getPassword());

            preparedInsertStatement.executeUpdate();
            ResultSet insertPkeyResultSet = preparedInsertStatement.getGeneratedKeys();

            if(insertPkeyResultSet.next()){
                int generated_account_id = (int) insertPkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
        return null;
    }

    // Used by postMessage and updateMessageByMessageID in MessageDAO
    public Account getAccountByID(int account_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE (account_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int result_account_id = (int) resultSet.getLong(1);
                String result_account_username = resultSet.getString(2);
                String result_account_password = resultSet.getString(3);

                return new Account(result_account_id, result_account_username, result_account_password);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
