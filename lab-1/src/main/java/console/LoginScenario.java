package console;

import entities.bank.Bank;
import entities.bank.CentralBank;
import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;
import models.users.User;

import java.util.Scanner;

public class LoginScenario {
    private final static Scanner scanner = new Scanner(System.in);

    public static void run(CentralBank centralBank) throws LowAmountException, DateAccessException, NotVerifiedLimitException {
        while(true) {
            System.out.println("Your bank:");
            String bankName = scanner.nextLine();
            System.out.println("Your name:");
            String name = scanner.nextLine();
            System.out.println("Your surname:");
            String surname = scanner.nextLine();
            Bank bank = centralBank.getBank(bankName);
            User user = bank.getUser(name, surname);
            System.out.println("(1) select bank account");
            System.out.println("(2) create bank account");
            int choose = scanner.nextInt();
            switch (choose){
                case 1:
                    SelectBankAccountScenario.run(centralBank, bank, user);
                    break;
                case 2:
                    CreateBankAccountScenario.run(centralBank, bank, user);
                    break;
            }
        }
    }
}
