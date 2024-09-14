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

public class AfterLoginAccountScenario {
    private static Scanner scanner = new Scanner(System.in);

    public static void run(CentralBank centralBank, Bank bank, IBankAccount bankAccount) throws LowAmountException, DateAccessException, NotVerifiedLimitException {
        while (true) {
            System.out.println("What do u want?");
            System.out.println("(1) put money");
            System.out.println("(2) take money ");
            System.out.println("(3) transfer money");
            System.out.println("(4) check balance");
            System.out.println("(5) exit");
            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    System.out.println("Money: ");
                    bankAccount.putMoney(scanner.nextDouble());
                    break;
                case 2:
                    System.out.println("Money: ");
                    bankAccount.takeMoney(scanner.nextDouble());
                    break;
                case 3:
                    System.out.println("Destination bank name: ");
                    scanner.nextLine();
                    String bankName = scanner.nextLine();

                    System.out.println("Destination person name: ");
                    String personName = scanner.nextLine();

                    System.out.println("Destination person surname: ");
                    String personSurname = scanner.nextLine();

                    Bank findBank = centralBank.getBank(bankName);
                    User user = findBank.getUser(personName, personSurname);

                    List<IBankAccount> accountList = findBank.findAccounts(user);
                    System.out.println("Select bank account: ");
                    for(int i = 0; i < accountList.size(); i++){
                        System.out.println((i + 1) + " " + accountList.get(i).getAccountType());
                    }
                    int accountId = scanner.nextInt();
                    IBankAccount difBankAccount = accountList.get(accountId - 1);
                    System.out.println("Money : ");
                    double money = scanner.nextDouble();
                    bankAccount.transferMoney(difBankAccount, money);
                    break;
                case 4:
                    System.out.println(bankAccount.checkBalance());
                    break;
                case 5:
                    return;
            }
        }
    }
}
