package console;

import entities.bank.CentralBank;
import exceptions.DateAccessException;
import exceptions.LowAmountException;
import exceptions.NotVerifiedLimitException;

import java.util.Scanner;

public class StartScenario {
    private final static Scanner scanner = new Scanner(System.in);

    public static void run() throws LowAmountException, DateAccessException, NotVerifiedLimitException {
        CentralBank centralBank = CentralBankRepository.takeData();
        while (true) {
            System.out.println("Choose your action:");
            System.out.println("(1) login user");
            System.out.println("(2) create new user");
            System.out.println("(3) exit");
            int chose = scanner.nextInt();
            switch (chose) {
                case 1:
                    LoginScenario.run(centralBank);
                    break;
                case 2:
                    CreateNewUserScenario.run(centralBank);
                    break;
                case 3:
                    return;
            }
        }
    }
}
