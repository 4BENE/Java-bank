package entities.bankAccounts;

import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import models.account.AccountType;
import models.date.Date;
import models.transactions.Transaction;
import models.users.User;

import java.util.List;

public interface IBankAccount {
   public void putMoney(double money);
   public void takeMoney(double money) throws LowAmountException, DateAccessException, NotVerifiedLimitException;
   public void transferMoney(IBankAccount bankAccount, double money) throws LowAmountException, DateAccessException, NotVerifiedLimitException;
   public boolean isVerified();
   public List<Transaction> getTransactions();
   public void skipTime(Date date);
   public void makeChanges(double percents, double limit);
   public User getUser();
   public double checkBalance();
   public AccountType getAccountType();
}
