package entities.bankAccounts;

import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import models.account.AccountType;
import models.transactions.Transaction;
import models.transactions.TypeTransaction;
import models.users.User;
import models.date.Date;

import java.util.ArrayList;
import java.util.List;

public class DepositAccount implements IBankAccount{
    public User user;
    public double percentAmount = 0;
    public Date accountBlockingDate;
    public Date currentPercentDate = new Date(0, 0 ,0);
    public Date lastPercentDate = new Date(0 ,0 ,0);
    public double amount;
    public double notVerifiedLimit;
    public double percents;
    public List<Transaction> accountTransactions = new ArrayList<Transaction>();
    public DepositAccount(User user,double amount, Date accountBlockingDate, double notVerifiedLimit, double percents){
        this.notVerifiedLimit = notVerifiedLimit;
        this.amount = amount;
        this.user = user;
        this.accountBlockingDate = accountBlockingDate;
        this.percents = percents;
    }
    @Override
    public void putMoney(double money) {
        amount+=money;
        accountTransactions.add(new Transaction(this, money, TypeTransaction.put));
    }

    @Override
    public void takeMoney(double money) throws LowAmountException, DateAccessException, NotVerifiedLimitException {
        if(!accountBlockingDate.isEmpty()){
            throw new DateAccessException("you need to wait more");
        }
        if(amount < money){
            throw new LowAmountException("to big sum to take");
        }
        if(money > notVerifiedLimit && !this.isVerified()){
            throw new NotVerifiedLimitException("You not verify you ID, you can't take this sum");
        }
        amount -= money;
        accountTransactions.add(new Transaction(this, money, TypeTransaction.take));
    }

    @Override
    public void transferMoney(IBankAccount bankAccount, double money) throws LowAmountException, DateAccessException, NotVerifiedLimitException {
        this.takeMoney(money);
        bankAccount.putMoney(money);
        accountTransactions.add(new Transaction(bankAccount, money, TypeTransaction.transfer));
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
        accountBlockingDate.blockingSkip(date);
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
        return AccountType.deposit;
    }
}
