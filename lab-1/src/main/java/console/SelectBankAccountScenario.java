package console;

import entities.bank.Bank;
import entities.bank.CentralBank;
import entities.bankAccounts.IBankAccount;
import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import models.users.User;

import java.util.List;
import java.util.Scanner;

public class SelectBankAccountScenario {
    private final static Scanner scanner = new Scanner(System.in);
    public static void run(CentralBank centralBank, Bank bank, User user) throws LowAmountException, DateAccessException, NotVerifiedLimitException {
        List<IBankAccount> accountList = bank.findAccounts(user);
        System.out.println("Select your bank account: ");
        for(int i = 0; i < accountList.size(); i++){
            System.out.println((i + 1) + " " + accountList.get(i).getAccountType());
        }
        int accountId = scanner.nextInt();
        if (accountId == 0) {
            return;
        }
        IBankAccount bankAccount = accountList.get(accountId - 1);
        AfterLoginAccountScenario.run(centralBank, bank , bankAccount);
    }
}
