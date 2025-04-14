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

    public Account getAccount(Account account) {
        return accountDAO.getAccount(account);
    }

    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }
}
