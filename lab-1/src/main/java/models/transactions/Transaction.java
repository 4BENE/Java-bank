package models.transactions;

import entities.bankAccounts.IBankAccount;
import models.users.User;

public class Transaction {
    public IBankAccount recipient;
    public double sum;
    public TypeTransaction typeTransaction;
    public Transaction(IBankAccount user, double sum, TypeTransaction typeTransaction){
        this.recipient = user;
        this.sum = sum;
        this.typeTransaction = typeTransaction;
    }
}
