package entities.bank;

import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import entities.bankAccounts.CreditAccount;
import entities.bankAccounts.DebitAccount;
import entities.bankAccounts.DepositAccount;
import entities.bankAccounts.IBankAccount;
import models.account.AccountType;
import models.date.Date;
import models.notification.Notification;
import models.transactions.Transaction;
import models.transactions.TypeTransaction;
import models.users.User;
import models.users.UserBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bank {
    public String name;
    public double notVerifiedLimit;
    public List<User> users = new ArrayList<User>();
    public List<IBankAccount> accounts = new ArrayList<IBankAccount>();
    public double defaultPercents;
    public double creditLimit;
    public Date depositBlockingDate;
    public List<User> notificationUsers = new ArrayList<User>();
    public Bank(String name, double defaultPercents, double creditLimit,Date depositBlockingDate, double notVerifiedLimit){
        this.name = name;
        this.defaultPercents = defaultPercents;
        this.creditLimit = creditLimit;
        this.depositBlockingDate = depositBlockingDate;
        this.notVerifiedLimit = notVerifiedLimit;
    }
    public User getUser(String name, String surname){
        for (var x : users){
            if(Objects.equals(x.name, name) && Objects.equals(x.surname, surname) ){
                return x;
            }
        }
        throw new IllegalArgumentException("Can't find this user");
    }

    public List<IBankAccount> findAccounts(User user){
        List<IBankAccount> accountList = new ArrayList<IBankAccount>();
        for(var x : accounts){
            if(Objects.equals(x.getUser().name, user.name) && Objects.equals(x.getUser().surname, user.surname)){
                accountList.add(x);
            }
        }
        if(accountList.isEmpty()){
            throw new IllegalArgumentException("You dont have account");
        }
        return accountList;
    }

    public void verifyUser(User user, String address, String passport){
        if(address == null && passport == null){
            throw new IllegalArgumentException("your address and passport is null");
        }
        else if(address == null){
            users.get(users.indexOf(user)).addAddress(users.get(users.indexOf(user)).address).addPassport(passport);
        }
        else if(passport == null){
            users.get(users.indexOf(user)).addPassport(users.get(users.indexOf(user)).passport).addAddress(address);
        }
        else {
            users.get(users.indexOf(user)).addAddress(address).addPassport(passport);
        }
    }
    public void sendNotification(Notification notification){
        for(var x : notificationUsers){
            x.allNotification.add(notification);
        }
    }
    public void makeChanges(double newPercents, double newLimit){
        for(var x : accounts){
            x.makeChanges(newPercents, newLimit);
        }
        if (newLimit != 0 && newPercents != 0) {
            this.sendNotification(new Notification("NEW CHANGES!", "Now new percents is " + newPercents + "new limit is" + newLimit));
        }
        if(newLimit == 0 && newPercents != 0) {
            this.sendNotification(new Notification("NEW CHANGES!", "Now new percents is " + newPercents));
        }
        if(newLimit != 0 && newPercents == 0){
            this.sendNotification(new Notification("NEW CHANGES!", "New limit is" + newLimit));
        }
        if(newLimit <= 0 && newPercents <= 0){
            throw new IllegalArgumentException("Limit and Percents are positive");
        }
    }
    public void addUser(String name, String surname, String address, String passport){
        User newUser = new UserBuilder().
                withNameAndSurname(name,surname).
                withAddress(address).
                withPassport(passport).
                build();
        users.add(newUser);
    }
    public void createNewAccount(AccountType accountType, double balance, User user){
        if(balance < 0){
            throw new IllegalArgumentException("balance must be positive");
        }
        if(!users.contains(user)){
            users.add(user);
        }
        if(accountType == AccountType.credit){
            IBankAccount newAccount = new CreditAccount(user, balance, defaultPercents, notVerifiedLimit, creditLimit);
            accounts.add(newAccount);
        }
        if(accountType == AccountType.debit){
            IBankAccount newAccount = new DebitAccount(user, balance, defaultPercents,notVerifiedLimit);
            accounts.add(newAccount);
        }
        if(accountType == AccountType.deposit){
            IBankAccount newAccount = new DepositAccount(user, balance,depositBlockingDate,notVerifiedLimit, defaultPercents);
            accounts.add(newAccount);
        }
    }
    public void undoTransaction(IBankAccount account, int idTransaction) throws LowAmountException, DateAccessException, NotVerifiedLimitException {
        Transaction currentTransaction = account.getTransactions().get(idTransaction);
        if(currentTransaction.typeTransaction == TypeTransaction.put){
            account.takeMoney(currentTransaction.sum);
        }
        if(currentTransaction.typeTransaction == TypeTransaction.take){
            account.putMoney(currentTransaction.sum);
        }
        if(currentTransaction.typeTransaction == TypeTransaction.transfer){
            account.putMoney(currentTransaction.sum);
            currentTransaction.recipient.takeMoney(currentTransaction.sum);
        }
    }
}
