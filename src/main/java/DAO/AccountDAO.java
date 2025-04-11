package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        if (account.getUsername().length() > 0 && account.getPassword().length() >= 4 && this.getAccount(account) == null){
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
        }
            
        return null;
    }
}
