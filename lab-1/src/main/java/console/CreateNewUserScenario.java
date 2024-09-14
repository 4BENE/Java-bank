package console;

import entities.bank.Bank;
import entities.bank.CentralBank;

import java.util.Scanner;

public class CreateNewUserScenario {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run(CentralBank centralBank) {
        System.out.println("Your bank name: ");
        String bankName = scanner.nextLine();
        Bank bank = centralBank.getBank(bankName);

        System.out.println("Your name: ");
        String name = scanner.nextLine();

        System.out.println("Your surname: ");
        String surname = scanner.nextLine();

        System.out.println("Your passport: ");
        String passport = null;
        passport = scanner.nextLine();

        System.out.println("Your address: ");
        String address = null;
        address = scanner.nextLine();
        bank.addUser(name, surname, address, passport);
        System.out.println("Success: ");
    }
}
