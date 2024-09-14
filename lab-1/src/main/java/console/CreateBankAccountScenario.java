package console;

import entities.bank.Bank;
import entities.bank.CentralBank;
import models.account.AccountType;
import models.date.Date;
import models.users.User;

import java.util.Scanner;

public class CreateBankAccountScenario {
    private final static Scanner scanner = new Scanner(System.in);
    public static void run(CentralBank centralBank, Bank bank, User user) {
        System.out.println("Choose account type: ");
        System.out.println("(1) Deposit");
        System.out.println("(2) Credit");
        System.out.println("(3) Debit");
        int accountType = scanner.nextInt();
        AccountType curAccountType;
        switch (accountType) {
            case 1:
                curAccountType = AccountType.deposit;
                break;
            case 2:
                curAccountType = AccountType.credit;
                break;
            case 3:
                curAccountType = AccountType.debit;
                break;
            default:
                return;
        }
        System.out.println("Balance: ");
        int money = scanner.nextInt();
        Date openDate = null;
        if (curAccountType == AccountType.deposit) {
            openDate.spendTime(bank.depositBlockingDate);
        }
        bank.createNewAccount(curAccountType, money, user);
    }
}
