package entities.bankAccounts;

import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import models.account.AccountType;
import models.date.Date;
import models.transactions.Transaction;
import models.transactions.TypeTransaction;
import models.users.User;

import java.util.ArrayList;
import java.util.List;

public class CreditAccount implements IBankAccount {
    public User user;
    public double amount;
    public double percents;
    public double percentAmount;
    public double notVerifiedLimit;
    public double limitAmount;
    public Date currentPercentDate = new Date(0, 0 ,0);
    public Date lastPercentDate = new Date(0 ,0 ,0);
    public List<Transaction> accountTransactions = new ArrayList<Transaction>();
    public CreditAccount(User user, double amount, double percents, double notVerifiedLimit, double limitAmount){
        this.user = user;
        this.amount = amount;
        this.percents = percents;
        this.notVerifiedLimit = notVerifiedLimit;
        this.limitAmount = limitAmount;
    }
    @Override
    public void putMoney(double money) {
        amount += money;
        accountTransactions.add(new Transaction(this, money, TypeTransaction.put));
    }

    @Override
    public void takeMoney(double money) throws LowAmountException, NotVerifiedLimitException {
    if(amount - money < limitAmount){
        throw new LowAmountException("You can't take this sum");
    }
    if(money > notVerifiedLimit){
        throw new NotVerifiedLimitException("You not verify you ID, you can't take this sum");
    }
    amount -= money;
    accountTransactions.add(new Transaction(this, money, TypeTransaction.take));
    }
    @Override
    public boolean isVerified() {
        return user.passport != null && user.address != null;
    }

    @Override
    public List<Transaction> getTransactions() {
        return accountTransactions;
    }

    @Override
    public void skipTime(Date date) {
        lastPercentDate.copyTime(currentPercentDate);
        currentPercentDate.spendTime(date);
        if (currentPercentDate.getMonth() > lastPercentDate.getMonth() || currentPercentDate.getYears() > lastPercentDate.getYears()) {
            Date temp = currentPercentDate;
            temp.spendTime(lastPercentDate);
            percentAmount += amount*(temp.getYears() + (double)temp.getMonth() / 12 + (double)temp.getDays()/ 365) * percents;
            this.putMoney(percentAmount);
            percentAmount = 0;
        } else {
            Date temp = currentPercentDate;
            temp.spendTime(lastPercentDate);
            percentAmount += amount*(temp.getYears() + (double)temp.getMonth() / 12 + (double)temp.getDays()/ 365) * percents;
        }
    }

    @Override
    public void transferMoney(IBankAccount bankAccount, double money) throws LowAmountException, NotVerifiedLimitException {
        this.takeMoney(money);
        bankAccount.putMoney(money);
        accountTransactions.add(new Transaction(bankAccount, money, TypeTransaction.transfer));
    }

    @Override
    public void makeChanges(double percents, double limit) {
        if(percents != 0) {
            this.percents = percents;
        }
        if(percents != 0) {
            notVerifiedLimit = limit;
        }
    }

    @Override
    public User getUser() {
        return user;
    }
    @Override
    public double checkBalance() {
        return amount;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.credit;
    }
}
