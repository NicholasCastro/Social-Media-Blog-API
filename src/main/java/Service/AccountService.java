package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    // No-arg Constructor
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    // Constructor with argument, used with mock DAOs in test cases
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // 2: Our API should be able to process User logins. 
    // Also used for insertAccount method (Requirement 1)
    public Account getAccount(Account account) {
        Account loginAccount = accountDAO.getAccount(account);

        if(loginAccount != null && loginAccount.getUsername().equals(account.getUsername()) && loginAccount.getPassword().equals(account.getPassword())) {
            return loginAccount;
        } else {
            return null;
        }
    }

    // 1: Our API should be able to process new User registrations.
    public Account addAccount(Account account) {
        if (account.getUsername().length() > 0 && account.getPassword().length() >= 4 && this.getAccount(account) == null){
            return accountDAO.insertAccount(account);
        } else {
            return null;
        } 
    }
}
