package console;

import entities.bank.Bank;
import entities.bank.CentralBank;
import models.account.AccountType;
import models.date.Date;
import models.users.User;
import models.users.UserBuilder;

public class CentralBankRepository {
    public static CentralBank takeData() {
        CentralBank centralBank = new CentralBank() ;
        Bank sber = SberBank();
        Bank big = BigBank();
        centralBank.addNewBank(sber);
        centralBank.addNewBank(big);
        sber.addUser(createIvan().name,createIvan().surname,createIvan().address,createIvan().passport);
        big.addUser(createMasha().name, createMasha().surname, createMasha().address, createMasha().passport);
        sber.createNewAccount(AccountType.debit, 1000, createIvan());
        sber.createNewAccount(AccountType.credit, 5000, createMasha());
        big.createNewAccount(AccountType.deposit, 3000, createMasha());
        return centralBank;
    }
    private static User createIvan() {
        UserBuilder userBuilder = new UserBuilder();
        return userBuilder.withNameAndSurname("Ivan","Ivanov")
                .withPassport("123")
                .withAddress("Pushkina dom kolotushkina").build();
    }
    private static User createMasha() {
        UserBuilder userBuilder = new UserBuilder();
        return userBuilder.withNameAndSurname("Masha","Chizhikova")
                .withPassport("123321")
                .withAddress("Pushkina dom kolotushkina 1").build();
    }

    private static Bank SberBank() {
        return new Bank("Sber", 10, 200, new Date(0, 1, 0), 1000);
    }

    private static Bank BigBank() {
        return new Bank("Big", 15, 300, new Date(0,2,0), 1000);
    }
}
